package com.localjobs.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.localjobs.domain.Account;
import com.localjobs.domain.Job;
import com.localjobs.domain.JobVo;
import com.localjobs.jpa.repository.AccountRepository;
import com.localjobs.service.CoordinateFinder;
import com.localjobs.service.FullTextSearchService;
import com.localjobs.service.LocalJobsService;
import com.localjobs.utils.SecurityUtils;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Inject
    private LocalJobsService localJobsService;

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private FullTextSearchService fullTextSearchService;

    public JobController() {
    }

    @RequestMapping(value = "nearme/{skills}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<JobVo> allJobsNearToLocationWithSkill(@PathVariable("skills") String[] skills,
            @RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude) throws Exception {

        List<JobVo> jobs = localJobsService.recommendJobs(latitude, longitude, skills,
                SecurityUtils.getCurrentLoggedInUsername());
        return jobs;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Job> allJobs() {
        List<Job> jobs = localJobsService.findAllJobs();
        return jobs;
    }

    @RequestMapping(value = "/{jobId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Job oneJob(@PathVariable("jobId") String jobId) {
        Job job = localJobsService.findOneJob(jobId);
        return job;
    }

    @RequestMapping(value = "/jobs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Job createNewJob(@Valid Job job) {
        localJobsService.saveJob(job);
        return job;
    }

    @RequestMapping(value = "/{jobId}", method = RequestMethod.DELETE)
    public void deleteJob(@PathVariable("jobId") String jobId) {
        Job job = localJobsService.findOneJob(jobId);
        localJobsService.deleteJob(job);
    }

    @RequestMapping("/jobsforme")
    public String allJobsForMe(Model model) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        Account account = accountRepository.findAccountByUsername(SecurityUtils.getCurrentLoggedInUsername());
        double[] coordinates = CoordinateFinder.getLatLng(account.getAddress());
        if (coordinates == null || coordinates.length == 0) {
            return "redirect:/myprofile";
        }

        double latitude = coordinates[0];
        double longitude = coordinates[1];
        List<Job> jobs = localJobsService.findAllJobsNear(latitude, longitude);
        model.addAttribute("jobs", jobs);
        return "jobs";
    }

    @RequestMapping(value = "/apply/{jobId}", method = RequestMethod.POST)
    public String applyJob(@PathVariable("jobId") String jobId) {
        String username = SecurityUtils.getCurrentLoggedInUsername();
        localJobsService.appyJob(jobId, username);
        return "redirect:/home";
    }

    @RequestMapping(value = "/fulltext/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Job> fullTextSearch(@PathVariable("query") String query) {
        System.out.println("Full text search query is .. " + query);
        Set<String> documentIds = fullTextSearchService.search(query);
        if (CollectionUtils.isEmpty(documentIds)) {
            return new ArrayList<Job>();
        }
        Query idsQuery = Query.query(Criteria.where("_id").in(documentIds));
        return mongoTemplate.find(idsQuery, Job.class);
    }
}

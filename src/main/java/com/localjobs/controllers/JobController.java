package com.localjobs.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metric;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.localjobs.domain.Account;
import com.localjobs.domain.Job;
import com.localjobs.googleapi.DistanceResponse;
import com.localjobs.googleapi.GoogleDistanceClient;
import com.localjobs.jpa.repository.AccountRepository;
import com.localjobs.service.CoordinateFinder;
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
	private GoogleDistanceClient googleDistanceClient;

	public JobController() {
	}

	@RequestMapping(value = "nearme/{skills}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<GeoResult<Job>> allJobsNearToLocationWithSkill(
			@PathVariable("skills") String[] skills,
			@RequestParam("longitude") double longitude,
			@RequestParam("latitude") double latitude) throws Exception {

		Metric metric = Metrics.KILOMETERS;
		Query skillsWhereClause = Query.query(Criteria.where("skills").in(
				skills));
		NearQuery nearQuery = NearQuery
				.near(new Point(longitude, latitude), metric)
				.query(skillsWhereClause).num(10);
		GeoResults<Job> geoResults = mongoTemplate
				.geoNear(nearQuery, Job.class);
		List<GeoResult<Job>> jobs = geoResults.getContent();
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

		Account account = accountRepository.findAccountByUsername(SecurityUtils
				.getCurrentLoggedInUsername());
		double[] coordinates = CoordinateFinder.getLatLng(account.getAddress());
		if (coordinates == null || coordinates.length == 0) {
			return "redirect:/myprofile";
		}

		double latitude = coordinates[0];
		double longitude = coordinates[1];
		List<JobDistanceVo> jobsWithDistance = findJobsWithLocation(latitude,
				longitude);
		model.addAttribute("jobs", jobsWithDistance);
		return "jobs";
	}

	@RequestMapping(value = "/apply/{jobId}", method = RequestMethod.POST)
	public String applyJob(@PathVariable("jobId") String jobId) {
		String username = SecurityUtils.getCurrentLoggedInUsername();
		localJobsService.appyJob(jobId, username);
		return "redirect:/home";
	}

	
	private List<JobDistanceVo> findJobsWithLocation(double latitude,
			double longitude) {
		List<Job> jobs = localJobsService.findAllJobsNear(latitude, longitude);
		List<JobDistanceVo> locaJobsWithDistance = new ArrayList<JobDistanceVo>();
		for (Job localJob : jobs) {
			DistanceResponse response = googleDistanceClient.findDirections(
					localJob.getLocation(),
					new double[] { latitude, longitude });
			JobDistanceVo linkedinJobWithDistance = new JobDistanceVo(localJob,
					response.rows[0].elements[0].distance,
					response.rows[0].elements[0].duration);
			locaJobsWithDistance.add(linkedinJobWithDistance);
		}
		return locaJobsWithDistance;
	}
}

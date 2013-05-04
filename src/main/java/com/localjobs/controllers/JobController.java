package com.localjobs.controllers;

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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.localjobs.domain.Job;
import com.localjobs.service.LocalJobsService;

@Controller
@RequestMapping("/jobs")
public class JobController {

	@Inject
	private LocalJobsService localJobsService;

	@Inject
	private MongoTemplate mongoTemplate;

	public JobController() {
	}

	@RequestMapping(value = "/{skills}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<GeoResult<Job>> allJobsNearToLocationWithSkill(
			@PathVariable("skills") String[] skills,
			@RequestParam("longitude") double longitude,
			@RequestParam("latitude") double latitude) throws Exception {
		
		Metric metric = Metrics.KILOMETERS;
		Query skillsWhereClause = Query.query(Criteria.where("skills").in(skills));
		NearQuery nearQuery = NearQuery
									.near(new Point(longitude, latitude), metric)
									.query(skillsWhereClause).num(10);
		GeoResults<Job> geoResults = mongoTemplate.geoNear(nearQuery, Job.class);
		List<GeoResult<Job>> jobs = geoResults.getContent();
		return jobs;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Job> allJobs() {
		List<Job> jobs = localJobsService.findAllJobs();
		return jobs;
	}

	@RequestMapping(value = "/{jobId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Job oneJob(@PathVariable("jobId") String jobId) {
		Job job = localJobsService.findOneJob(jobId);
		return job;
	}

	@RequestMapping(value = "/jobs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Job createNewJob(@Valid Job job) {
		localJobsService.saveJob(job);
		return job;
	}

	@RequestMapping(value = "/{jobId}", method = RequestMethod.DELETE)
	public void deleteJob(@PathVariable("jobId") String jobId) {
		Job job = localJobsService.findOneJob(jobId);
		localJobsService.deleteJob(job);
	}

}

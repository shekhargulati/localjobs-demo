package com.localjobs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.localjobs.domain.Job;
import com.mongodb.Mongo;

public class SearchifyFullTextSearchServiceTest {

	@Test
	public void testAddToIndex() throws Exception{
		SearchifyFullTextSearchService service = new SearchifyFullTextSearchService();
		MongoTemplate mongoTemplate = new MongoTemplate(new Mongo(), "localjobs");
		List<Job> jobs = mongoTemplate.findAll(Job.class);
		for (Job job : jobs) {
			service.addToIndex(job.getId(), toFields(job));
		}
		
	}

	private Map<String, String> toFields(Job job) {
		Map<String , String> map = new HashMap<>();
		map.put("jobdesc", job.getJobTitle());
		return map;
	}

}

package com.localjobs.service;

import java.util.List;

import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.localjobs.domain.JobVo;
import com.mongodb.Mongo;

public class LocalJobsServiceImplTest {
    

    @Test
    public void testRecommendJobs() throws Exception{
        MongoTemplate mongoTemplate = new MongoTemplate(new Mongo(), "localjobs");
        LocalJobsServiceImpl localJobsServiceImpl = new LocalJobsServiceImpl(mongoTemplate);
        double latitude = 29.970663;
        double longitude = 76.859124;
        String[] skills = {"java"};
        String username = "shekhar";
        List<JobVo> jobs = localJobsServiceImpl.recommendJobs(latitude, longitude, skills , username );
        
        for (JobVo jobVo : jobs) {
            System.out.println(jobVo);
        }
    }

}

package com.localjobs.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metric;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import com.localjobs.domain.Job;
import com.localjobs.domain.JobVo;
import com.localjobs.mongodb.repository.JobRepository;
import com.mongodb.MongoException;

@Service
public class LocalJobsServiceImpl implements LocalJobsService {

    private MongoTemplate mongoTemplate;

    @Inject
    private JobRepository jobRepository;

    @Inject
    public LocalJobsServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Job> findAllJobs() {
        Query query = new Query().limit(10);
        return mongoTemplate.find(query, Job.class);
    }

    @Override
    public Job findOneJob(String jobId) {
        Query query = Query.query(Criteria.where("_id").is(jobId));
        return mongoTemplate.findOne(query, Job.class);
    }

    @Override
    public List<Job> findAllJobsNear(double latitude, double longitude) {
        Query query = Query.query(Criteria.where("location").near(new Point(latitude, longitude))).limit(5);
        return mongoTemplate.find(query, Job.class);
    }

    @Override
    public List<Job> findAllJobsNearWithSkill(double latitude, double longitude, String[] skills, String username) {
        Query query = Query.query(
                Criteria.where("location").near(new Point(latitude, longitude)).and("skills").in(Arrays.asList(skills))
                        .and("appliedBy").nin(username)).limit(5);
        return mongoTemplate.find(query, Job.class);
    }

    @Override
    public Job saveJob(Job job) {
        jobRepository.save(job);
        return job;
    }

    @Override
    public long totalNumberOfJob() {
        return jobRepository.count();
    }

    @Override
    public void deleteJob(Job job) {
        jobRepository.delete(job);
    }

    @Override
    public void appyJob(String jobId, String username) {
        Query query = Query.query(Criteria.where("_id").is(jobId));
        Update update = new Update().addToSet("appliedBy", username);
        mongoTemplate.updateFirst(query, update, Job.class);

    }

    @Override
    public List<JobVo> recommendJobs(double latitude, double longitude, String[] skills, String username) {
        Query query = Query.query(Criteria.where("skills").in(Arrays.asList(skills)).and("appliedBy").nin(username));
        List<JobVo> jobs = getJobsNearWithQuery(latitude, longitude, query);
        return jobs;
    }

    @Override
    public List<JobVo> appliedJobs(double latitude, double longitude, String user) {
        Query query = Query.query(Criteria.where("appliedBy").in(user));
        return getJobsNearWithQuery(latitude, longitude, query);
    }

    private List<JobVo> getJobsNearWithQuery(final double latitude, final double longitude, final Query query) {

        RetryCallback<List<JobVo>> callback = new RetryCallback<List<JobVo>>() {

            @Override
            public List<JobVo> doWithRetry(RetryContext context) throws Exception {
                Metric metric = Metrics.KILOMETERS;
                NearQuery near = NearQuery.near(new Point(longitude, latitude)).in(metric).query(query).num(5)
                        .spherical(true);
                GeoResults<Job> geoResults = mongoTemplate.geoNear(near, Job.class);
                List<JobVo> jobs = new ArrayList<>();
                Iterator<GeoResult<Job>> iterator = geoResults.iterator();
                while (iterator.hasNext()) {
                    GeoResult<Job> geoResult = iterator.next();
                    JobVo jobVo = new JobVo(geoResult);
                    jobs.add(jobVo);
                }
                return jobs;
            }
        };

        return retryOperation(callback);
    }

    private List<JobVo> retryOperation(RetryCallback<List<JobVo>> callback) {
        RetryTemplate retryTemplate = new RetryTemplate();
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(DataAccessResourceFailureException.class, true);
        retryableExceptions.put(MongoException.class, true);
        retryableExceptions.put(IOException.class, true);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5, retryableExceptions);
        retryTemplate.setRetryPolicy(retryPolicy);
        try {
            return retryTemplate.execute(callback);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

package com.localjobs.service;

import java.util.List;

import com.localjobs.domain.Job;
import com.localjobs.domain.JobVo;

public interface LocalJobsService {
    List<Job> findAllJobs();

    Job findOneJob(String jobId);

    List<Job> findAllJobsNear(double latitude, double longitude);

    List<Job> findAllJobsNearWithSkill(double latitude, double longitude, String[] skills, String username);

    public abstract void deleteJob(Job job);

    public abstract long totalNumberOfJob();

    public abstract Job saveJob(Job job);

    void appyJob(String jobId, String username);

    List<JobVo> appliedJobs(double latitude, double longitude, String user);

    public abstract List<JobVo> recommendJobs(double latitude, double longitude, String[] skills, String username);
}

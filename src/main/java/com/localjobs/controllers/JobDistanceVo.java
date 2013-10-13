package com.localjobs.controllers;

import com.localjobs.domain.Job;
import com.localjobs.googleapi.Distance;
import com.localjobs.googleapi.Duration;

public class JobDistanceVo {

    private String jobId;
    private String jobTitle;
    private String companyName;
    private String[] skills;
    private String address;

    private String distance;
    private String duration;

    public JobDistanceVo() {
        // TODO Auto-generated constructor stub
    }

    public JobDistanceVo(Job job, Distance distance, Duration duration) {
        this.jobId = job.getId();
        this.jobTitle = job.getJobTitle();
        this.skills = job.getSkills();
        this.companyName = job.getCompany().getCompanyName();
        this.address = job.getFormattedAddress();
        if (distance != null) {
            this.distance = distance.getText();
        }
        if (duration != null) {
            this.duration = duration.getText();
        }

    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}

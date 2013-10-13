package com.localjobs.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;

import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoResult;

public class JobVo implements Serializable {

    private String id;
    private String title;
    private String company;
    private double[] lngLat;
    private double distance;
    private String distanceText;
    private String metric;
    private String[] skills;
    private String address;

    public JobVo(GeoResult<Job> geoResult) {
        Job job = geoResult.getContent();
        Distance dist = geoResult.getDistance();
        this.id = job.getId();
        this.title = job.getJobTitle();
        this.skills = job.getSkills();
        this.address = job.getFormattedAddress();
        this.company = job.getCompany().getCompanyName();
        this.lngLat = job.getLocation();
        this.distance = format(dist.getValue());
        this.metric = dist.getMetric().toString();
        this.distanceText = String.valueOf(this.distance) + " " + metric;
    }

    private double format(double d){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(d);
        double parsedValue = 0;
        try {
             parsedValue = (Double)decimalFormat.parse(format);
        } catch (ParseException e) {
            return d;
        }
        return parsedValue;
    }
    public JobVo() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public double[] getLngLat() {
        return lngLat;
    }

    public double getDistance() {
        return distance;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public String getMetric() {
        return metric;
    }

    public String[] getSkills() {
        return skills;
    }
    
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "JobVo [id=" + id + ", title=" + title + ", company=" + company + ", lngLat=" + Arrays.toString(lngLat)
                + ", distance=" + distance + ", distanceText=" + distanceText + ", metric=" + metric + ", skills="
                + Arrays.toString(skills) + ", address=" + address + "]";
    }
    
}

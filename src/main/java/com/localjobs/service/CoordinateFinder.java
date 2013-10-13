package com.localjobs.service;

import java.util.Arrays;

import org.springframework.util.CollectionUtils;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

public class CoordinateFinder {

    /**
     * Returns an array of which first is latitude and second is longitude
     * 
     * @param location
     * @return
     * @throws Exception
     */
    public static double[] getLatLng(String location) {
        final Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(location).setLanguage("en")
                .getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);

        if (CollectionUtils.isEmpty(geocoderResponse.getResults())) {
            return new double[0];
        }

        GeocoderResult geocoderResult = geocoderResponse.getResults().get(0);
        LatLng latLng = geocoderResult.getGeometry().getLocation();
        return new double[] { latLng.getLat().doubleValue(), latLng.getLng().doubleValue() };
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(CoordinateFinder.getLatLng("sector 56, gurgaon")));
    }

}

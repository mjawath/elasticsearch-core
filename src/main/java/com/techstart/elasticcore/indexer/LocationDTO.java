package com.techstart.elasticcore.indexer;

//import com.webshop.entity.Location;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.geo.Point;

/**
 * Created by jawa on 11/4/2020.
 */
public class LocationDTO {

    private Double lat;
    private Double lng;
    private Double lon;

    private String gps;
    private Point point;
    private GeoPoint geoPoint;


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
        lon =lng;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
        lng= lon;
    }
}

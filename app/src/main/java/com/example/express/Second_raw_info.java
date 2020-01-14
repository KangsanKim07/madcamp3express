package com.example.express;

import com.skt.Tmap.TMapPOIItem;

import java.io.Serializable;

public class Second_raw_info implements Serializable {
    private String name;
    private String phone_num;
    private String Lon;
    private String Lat;
    private String time;

    public Second_raw_info(TMapPOIItem item,String phone_num, String time){
        super();
        name = item.getPOIName();
        Lon = item.frontLon;
        Lat = item.noorLat;
        this.phone_num = phone_num;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }
}

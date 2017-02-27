package com.example.administrator.signin.modul;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017-02-02 .
 */

public class GeoPoint extends BmobObject {
    private Integer ID;
    private Double Latitude;
    private Double longitude;
    private Long time;
    private Integer tId;
    private String cId;


    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcId() {
        return cId;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

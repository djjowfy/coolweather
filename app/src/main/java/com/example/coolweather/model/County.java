package com.example.coolweather.model;

/**
 * Created by WangFuyi on 2015/2/21.
 */
public class County {
    private int id;
    private String countyName;
    private String countyPyName;
    private int cityId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCountyName() {
        return countyName;
    }
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
    public String getCountyPyName() {
        return countyPyName;
    }
    public void setCountyPyName(String countyPyName) {
        this.countyPyName = countyPyName;
    }
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}

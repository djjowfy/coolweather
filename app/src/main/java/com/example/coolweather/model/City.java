package com.example.coolweather.model;

/**
 * Created by WangFuyi on 2015/2/21.
 */
public class City {
    private int id;
    private String cityName;
    private String cityPyName;
    private int provinceId;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public int getProvinceId() {
        return provinceId;
    }
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
    public String getCityPyName() {
        return cityPyName;
    }
    public void setCityPyName(String cityPyName) {
        this.cityPyName = cityPyName;
    }
}

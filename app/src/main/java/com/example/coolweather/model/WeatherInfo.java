package com.example.coolweather.model;

import java.util.Timer;

/**
 * Created by WangFuyi on 2015/2/22.
 */

public class WeatherInfo {
    private String cityName;
    private String stateDetailed;
    private String humidity;
    private String time;
    private String countyCode;
    private Tem tem = new Tem();
    private Wind wind = new Wind();

    public String getCityName(){
        return cityName;
    }
    public void setCityName(String cityName){
        this.cityName = cityName;
    }

    public String getStateDetailed(){
        return stateDetailed;
    }
    public void setStateDetailed(String stateDetailed){
        this.stateDetailed = stateDetailed;
    }

    public String getHumidity(){
        return humidity;
    }
    public void setHumidity(String humidity){
        this.humidity = humidity;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public Tem getTem(){
        return tem;
    }
    public void setTem(Tem tem){
        this.tem = tem;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}

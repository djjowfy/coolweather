package com.example.coolweather.model;

/**
 * Created by WangFuyi on 2015/2/23.
 */
public class Wind{
    private String  windState;
    private String  windDir;
    private String  windPower;

    public void setWindState(String windState) {
        this.windState = windState;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public String getWindDir() {
        return windDir;
    }

    public String getWindPower() {
        return windPower;
    }

    public String getWindState() {
        return windState;
    }
}

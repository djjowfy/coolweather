package com.example.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.coolweather.db.CoolWeatherDB;
import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;
import com.example.coolweather.model.Tem;
import com.example.coolweather.model.WeatherInfo;
import com.example.coolweather.model.Wind;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by WangFuyi on 2015/2/21.
 */
public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response) {
        try {
            if (!TextUtils.isEmpty(response)) {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = factory.newPullParser();
                xmlPullParser.setInput(new StringReader(response));
                int eventType = xmlPullParser.getEventType();
                Province province = new Province();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String nodeName = xmlPullParser.getName();
                    switch (eventType) {
                        // 开始解析某个结点
                        case XmlPullParser.START_TAG: {
                            if ("city".equals(nodeName)) {
                                province.setProvinceName(xmlPullParser.getAttributeValue(null, "quName"));// 通过属性名来获取属性值
                                province.setProvincePyName(xmlPullParser.getAttributeValue(null, "pyName"));
                                // 将解析出来的数据存储到Province表
                                coolWeatherDB.saveProvince(province);
                            }
                            break;
                        }
                        // 完成解析某个结点
                        case XmlPullParser.END_TAG: {
                            if ("china".equals(nodeName)) {
                                return true;
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId,String provincePyName) {
        try {
            if (!TextUtils.isEmpty(response)) {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = factory.newPullParser();
                xmlPullParser.setInput(new StringReader(response));
                int eventType = xmlPullParser.getEventType();
                City city = new City();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String nodeName = xmlPullParser.getName();
                    switch (eventType) {
                        // 开始解析某个结点
                        case XmlPullParser.START_TAG: {
                            if ("city".equals(nodeName)) {
                                city.setCityName(xmlPullParser.getAttributeValue(null, "cityname"));// 通过属性名来获取属性值
                                city.setCityPyName(xmlPullParser.getAttributeValue(null, "pyName"));// 通过属性名来获取属性值
                                city.setProvinceId(provinceId);
                                // 将解析出来的数据存储到City表
                                coolWeatherDB.saveCity(city);
                            }
                            break;
                        }
                        // 完成解析某个结点
                        case XmlPullParser.END_TAG: {
                            if (provincePyName.equals(nodeName)) {
                                return true;
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,String response,int cityId,String cityPyName) {
        try {
            if (!TextUtils.isEmpty(response)) {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = factory.newPullParser();
                xmlPullParser.setInput(new StringReader(response));
                int eventType = xmlPullParser.getEventType();
                County county = new County();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String nodeName = xmlPullParser.getName();
                    switch (eventType) {
                        // 开始解析某个结点
                        case XmlPullParser.START_TAG: {
                            if ("city".equals(nodeName)) {
                                county.setCountyName(xmlPullParser.getAttributeValue(null, "cityname"));// 通过属性名来获取属性值
                                county.setCountyPyName(xmlPullParser.getAttributeValue(null, "pyName"));// 通过属性名来获取属性值
                                county.setCountyCode(xmlPullParser.getAttributeValue(null, "url"));
                                county.setCityId(cityId);
                                // 将解析出来的数据存储到City表
                                coolWeatherDB.saveCounty(county);
                            }
                            break;
                        }
                        // 完成解析某个结点
                        case XmlPullParser.END_TAG: {
                            if (cityPyName.equals(nodeName)) {
                                return true;
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    /**
     * 解析和处理服务器返回的天气数据
     */
    public static void handleWeatherResponse(Context context, String response,String countyCode,String cityPyName) {
        try {
            if (!TextUtils.isEmpty(response)) {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = factory.newPullParser();
                xmlPullParser.setInput(new StringReader(response));
                int eventType = xmlPullParser.getEventType();
                WeatherInfo weatherInfo = new WeatherInfo();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String nodeName = xmlPullParser.getName();
                    if(eventType == XmlPullParser.START_TAG){
                        if ("city".equals(nodeName) && countyCode.equals(xmlPullParser.getAttributeValue(null, "url"))) {
                            //城市
                            weatherInfo.setCityName(xmlPullParser.getAttributeValue(null, "cityname"));
                            //天气状况
                            weatherInfo.setStateDetailed(xmlPullParser.getAttributeValue(null, "stateDetailed"));// 通过属性名来获取属性值
                            //温度
                            Tem tem = weatherInfo.getTem();
                            tem.setTem1(xmlPullParser.getAttributeValue(null, "tem1"));// 通过属性名来获取属性值
                            tem.setTem2(xmlPullParser.getAttributeValue(null, "tem2"));
                            tem.setTemNow(xmlPullParser.getAttributeValue(null, "temNow"));
                            weatherInfo.setTem(tem);
                            //风
                            Wind wind =  new Wind();
                            wind.setWindState(xmlPullParser.getAttributeValue(null, "windState"));
                            wind.setWindDir(xmlPullParser.getAttributeValue(null, "windDir"));
                            wind.setWindPower(xmlPullParser.getAttributeValue(null, "windPower"));
                            weatherInfo.setWind(wind);
                            //湿度
                            weatherInfo.setHumidity(xmlPullParser.getAttributeValue(null, "humidity"));
                            //更新时间
                            weatherInfo.setTime(xmlPullParser.getAttributeValue(null, "time"));
                            //天气城市编码
                            weatherInfo.setCountyCode(xmlPullParser.getAttributeValue(null, "url"));
                            // 将解析出来的数据存储到SharedPreferences
                            saveWeatherInfo(context,weatherInfo,cityPyName);
                        }
                    }
                    eventType = xmlPullParser.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的所有天气信息存储到SharedPreferences文件中。
     */
    public static void saveWeatherInfo(Context context,WeatherInfo weatherInfo,String cityPyName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);

        editor.putString("city_name", weatherInfo.getCityName());

        editor.putString("weather_state_detailed",weatherInfo.getStateDetailed());

        editor.putString("temp1", weatherInfo.getTem().getTem1());
        editor.putString("temp2", weatherInfo.getTem().getTem2());
        editor.putString("temp_now", weatherInfo.getTem().getTemNow());

        editor.putString("wind_state", weatherInfo.getWind().getWindState());
        editor.putString("wind_dir", weatherInfo.getWind().getWindDir());
        editor.putString("wind_power", weatherInfo.getWind().getWindPower());

        editor.putString("humidity",weatherInfo.getHumidity());

        editor.putString("publish_time", weatherInfo.getTime());

        editor.putString("county_code", weatherInfo.getCountyCode());

        editor.putString("city_py_name",cityPyName);

        editor.putString("current_date", sdf.format(new Date()));

        editor.commit();
    }

}
package com.example.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.coolweather.db.CoolWeatherDB;
import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

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
}

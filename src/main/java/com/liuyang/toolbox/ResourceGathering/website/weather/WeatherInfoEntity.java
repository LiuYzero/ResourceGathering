package com.liuyang.toolbox.ResourceGathering.website.weather;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 天气信息实体类
 *
 * @author liuyang
 * @since 2025/02/26
 */
@Data
@Setter
@Getter
public class WeatherInfoEntity {

    // 地点
    String address;
    // 日期
    String date;
    // 最低温度
    String minTemperature;
    // 最高温度
    String maxTemperature;
    // 最低湿度
    String minHumidity;
    // 最高湿度
    String maxHumidity;
    // 风力信息
    String wind;
    // 天气信息
    String weather;
    // 详细信息
    String detailsInfo;
    // 附加信息
    String additionalInfo;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(String minHumidity) {
        this.minHumidity = minHumidity;
    }

    public String getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(String maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDetailsInfo() {
        return detailsInfo;
    }

    public void setDetailsInfo(String detailsInfo) {
        this.detailsInfo = detailsInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "WeatherInfoEntity{" +
                "address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", minTemperature='" + minTemperature + '\'' +
                ", maxTemperature='" + maxTemperature + '\'' +
                ", minHumidity='" + minHumidity + '\'' +
                ", maxHumidity='" + maxHumidity + '\'' +
                ", wind='" + wind + '\'' +
                ", weather='" + weather + '\'' +
                ", detailsInfo='" + detailsInfo + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}

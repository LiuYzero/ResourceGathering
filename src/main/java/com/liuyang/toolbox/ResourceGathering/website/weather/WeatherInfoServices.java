package com.liuyang.toolbox.ResourceGathering.website.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 天气信息类<br/>
 * 包含了天气信息的    城市名称，  日期，  天气，  最高温度，  最低温度<br/>
 *
 * @Author: liuyang
 * @Date: 2025/2/26
 */
@Service
public class WeatherInfoServices {
    private static final Logger logger = LoggerFactory.getLogger(WeatherInfoServices.class);
//
//    public static void main(String[] args) {
//        new WeatherInfoServices().queryWeatherInfo("");
//    }

    public WeatherInfoEntity queryWeatherInfo(){
        WeatherInfoEntity weatherInfoEntity = new WeatherInfoEntity();
        String originalWeatherInfo = originalWeatherInfo(requestWebsiteContent());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(originalWeatherInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(jsonNode == null){
            return weatherInfoEntity;
        }
        JsonNode Weather24HourNode =  jsonNode.get("Data").get("Weather24Hour");


        for (JsonNode node : Weather24HourNode) {
            weatherInfoEntity.setAddress("东莞");
            weatherInfoEntity.setDate(node.get("UpdateDate").asText());
            weatherInfoEntity.setMinTemperature(node.get("MinTemp").asText());
            weatherInfoEntity.setMaxTemperature(node.get("MaxTemp").asText());
            weatherInfoEntity.setMinHumidity(node.get("MinHumidity").asText());
            weatherInfoEntity.setMaxHumidity(node.get("MaxHumidity").asText());
            weatherInfoEntity.setWind(node.get("Wind").asText());
            weatherInfoEntity.setWeather(node.get("Weather_One").asText()+";"+node.get("Weather_Two").asText());
            weatherInfoEntity.setDetailsInfo(node.get("AfterTime").asText());
        }
        logger.info("dongguan weather {}", weatherInfoEntity);

        return weatherInfoEntity;
    }

    /**
     * 解析原始的天气信息，返回一个json字符串，字符串中包含了天气信息
     * @param webContent 原始网页内容
     * @return json天气信息
     */
    public String originalWeatherInfo(String webContent){
        Document document = Jsoup.parse(webContent);
        Elements scriptElements = document.select("script");
        for (Element scriptElement : scriptElements) {
            String scriptElementContent = scriptElement.html();
            if(scriptElementContent.contains("index.forecast")){
                scriptElementContent = scriptElementContent.substring(scriptElementContent.indexOf("forecast")+10);
                scriptElementContent = scriptElementContent.substring(0, scriptElementContent.length()-16);
                logger.info("originalWeatherInfo {}", scriptElementContent);
                return scriptElementContent;
            }
        }

        return "";
    }

    public String requestWebsiteContent(){
        String responseContent = "";

        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        logger.info("dateStr is {}", dateStr);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://www.dg121.com/Forecast/Index?_t="+dateStr)
                .method("GET", null)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            responseContent = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("requestWebsiteContent {}", responseContent);
        return responseContent;
    }
}

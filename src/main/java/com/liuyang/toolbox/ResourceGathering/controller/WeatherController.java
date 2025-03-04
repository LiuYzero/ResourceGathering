package com.liuyang.toolbox.ResourceGathering.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.liuyang.toolbox.ResourceGathering.entity.ResponseResult;
import com.liuyang.toolbox.ResourceGathering.website.weather.WeatherInfoEntity;
import com.liuyang.toolbox.ResourceGathering.website.weather.WeatherInfoServices;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 天气信息类<br/>
 * 包含了天气信息的    城市名称，  日期，  天气，  最高温度，  最低温度<br/>
 *
 * @Author: liuyang
 * @Date: 2025/2/26
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {
    private static final Logger logger =  LoggerFactory.getLogger(WeatherController.class);

    @Resource
    WeatherInfoServices weatherServices;

    @PostMapping("/qeuryDongGuan")
    public ResponseResult qeuryDongGuan(@RequestBody JsonNode data){
        logger.info("qeuryDongGuan {}", data);
        WeatherInfoEntity entity = weatherServices.queryWeatherInfo();
        return ResponseResult.success(entity);
    }

}

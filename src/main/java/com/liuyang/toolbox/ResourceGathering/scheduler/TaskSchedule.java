package com.liuyang.toolbox.ResourceGathering.scheduler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liuyang.toolbox.ResourceGathering.monitor.IotMonitorServices;
import jakarta.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TaskSchedule {
    public  static final Logger logger = LoggerFactory.getLogger(TaskSchedule.class);


    private static final String RESOURCE_URL = "http://PG-JPA-DBPAAS-SCHEDULER/pg/resources/query";
    private static final String WEATHER_URL = "http://RESOURCEGATHERING/weather/queryDongGuan";

    @Resource
    RestTemplate restTemplate;

    @Resource
    IotMonitorServices iotMonitorServices;

    @Scheduled(cron="*/10 * *  * * ?")
    public void monitorReport(){
        iotMonitorServices.monitorReport();
        logger.info("executed iotMonitorServices.monitorReport();");
    }



}

package com.liuyang.toolbox.ResourceGathering.monitor;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.management.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IOT监控服务<br>
 * 提供微服务运行相关的监控数据<br/>
 * 堆内存、bean数量、线程数量、JVM运行时间等<br/>
 *
 */
@Service
public class IotMonitorServices {

    private static final Logger logger = LoggerFactory.getLogger(IotMonitorServices.class);

    private static final String INFLUXDB_URL = "http://IOT-INFLUXDB-API/influxdb/insertData";

    @Autowired
    private Environment environment;

    @Autowired
    RestTemplate restTemplate;

    public void monitorReport(){
        JSONObject monitorData = getJVMInfo();
        logger.info("monitorData: {}", monitorData);
        Boolean flag = insertData(monitorData);
    }

    public boolean insertData(JSONObject monitorData){
        JSONObject response = restTemplate.postForObject(INFLUXDB_URL, monitorData, JSONObject.class);
        logger.info("response: {}", response);
        return Boolean.TRUE;
    }

    public JSONObject getJVMInfo(){

        //运行时情况
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String hostName = runtime.getName();

        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
        } catch (UnknownHostException e) {
            logger.error("",e);
        }

        String serviceName = environment.getProperty("spring.application.name");
        String servicePort = environment.getProperty("server.port");

        //操作系统情况
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = os.getSystemLoadAverage();
        logger.info("cpuLoad: {}", cpuLoad);


        //  线程使用情况
        ThreadMXBean threadUsage = ManagementFactory.getThreadMXBean();
        int threadNum = threadUsage.getThreadCount();
        logger.info("threadNum: {}", threadNum);

        //堆内存使用情况
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        long heapMemoryUsed = heapMemoryUsage.getUsed() / 1024 / 1024;
        long heapMemoryMax = heapMemoryUsage.getMax() / 1024 / 1024;
        logger.info("heapMemoryUsed: {}", heapMemoryUsed);
        logger.info("heapMemoryMax: {}", heapMemoryMax);

        // 非堆内存
        MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        long nonHeapMemoryUsed = nonHeapMemoryUsage.getUsed() / 1024 / 1024;
        long nonHeapMemoryMax = nonHeapMemoryUsage.getMax() / 1024 / 1024;
        logger.info("nonHeapMemoryUsed: {}", nonHeapMemoryUsed);
        logger.info("nonHeapMemoryMax: {}", nonHeapMemoryMax);

        // 类加载情况
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        long classLoadedNum = classLoadingMXBean.getLoadedClassCount();
        long classLoadTotalNum = classLoadingMXBean.getTotalLoadedClassCount();
        logger.info("classLoadedNum: {}", classLoadedNum);
        logger.info("classLoadTotalNum: {}", classLoadTotalNum);

        JSONObject monitorData = new JSONObject();
        monitorData.put("database", "db_iot");
        monitorData.put("table","t_jvm");
        JSONObject tags = new JSONObject();
        JSONObject fields = new JSONObject();

        tags.put("hostname", hostName);
        tags.put("serviceName",serviceName);
        tags.put("servicePort",servicePort);

        fields.put("cpuLoad", cpuLoad);
        fields.put("threadNum", threadNum);
        fields.put("heapMemoryUsed", heapMemoryUsed);
        fields.put("heapMemoryMax", heapMemoryMax);
        fields.put("nonHeapMemoryUsed", nonHeapMemoryUsed);
        fields.put("nonHeapMemoryMax", nonHeapMemoryMax);
        fields.put("classLoadedNum", classLoadedNum);
        fields.put("classLoadTotalNum", classLoadTotalNum);

        monitorData.put("tags", tags);
        monitorData.put("fields", fields);

        return monitorData;


    }
}


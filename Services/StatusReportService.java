package org.algorithmcontestdatacollect.crawlerendpoint2.Services;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatusReportService {
    private static Logger logger = LoggerFactory.getLogger(StatusReportService.class);

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    Environment environment;

    @Autowired
    ApplicationContext applicationContext;

    @Scheduled(cron = "*/5 * * * * *")
    public void report() {
        var spiderNames = applicationContext.getBeanNamesForType(Spider.class);
        JSONObject upload = new JSONObject();
        for(var spiderName: spiderNames) {
            Spider spider = applicationContext.getBean(spiderName,Spider.class);
            JSONObject status = (JSONObject) JSONObject.toJSON(spider.getStatus());
            upload.put(spiderName,status);
        }
        redisTemplate.opsForHash().put("spiderStatus",environment.getProperty("spring.application.name"),upload.toJSONString());
        redisTemplate.opsForHash().put("spiderExpire",environment.getProperty("spring.application.name"),String.valueOf(System.currentTimeMillis()));
    }
}

package org.algorithmcontestdatacollect.crawlerendpoint2.HTTPInterface.ControlInterface;

import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//从Redis数据库中获取爬虫的过期时间和状态信息，
// 筛选出在过去60秒内活跃的爬虫，然后构造一个包含爬虫状态信息的JSON对象作为响应返回
@RestController
public class CrawlerInfoInterface {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @GetMapping("/GetAllCrawlerStatus")
    public String getAllCrawlerStatus() {
        var entries = redisTemplate.opsForHash().entries("spiderExpire");
        var ret = new JSONObject();
        for(var entry:entries.entrySet()){
            if(Long.parseLong((String) entry.getValue()) >= System.currentTimeMillis() - 60*1000) {
                var stat = new JSONObject();
                stat.put("lastTime",entry.getValue());
                stat.put("status",JSONObject.parseObject((String) redisTemplate.opsForHash().get("spiderStatus",(String)entry.getKey())));
                ret.put((String) entry.getKey(),stat);
            }
        }
        return ResponseUtil.JSONReturn(200,ret);
    }
}

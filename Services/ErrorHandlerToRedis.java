package org.algorithmcontestdatacollect.crawlerendpoint2.Services;

import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import reactor.util.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;


public class ErrorHandlerToRedis implements IErrorHandler{
    private RedisConnectionFactory redisConnectionFactory;
    private String handlerName;
    private String streamName;

    private String spiderName;
    public ErrorHandlerToRedis(String streamName,String handlerName,RedisConnectionFactory redisConnectionFactory,String spiderName){
        this.handlerName = handlerName;
        this.redisConnectionFactory = redisConnectionFactory;
        this.streamName = streamName;
        this.spiderName = spiderName;
    }
    @Override
    public void handleTask(Fault fault,@Nullable RequestParams requestParams) {
        var conn = redisConnectionFactory.getConnection();
        Map<byte[],byte[]> mp = new HashMap<>();
        mp.put("handler".getBytes(),handlerName.getBytes());
        JSONObject info = new JSONObject();
        info.put("spiderName",spiderName);
        info.put("fault",JSONObject.toJSONString(fault));
        if(requestParams != null) {
            JSONObject metaInfo = new JSONObject();
            metaInfo.put("url",requestParams.getUrl());
            metaInfo.put("method",requestParams.getMethod().name());
            info.put("requestParams",metaInfo);
        }
        mp.put("errorInfo".getBytes(),info.toJSONString().getBytes());
        conn.xAdd(streamName.getBytes(),mp);
        conn.close();
    }
}

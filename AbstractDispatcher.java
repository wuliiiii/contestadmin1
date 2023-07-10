package org.algorithmcontestdatacollect.crawlerendpoint2.Dispatcher;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.IHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractDispatcher {
    public final static Logger logger = LoggerFactory.getLogger(AbstractDispatcher.class);
    protected abstract String getSpider();
    protected abstract String getHandler();
    protected abstract String getStream();

    public void dispatch(ApplicationContext context,JSONArray params){
        context.getBean(getHandler(), IHandler.class).handleRequest(context.getBean(getSpider(), Spider.class),params.toString());
    }
}

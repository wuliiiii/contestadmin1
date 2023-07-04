package org.algotithmcontestdatacollect.crawlerdispatcher.Dispatcher;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class DispatcherFactory {
    public final static Logger logger = LoggerFactory.getLogger(DispatcherFactory.class);
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private ApplicationContext applicationContext;
    public DispatcherFactory() {

    }

    public void dispatch(String name , JSONArray params){
        ((AbstractDispatcher) applicationContext.getBean(name)).dispatch(redisConnectionFactory,params);
    }
//    @PostConstruct
//    void init(){
//        for(String s:applicationContext.getBeanDefinitionNames()){
//            if(s.startsWith("Dispatcher")){
//                mp.put(s,(AbstractDispatcher) applicationContext.getBean(s));
//            }
//        }
//    }
}

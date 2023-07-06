package org.algotithmcontestdatacollect.crawlerdispatcher.Dispatcher;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.serializer.RedisSerializer;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractDispatcher {
    public final static Logger logger = LoggerFactory.getLogger(AbstractDispatcher.class);
    protected abstract String getSpider();
    protected abstract String getHandler();
    protected abstract String getStream();

    public void dispatch(RedisConnectionFactory redisConnectionFactory,JSONArray params){
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        Map<String,String> mp = new HashMap<>();
        mp.put("spider",getSpider());
        mp.put("handler",getHandler());
        mp.put("params",params.toString());
        StringRecord stringRecord = StringRecord.of(mp).withStreamKey(getStream());
        RecordId recordId = redisConnection.xAdd(stringRecord.serialize(RedisSerializer.string()));
        if (recordId!=null){
            logger.info("成功推送:{}-{},{}",getSpider(),getHandler(),recordId.toString() );
        }else{
            logger.error("推送失败:{}-{},{}",getSpider(),getHandler(),params.toString());
        }
    }
}

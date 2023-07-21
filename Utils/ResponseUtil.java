package org.algorithmcontestdatacollect.crawlerendpoint2.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ResponseUtil {
    public static String JSONReturn(int status){
        JSONObject ret = new JSONObject();
        ret.put("code",status);
        return ret.toJSONString();
    }
    public static String JSONReturn(int status, String msg){
        JSONObject ret = new JSONObject();
        ret.put("code",status);
        ret.put("msg",msg);
        return ret.toJSONString();
    }
    public static String JSONReturn(int status, JSONObject result){
        JSONObject ret = new JSONObject();
        ret.put("code",status);
        ret.put("result",result);
        return ret.toJSONString();
    }
    public static String JSONReturn(int status, JSONArray result){
        JSONObject ret = new JSONObject();
        ret.put("code",status);
        ret.put("result",result);
        return ret.toJSONString();
    }

}

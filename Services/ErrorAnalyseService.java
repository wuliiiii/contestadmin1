package org.algorithmcontestdatacollect.crawlerendpoint2.Services;

import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.OtherEntities.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.OtherEntities.RequestParams;
import org.springframework.stereotype.Service;

@Service
public class ErrorAnalyseService {
    public Fault getFaultFromResult(String result) {
        JSONObject json = JSONObject.parseObject(result);
        return JSONObject.toJavaObject(json.getJSONObject("fault"),Fault.class);
    }
    public RequestParams getRequestParamsFromResult(String result) {
        JSONObject json = JSONObject.parseObject(result);
        if(!json.containsKey("requestParams")) {
            return null;
        }
        return JSONObject.toJavaObject(json.getJSONObject("requestParams"),RequestParams.class);
    }
}

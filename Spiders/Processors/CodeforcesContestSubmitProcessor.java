package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;

import java.util.HashMap;
import java.util.Map;

public class CodeforcesContestSubmitProcessor implements IProcessor {
    @Override
    public void process(Task task) {
        JSONObject main;
        try{
            main = JSON.parseObject(task.getResponseDetail());
        }catch (Exception e) {
            task.processError(new Fault("process","parseFail","Parse json fail"));
            return;
        }
        try {
            if (main.getString("status").equals("OK")){
                JSONArray contestSubmitList = main.getJSONArray("result"); // 提取result字段
                JSONObject result = new JSONObject();
                result.put("contestSubmitList",contestSubmitList);
                String url = task.getRequestParams().getUrl();
                result.put("handler",url.split("&handle=")[1]);
                Map<String,Object> ret = new HashMap<>();
                ret.put("handler","codeforcesContestSubmitResultHandler");//指定Dispatcher的handler，与指定Dispatcher相似,首字母小写CodeforcesRatingChangeContestResultHandler变成codeforcesRatingChangeContestResultHandler
                ret.put("result",result);//爬取的结果放到result中
                task.processSuccess(ret);
            }else{
                task.processError(new Fault("process","statusError","status is "+ main.getString("status")));
            }
        }catch (Exception e) {
            task.processError(new Fault("process","parse Error",e.getMessage()));
        }

    }
}

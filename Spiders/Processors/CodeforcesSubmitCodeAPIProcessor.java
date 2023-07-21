package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;

import java.util.HashMap;
import java.util.Map;

public class CodeforcesSubmitCodeAPIProcessor implements IProcessor{

    @Override
    public void process(Task task) {
        JSONObject main;
        try {
           main = JSON.parseObject(task.getResponseDetail());// 根据返回的页面使用JSON来处理
        }catch (Exception e) {
            task.processError(new Fault("process","parseFail","Parse json fail"));
            return;
        }
        try{
            JSONObject result = new JSONObject();
            result.put("submitCodes", main.getString("source"));
            result.put("sid", main.getString("href").split("/")[4]); //在这里提前获取提交记录，方便之后操作
            Map<String,Object> ret = new HashMap<>();
            ret.put("handler", "codeforcesSubmitCodeResultHandler");//指定Dispatcher的handler，与指定Dispatcher相似,首字母小写CodeforcesRatingChangeContestResultHandler变成codeforcesRatingChangeContestResultHandler
            ret.put("result", result);//爬取的结果放到result中
            task.processSuccess(ret);
        }catch (Exception e){
            task.processError(new Fault("process","parse Error",e.getMessage()));
        }
    }
}

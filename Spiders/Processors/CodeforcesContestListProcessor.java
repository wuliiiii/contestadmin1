package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CodeforcesContestListProcessor implements IProcessor{
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeforcesContestListProcessor.class);
    @Override
    public void process(Task task) {
        LOGGER.info("start handle Codeforces Contest List Page");
        JSONObject main;
        try{
            main = JSONObject.parseObject(task.getResponseDetail());
        }catch (Exception e) {
            task.processError(new Fault("process","parseFail","Parse json fail"));
            LOGGER.error("Codeforces Contest List parse fail");
            return;
        }
        if (main.getString("status").equals("OK")){
            Map<String,Object> result = new HashMap<>();
            try{
                JSONArray contestList = main.getJSONArray("result");
                result.put("handler","codeforcesContestListResultHandler");
                result.put("result",contestList);
                task.processSuccess(result);
            }catch (Exception e) {
                task.processError(new Fault("process","parseFail","Parse Result fail"));
            }
        }else{
            task.processError(new Fault("process","processFail","Codeforces Contest List parse fail"));
            LOGGER.error("Codeforces Contest List parse fail");
        }
    }
}

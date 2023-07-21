package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CodeforcesContestTotalQuestionProcessor implements IProcessor{
    Logger logger = LoggerFactory.getLogger(CodeforcesContestTotalQuestionProcessor.class);
    @Override
    public void process(Task task) {
//        var parser = Jsoup.parse(task.getResponseDetail());
//        Elements trs;
//        try{
//            trs = parser.getElementsByClass("problems").get(0).getElementsByTag("tr");
//        }catch (Exception e){
//            task.processError(new Fault("process","pageError",e.getMessage()));
//            return;
//        }
//        var problemSet = new JSONObject();
//        var cid = task.getRequestParams().getUrl().split("/")[4];
//        var problemList = new JSONArray();
//        for(int i = 1;i<trs.size();i++) {
//            JSONObject problem = new JSONObject();
//            var tr = trs.get(i);
//            var tds = tr.getElementsByTag("td");
//            var idx = tds.get(0).text();
//            var problemName = tds.get(1).getElementsByTag("a").get(0).text();
//            problem.put("idx",idx);
//            problem.put("name",problemName);
//            problemList.add(problem);
//        }
//        problemSet.put("cid",cid);
//        problemSet.put("problemList",problemList);
//        task.processSuccess(problemSet);
        var result = JSONObject.parseObject(task.getResponseDetail());
        if(!result.getString("status").equals("OK")){
            task.processError(new Fault("process","statusError","status is "+ result.getString("status")));
        }
        Map<String,Object> ret = new HashMap<>();
        ret.put("handler","codeforcesContestTotalQuestionResultHandler");
        ret.put("result",result.getJSONObject("result").getJSONArray("problems"));
        task.processSuccess(ret);
    }

}

package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.algorithmcontestdatacollect.crawlerendpoint2.Utils.RegexUtil;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AtcoderContestSubmitProcessor implements IProcessor{
    private Logger LOGGER = LoggerFactory.getLogger(AtcoderContestSubmitProcessor.class);

    @Override
    public void process(Task task) {
        var parser = Jsoup.parse(task.getResponseDetail());
        var tableResponsive = parser.getElementsByClass("table-responsive");
        if (tableResponsive.size() == 0){
            LOGGER.info("AtcoderContestSubmitProcessor: no submit");
            return;
        }
        var cSubmit = new JSONObject();
        var contestSubmit = new JSONArray();
        try{
            var url = parser
                    .getElementById("main-container")
                    .getElementsByClass("table-responsive").get(0)
                    .getElementsByTag("thead").get(0)
                    .getElementsByTag("tr").get(0)
                    .getElementsByTag("th").get(0)
                    .getElementsByTag("a").get(0)
                    .attr("href");
            cSubmit.put("nickName", url.split("/")[2]);
            cSubmit.put("acId", url.split("=")[2].split("&")[0]);
        }catch (Exception e) {
            task.processError(new Fault("process","pageError",e.getMessage()));
            return;
        }
        try {
            var trs = parser
                    .getElementById("main-container")
                    .getElementsByClass("table-responsive").get(0)
                    .getElementsByTag("tbody").get(0)
                    .getElementsByTag("tr");
            for(var tr : trs){
                JSONObject submit = new JSONObject();
                var tds = tr.getElementsByTag("td");
                String ctime = tds.get(0).text();
                // LOGGER.info("ctime = "+ ctime);
                try {
                    submit.put("ctime", RegexUtil.timeStringToTimeStamp(ctime)); //修改格式
                }catch (Exception e){
                    submit.put("cTime",0);
                }
                submit.put("status", tds.get(6).text());
                submit.put("language", tds.get(3).text());
                submit.put("index", tds.get(1).text().split("-")[0].strip());
                submit.put("sid", RegexUtil.parserSid(tds.get(tds.size()-1).getElementsByTag("a").get(0).attr("href")));
                contestSubmit.add(submit);
            }
        }catch (Exception e) {
            task.processError(new Fault("process","pageError",e.getMessage()));
            return;
        }
        cSubmit.put("contestSubmit", contestSubmit);
        var baseUrl = task.getRequestParams().getUrl().split("=")[0] +"="+ task.getRequestParams().getUrl().split("=")[1];
        var nowPage = Integer.parseInt((task.getRequestParams().getUrl().split("=")[2]));
        task.getSpider().addTask(new Task(new RequestParams(baseUrl +"="+ (nowPage + 1))));
        Map<String,Object> res = new HashMap<>();
        res.put("handler", "atcoderContestSubmitResultHandler");
        res.put("result", cSubmit);
        task.processSuccess(res);
    }
}

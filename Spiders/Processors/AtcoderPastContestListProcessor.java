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

public class AtcoderPastContestListProcessor implements IProcessor{
    private static final Logger LOGGER = LoggerFactory.getLogger(AtcoderPastContestListProcessor.class);
    @Override
    public void process(Task task) {
        try {
            var parser = Jsoup.parse(task.getResponseDetail());
            var contestList = new JSONArray();
            var tableResponsive = parser.getElementsByClass("table-responsive");
            if (tableResponsive.size() == 0){
                LOGGER.info("AtcoderPastContestListProcessor: no contest");
                return;
            }
            var trs = tableResponsive.get(0)
                    .getElementsByTag("tbody").get(0)
                    .getElementsByTag("tr");
            for (var tr : trs) {
                JSONObject contest = new JSONObject();
                var tds = tr.getElementsByTag("td");
                try{
                    contest.put("startTime", RegexUtil.timeStringToTimeStamp(tds.get(0).getElementsByTag("time").get(0).text()));
                }catch (Exception e){
                    contest.put("startTIme",0);
                }
                contest.put("nickName",RegexUtil.parserNickname(tds.get(1).getElementsByTag("a").get(0).attr("href")));
                contest.put("name",tds.get(1).getElementsByTag("a").get(0).text());
                contest.put("duration",RegexUtil.parserDuration(tds.get(2).text()));
                contestList.add(contest);
            }
            var baseUrl = task.getRequestParams().getUrl().split("=")[0];
            var nowPage = Integer.parseInt(task.getRequestParams().getUrl().split("=")[1]);
            task.getSpider().addTask(new Task(new RequestParams(baseUrl +"="+ (nowPage + 1))));
            Map<String,Object> res = new HashMap<>();
            res.put("handler","atcoderContestListResultHandler");
            res.put("result",contestList);
            task.processSuccess(res);
        }catch (Exception e) {
            task.processError(new Fault("process","pageError",e.getMessage()));
        }

    }
}

package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.algorithmcontestdatacollect.crawlerendpoint2.Utils.RegexUtil;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;

public class AtcoderPresentContestListProcessor implements IProcessor {

    @Override
    public void process(Task task) {
        try {
            var parser = Jsoup.parse(task.getResponseDetail());
            var contestList = new JSONArray();
            var trs = parser
                    .getElementById("contest-table-upcoming")
                    .getElementsByClass("table-responsive").get(0)
                    .getElementsByTag("tbody").get(0)
                    .getElementsByTag("tr");
            for (var tr : trs) {
                JSONObject contest = new JSONObject();
                var tds = tr.getElementsByTag("td");
                try {
                    contest.put("startTime", RegexUtil.timeStringToTimeStamp(tds.get(0).getElementsByTag("time").get(0).text()));
                } catch (Exception e) {
                    contest.put("startTIme", 0);
                }
                contest.put("nickName", RegexUtil.parserNickname(tds.get(1).getElementsByTag("a").get(0).attr("href")));
                contest.put("name", tds.get(1).getElementsByTag("a").get(0).text());
                contest.put("duration", RegexUtil.parserDuration(tds.get(2).text()));
                contestList.add(contest);
            }
            Map<String, Object> ret = new HashMap<>();
            ret.put("handler", "atcoderContestListResultHandler");
            ret.put("result", contestList);
            task.processSuccess(ret);
        } catch (Exception e) {
            task.processError(new Fault("process", "pageError", e.getMessage()));
        }

    }
}

package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.algorithmcontestdatacollect.crawlerendpoint2.Utils.RegexUtil;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AtcoderStucontestProcessor implements IProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtcoderPastContestListProcessor.class);

    @Override
    public void process(Task task) {
        try{
            var parser = Jsoup.parse(task.getResponseDetail());
            var acid = task.getRequestParams().getUrl().toString().split("/")[4];
            var contestList = new JSONArray();
            var aContestList = new JSONObject();
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
                    contest.put("date", RegexUtil.timeStringToTimeStamp(tds.get(0).getElementsByTag("time").get(0).text()));
                }catch (Exception e){}
                contest.put("nickName",RegexUtil.parserNickname(tds.get(1).getElementsByTag("a").get(0).attr("href")));
                contest.put("name",tds.get(1).getElementsByTag("a").get(0).text());
                contest.put("rank",tds.get(2).getElementsByTag("td").get(0).text());
                contest.put("performance",tds.get(3).getElementsByTag("td").get(0).text());
                contest.put("rating",tds.get(4).text().equals("±0")||tds.get(4).text().equals("-")?"0":tds.get(4).text());
                contest.put("diff",tds.get(5).text().equals("±0")||tds.get(5).text().equals("-")?"0":tds.get(5).text()) ;
                contestList.add(contest);
            }
            aContestList.put("acid",acid);
            aContestList.put("contestList",contestList);
            Map<String,Object> ret = new HashMap<>();
            ret.put("handler","atcoderStucontestResultHandler");
            ret.put("result",aContestList);
            task.processSuccess(ret);
        }catch (Exception e){
            task.processError(new Fault("process", "pageError", e.getMessage()));
        }

    }
}

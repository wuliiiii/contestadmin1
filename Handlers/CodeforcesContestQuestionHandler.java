package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;
//接收一个包含比赛ID和问题索引的参数，然后构造一个相应的URL，
// 创建一个爬取任务，并将任务添加到爬虫对象中进行处理。

@Service("CodeforcesContestQuestionHandler")
public class CodeforcesContestQuestionHandler implements IHandler {
    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray jsonArray = JSONArray.parseArray(params);
        Long cid = jsonArray.getLong(0);
        String index = jsonArray.getString(1);
        RequestParams requestParams = new RequestParams("https://codeforces.com/contest/"+cid+"/problem/"+index);
        spider.addTask(new Task(requestParams));
    }
}

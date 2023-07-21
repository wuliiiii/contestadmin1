package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;
//创建一个爬取任务，目的是获取Codeforces比赛问题的总览信息。
// 通过构造特定的API URL，将任务添加到爬虫对象中进行处理
@Service("CodeforcesContestTotalQuestionHandler")
public class CodeforcesContestTotalQuestionHandler implements IHandler {
    @Override
    public void handleRequest(Spider spider, String params) {
        RequestParams requestParams = new RequestParams("https://codeforces.com/api/problemset.problems");
        spider.addTask(new Task(requestParams));
    }
}

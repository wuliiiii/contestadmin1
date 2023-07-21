package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//在处理"CodeforcesContestListHandler"的请求时，
// 在指定的URL上添加一个任务，以爬取Codeforces网站上比赛列表的API数据。
@Service("CodeforcesContestListHandler")
public class CodeforcesContestListHandler implements IHandler{
    @Override
    public void handleRequest(Spider spider, String params) {
        RequestParams requestParams = new RequestParams("https://codeforces.com/api/contest.list?gym=false");
        spider.addTask(new Task(requestParams));
    }
}

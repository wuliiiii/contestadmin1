package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;


import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;
//接收一个包含用户handle的参数，构造相应的URL，创建一个爬取任务，并将任务添加到爬虫对象中进行处理。
// 该任务的目的是获取指定用户在Codeforces上的比赛记录

@Service("CodeforcesContestRecordHandler")
public class CodeforcesContestRecordHandler implements IHandler{
    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        RequestParams requestParams = new RequestParams("https://codeforces.com/api/user.status?handle="+JSONParams.getString(0));
        spider.addTask(new Task(requestParams));
    }
}

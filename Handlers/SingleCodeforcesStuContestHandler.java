package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//接收包含Codeforces用户handle的参数，构造相应的URL，
// 创建一个爬取任务，并将任务添加到爬虫对象中进行处理。
// 该任务的目的是获取指定用户的学生比赛信息
@Service("SingleCodeforcesStuContestHandler")
public class SingleCodeforcesStuContestHandler implements IHandler{

    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        String cfid = JSONParams.getString(0); // 使用JSONArray获取参数
        spider.addTask(new Task(new RequestParams("https://codeforces.com/api/user.rating?handle="+cfid)));
    }
}

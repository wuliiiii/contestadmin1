package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//是在处理"AtcoderStucontestHandler"的请求时，
// 在指定的URL上添加一个任务，以爬取Atcoder网站上特定用户的比赛历史页面。
@Service("AtcoderStuContestHandler")
public class AtcoderStucontestHandler implements IHandler{

    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        String acid = JSONParams.getString(0);
        spider.addTask(new Task(new RequestParams("https://atcoder.jp/users/"+acid+"/history")));
    }
}

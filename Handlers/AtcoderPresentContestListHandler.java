package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//处理"AtcoderPastContestListHandler"的请求时，
// 在指定的URL上添加一个任务，以爬取Atcoder网站上当前比赛的列表页面。
@Service("AtcoderPresentContestListHandler")
public class AtcoderPresentContestListHandler implements IHandler{
    @Override
    public void handleRequest(Spider spider, String params) {
        spider.addTask(new Task(new RequestParams("https://atcoder.jp/contests")));
    }
}

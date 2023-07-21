package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//接收包含AtCoder提交记录ID和比赛昵称的参数，构造相应的URL，创建一个爬取任务，
// 并将任务添加到爬虫对象中进行处理。该任务的目的是获取指定比赛中特定提交记录的代码。
@Service("SingleAtcoderSubmitCodeHandler")
public class SingleAtcoderSubmitCodeHandler implements IHandler {



    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        Long sid = JSONParams.getLong(0); // 使用JSONArray获取参数
        String nickname = JSONParams.getString(1);
        spider.addTask(new Task(new RequestParams("https://atcoder.jp/contests/"+nickname+"/submissions/"+sid)));
    }
}

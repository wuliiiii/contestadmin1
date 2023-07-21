package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;

//在爬虫中执行特定的任务。通过接口的方式，
// 可以实现不同的任务处理类，并在爬虫中灵活地使用不同的处理逻辑。
public interface IHandler {
    void handleRequest(Spider spider,String params);
}

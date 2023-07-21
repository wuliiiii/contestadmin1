package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers;

import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Spider;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//通过发送HTTP请求获取Codeforces提交记录页面，
// 解析页面获取Csrf-Token，并构造包含表单数据和请求头数据的请求参数，
// 然后将任务添加到爬虫对象中进行处理。该任务的目的是向Codeforces提交特定提交记录的代码
@Service("SingleCodeforcesSubmitCodeAPIHandler")
public class SingleCodeforcesSubmitCodeAPIHandler implements IHandler {
    RestTemplate restTemplate;
    public SingleCodeforcesSubmitCodeAPIHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public void handleRequest(Spider spider, String params) {
        JSONArray JSONParams = JSONArray.parseArray(params);
        Long sid = JSONParams.getLong(0); // 使用JSONArray获取参数
        Long cid = JSONParams.getLong(1);
        String codeforcesId=JSONParams.getString(2);
        String url="https://codeforces.com/submissions/"+codeforcesId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); // 替换为要获取的URL
        if (response.getStatusCode().is2xxSuccessful()) {
            String html = response.getBody();
            Document document = Jsoup.parse(html);
            Elements metaTags = document.select("meta[name=X-Csrf-Token]");
            if (!metaTags.isEmpty()) {
                Element metaTag = metaTags.first();
                String content = metaTag.attr("content");
                Map<String,String> forms = new HashMap<>();
                forms.put("submissionId",sid.toString());
                forms.put("csrf_token",content);
                HashMap<String, String> header = new HashMap<>();
                header.put("referer",url);
                header.put("content-type","application/x-www-form-urlencoded; charset=UTF-8");
                RequestParams requestParams = new RequestParams("https://codeforces.com/data/submitSource", HttpMethod.POST,forms,header,null);
                spider.addTask(new Task(requestParams));
            }
        }
    }
}

package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.jsoup.Jsoup;

import java.util.HashMap;

public class AtcoderSubmitCodeProcessor implements IProcessor {
    @Override
    public void process(Task task) {
        try{
            var parser = Jsoup.parse(task.getResponseDetail());
            var submitCode = parser
                    .getElementById("submission-code")
                    .getElementsByClass("linenums").text();
            JSONObject result = new JSONObject();
            String url = task.getRequestParams().getUrl();
            result.put("sid", url.split("submissions/")[1]);
            result.put("submitCode", submitCode);
            var ret = new HashMap<String,Object>();
            ret.put("handler", "atcoderSubmitCodeResultHandler");
            ret.put("result", result);
            task.processSuccess(ret);
        }catch (Exception e) {
            task.processError(new Fault("process", "pageError", e.getMessage()));
        }

    }
}

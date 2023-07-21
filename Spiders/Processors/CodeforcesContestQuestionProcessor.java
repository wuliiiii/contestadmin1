package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeforcesContestQuestionProcessor implements IProcessor{
    Logger logger = LoggerFactory.getLogger(CodeforcesContestQuestionProcessor.class);
    @Override
    public void process(Task task) {
        Document parser = Jsoup.parse(task.getResponseDetail());
        JSONObject ret = new JSONObject();
        ret.put("tags",getTagList(parser));
        ret.put("rating",getRating(parser));
    }

    private JSONArray getTagList(Document parser){
        return null;
    }

    private Integer getRating(Document parser){
        return null;
    }
}

package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.ErrorHandlers;

import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.SpiderLogRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Services.ErrorAnalyseService;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.SpiderLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CodeforcesRatingChangeContestErrorHandler")
public class CodeforcesRatingChangeContestErrorHandler extends AbstractHandler {
    @Autowired
    ErrorAnalyseService errorAnalyseService;
//这个子类用于处理Codeforces比赛中的积分变化错误信息，
// 并将相关日志保存到数据库中。
    @Autowired
    SpiderLogRepository spiderLogRepository;
    @Override
    public void handle(String result) {
        var fault = errorAnalyseService.getFaultFromResult(result);
        var requestParams = errorAnalyseService.getRequestParamsFromResult(result);
        var spiderName = JSONObject.parseObject(result).getString("spiderName");
        var log = SpiderLogEntity.fromFaultAndRequestParam(fault,requestParams,spiderName);
        spiderLogRepository.saveAndFlush(log);
    }
}

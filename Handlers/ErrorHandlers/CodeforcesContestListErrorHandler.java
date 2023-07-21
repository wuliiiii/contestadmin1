package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.ErrorHandlers;

import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.SpiderLogRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Services.ErrorAnalyseService;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.SpiderLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
////。该子类用于处理Codeforce比赛错误列表信息，
//// 并将相关日志保存到数据库中。
@Service("CodeforcesContestListErrorHandler")
public class CodeforcesContestListErrorHandler extends AbstractHandler {
    private Logger logger = LoggerFactory.getLogger(CodeforcesContestListErrorHandler.class);
    @Autowired
    ErrorAnalyseService errorAnalyseService;

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

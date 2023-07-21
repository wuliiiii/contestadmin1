package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcContestRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcSubmitCodeRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcSubmitRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcSubmitcodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public class AtcoderSubmitCodeResultHandler extends AbstractHandler {
    @Autowired
    public AcSubmitRepository acSubmitRepository;
    @Autowired
    public AcContestRepository acContestRepository;
    @Autowired
    private AcSubmitCodeRepository acSubmitCodeRepository;

    @Override
    public void handle(String result) {
        JSONObject res = JSONObject.parseObject(result);
        Long sid = res.getLong("sid");
        var submitCode = res.getString("submitCode");
        var acSubmitEntity = acSubmitRepository.getAcSubmitEntityBySid(sid);
        if(acSubmitEntity == null){
            LOGGER.error("题目{ "+sid+" }不在数据库中");
            return;
        }
        if(!acSubmitCodeRepository.existsAcSubmitCodeEntityBySid(sid)) {
            AcSubmitcodeEntity acSubmitcodeEntity = new AcSubmitcodeEntity();
            acSubmitcodeEntity.setSid(sid);
            acSubmitcodeEntity.setCode(submitCode);
            acSubmitCodeRepository.save(acSubmitcodeEntity);
        }

    }
}

package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcAccountRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcContestRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcSubmitRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcContestEntity;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcSubmitEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AtcoderContestSubmitResultHandler extends AbstractHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(AtcoderContestSubmitResultHandler.class);
    @Autowired
    public AcSubmitRepository acSubmitRepository;
    @Autowired
    public AcContestRepository acContestRepository;
    @Autowired
    private AcAccountRepository acAccountRepository;

    @Override
    public void handle(String result) {
        JSONObject cSubmit = (JSONObject) JSON.parse(result);
        var contestSubmit = cSubmit.getJSONArray("contestSubmit");
        var nickname = cSubmit.getString("nickName");
        var acId = cSubmit.getString("acId");
        var cid = acContestRepository.getAcContestEntityByNickname(nickname).getId();
        var acid = acAccountRepository.getAcAccountEntityByAtcoderId(acId).getId();
        LOGGER.info("数组大小"+contestSubmit.size());
        List<AcContestEntity> AtcoderContestList = acContestRepository.findAll();
        Map<Long,AcContestEntity> mp = new HashMap<>();
        for(var contest:AtcoderContestList){
            mp.put(contest.getId(),contest);
        }
        for (int i = 0;i<contestSubmit.size();i++){
            var submit = contestSubmit.getJSONObject(i);
            submit.put("cid", cid);
            submit.put("acid", acid);
            byte isAfter = 1;
            if(mp.containsKey(cid) && mp.get(cid).getEndTimeStamp() >= submit.getLong("ctime")){
                isAfter = 0;
            }
            submit.put("isAfter", isAfter);
            try{
                if(!acSubmitRepository.existsAcSubmitEntityBySid(submit.getLong("sid"))){
                    LOGGER.info("添加AtcoderSubmit{"+acId+"}/{"+nickname+"}");
                    acSubmitRepository.saveAndFlush(AcSubmitEntity.fromJSONObject(submit));
                }
            }catch (Exception e){
                LOGGER.error(e.toString());
            }
        }

    }
}

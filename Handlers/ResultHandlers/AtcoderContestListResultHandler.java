package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcContestRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcContestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtcoderContestListResultHandler extends AbstractHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(AtcoderContestListResultHandler.class);
    @Autowired
    public AcContestRepository acContestRepository;
    @Override
    public void handle(String result) {
        JSONArray contestList = (JSONArray) JSON.parse(result);
        for (int i = 0;i<contestList.size();i++){
            var contest = contestList.getJSONObject(i);
            try{
                if (!acContestRepository.existsAcContestEntityByNicknameAndStartTimeStamp(contest.getString("nickName"),contest.getLong("startTime"))){
                    if(!acContestRepository.existsAcContestEntityByNickname(contest.getString("nickName"))) {
                        LOGGER.info("添加AtcoderContest{}",contest.getString("nickName"));
                        acContestRepository.saveAndFlush(AcContestEntity.fromJSONObject(contest));
                    }else{
                        var src = acContestRepository.getAcContestEntityByNickname(contest.getString("nickName"));
                        src.setStartTimeStamp(contest.getLong("startTime"));
                        src.setDuaration(contest.getLong("duration"));
                        acContestRepository.saveAndFlush(src);
                    }
                }
            }catch (Exception e) {
                LOGGER.error(e.toString());
            }

        }
    }
}

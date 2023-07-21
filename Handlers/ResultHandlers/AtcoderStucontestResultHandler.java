package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcAccountRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcContestRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcStucontestRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcContestEntity;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcStucontestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AtcoderStucontestResultHandler extends AbstractHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(AtcoderStucontestResultHandler.class);
    @Autowired
    private AcStucontestRepository acStucontestRepository;
    @Autowired
    private AcContestRepository acContestRepository;
    @Autowired
    private AcAccountRepository acAccountRepository;

    @Override
    public void handle(String result) {
        JSONObject res = JSONObject.parseObject(result);
        String atcoderID = res.getString("acid");

        List<AcContestEntity> AtcoderContestList = acContestRepository.findAll();
        Map<String, AcContestEntity> mp = new HashMap<>();
        JSONArray stuContestList = res.getJSONArray("contestList");
        var acAccountEntity =
                acAccountRepository.getAcAccountEntityByAtcoderId(atcoderID); // 因为不是直接用cf账户存放，需要获取对应cf账户的ID
        if (acAccountEntity == null) {
            LOGGER.error("账户{}:不在数据库中", atcoderID);
            return;
        }
        Long acid = acAccountEntity.getId();
        for (var contest : AtcoderContestList) {
            mp.put(contest.getNickname(), contest);
        }
        int finalRating = 0;
        for (int i = 0; i < stuContestList.size(); i++) {
            JSONObject stuContest = stuContestList.getJSONObject(i);
            String nickname = stuContest.getString("nickName");
            Long cid = mp.get(nickname).getId();
            int rank = Integer.parseInt(stuContest.getString("rank"));
            int diff = Integer.parseInt(stuContest.getString("diff"));
            int rating = Integer.parseInt(stuContest.getString("rating"));
            if (!acStucontestRepository.existsAcStucontestEntityByAcidAndCid(acid,cid)) {
                AcStucontestEntity acStucontestEntity = new AcStucontestEntity();
                acStucontestEntity.setId(null);
                acStucontestEntity.setCid(cid);
                acStucontestEntity.setAcid(acid);
                acStucontestEntity.settRank(rank);
                acStucontestEntity.setDiff(diff);
                acStucontestEntity.setRating(rating);
                acStucontestRepository.save(acStucontestEntity);
                acStucontestRepository.flush();
                if(rating!=0){
                    finalRating = rating;
                    Long fRating = Long.parseLong(String.valueOf(finalRating));
                    acAccountEntity.setRating(fRating);
                    acAccountRepository.save(acAccountEntity);
                    acAccountRepository.flush();
                }

            }
        }

    }
}

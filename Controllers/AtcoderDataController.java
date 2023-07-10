package org.algotithmcontestdatacollect.displaybackend.Controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.Entities.AtcoderEntities.*;
import org.algotithmcontestdatacollect.displaybackend.Repositories.AcRepositories.*;
import org.algotithmcontestdatacollect.displaybackend.Repositories.UserRepository;
import org.algotithmcontestdatacollect.displaybackend.Services.RatingChangeService;
import org.algotithmcontestdatacollect.displaybackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@RestController
public class AtcoderDataController {
    @Autowired
    AcStucontestWithCinfoUserinfoRepository acStucontestWithCinfoUserinfoRepository;
    @Autowired
    AcContestWithParticipateRepository acContestWithParticipateRepository;

    @Autowired
    AcAccountTotalInfoRepository acAccountTotalInfoRepository;

    @Autowired
    AcStucontestWithUserinfoRepository acStucontestWithUserinfoRepository;

    @Autowired
    AcSubmitWithUserinfoRepository acSubmitWithUserinfoRepository;

    @Autowired
    AcSubmitcodeRepository acSubmitcodeRepository;

    @Autowired
    RatingChangeService ratingChangeService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/atcoder/list")
    public String getAllContest(){
        List<AcContestWithParticipateEntity> l;
        try{
            l = acContestWithParticipateRepository.findAll();
        }catch (Exception E){
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/atcoder/rank")
    public String getRank() {
        List<AcAccountTotalInfoEntity> l;
        try{
            l = acAccountTotalInfoRepository.findAll();
        }catch (Exception E){
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/atcoder/participateList/{cid}")
    public String getContestParticipate(@PathVariable Long cid) {
        List<AcStucontestWithUserinfoEntity> l;
        try{
            l = acStucontestWithUserinfoRepository.getAcStucontestWithUserinfoEntitiesByCid(cid);
        }catch (Exception E){
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/atcoder/submitList/{cid}")
    public String getContestSubmit(@PathVariable Long cid) {
        List<AcSubmitWithUserinfoEntity> l;
        try{
            l = acSubmitWithUserinfoRepository.getAcSubmitWithUserinfoEntitiesByCid(cid);
        }catch (Exception E){
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(l));
    }
    @GetMapping("/api/atcoder/submitcode/{sid}")
    public String getSubmitCode(@PathVariable Long sid) {
        var code = acSubmitcodeRepository.findById(sid);
        if (code.isPresent()) {
            return ResponseUtil.JSONReturn(200,(JSONObject) JSONObject.toJSON(code.get()));
        }else{
            return ResponseUtil.JSONReturn(404,"数据库尚未收录该提交代码");
        }
    }

    @GetMapping("/api/atcoder/TopTen")
    public String getTopTen() {
        List<AcAccountTotalInfoEntity> l;
        try{
            l = acAccountTotalInfoRepository.getTopByRatingOrderByRatingDesc(10);
        }catch (Exception E){
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/atcoder/contest/{cid}")
    public String getSpecifiedContest(@PathVariable Long cid) {
        try{
            var op = acContestWithParticipateRepository.findById(cid);
            if (op.isPresent()) {
                return ResponseUtil.JSONReturn(200, (JSONObject) JSONObject.toJSON(op.get()));
            }
        }catch (Exception E){
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(404,"不存在该比赛");
    }
    @GetMapping("/api/atcoder/participate/{atcoder_id}")
    public String getUserParticipate(@PathVariable String atcoder_id) {
        try{
            List<AcStucontestWithCinfoUserinfoEntity> acStucontestWithUserinfoEntities = acStucontestWithCinfoUserinfoRepository.getAcStucontestWithCinfoUserinfoEntitiesByAtcoderId(atcoder_id);
            var retData = (JSONArray) JSONArray.toJSON(acStucontestWithUserinfoEntities);
            if (acStucontestWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, retData);
            }
        }catch (Exception E) {
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(404,"用户名不存在或无数据");
    }
    @GetMapping("/api/atcoder/afterSubmit/{atcoder_id}")
    public String getUserAfterSubmit(@PathVariable String atcoder_id) {
        try{
            List<AcSubmitWithUserinfoEntity> acSubmitWithUserinfoEntities = acSubmitWithUserinfoRepository.getAcSubmitWithUserinfoEntitiesByAtcoderIdAndIsAfter(atcoder_id,(byte) 1);
            if (acSubmitWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(acSubmitWithUserinfoEntities));
            }
        }catch (Exception E) {
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(404,"用户名不存在或无数据");
    }
    @GetMapping("/api/atcoder/contestSubmit/{atcoder_id}")
    public String getUserContestSubmit(@PathVariable String atcoder_id) {
        try{
            List<AcSubmitWithUserinfoEntity> acSubmitWithUserinfoEntities = acSubmitWithUserinfoRepository.getAcSubmitWithUserinfoEntitiesByAtcoderIdAndIsAfter(atcoder_id,(byte) 0);
            if (acSubmitWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(acSubmitWithUserinfoEntities));
            }
        }catch (Exception E) {
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(404,"用户名不存在或无数据");
    }

    @GetMapping("/api/atcoder/user/participate/{username}")
    public String getAccountParticipate(@PathVariable String username) {
        try{
            List<AcStucontestWithCinfoUserinfoEntity> acStucontestWithUserinfoEntities = acStucontestWithCinfoUserinfoRepository.getAcStucontestWithCinfoUserinfoEntitiesByUsername(username);
            var retData = (JSONArray) JSONArray.toJSON(acStucontestWithUserinfoEntities);
            if (acStucontestWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, retData);
            }
        }catch (Exception E) {
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(404,"用户名不存在或无数据");
    }
    @GetMapping("/api/atcoder/user/afterSubmit/{username}")
    public String getAccountAfterSubmit(@PathVariable String username) {
        try{
            List<AcSubmitWithUserinfoEntity> acSubmitWithUserinfoEntities = acSubmitWithUserinfoRepository.getAcSubmitWithUserinfoEntitiesByUsernameAndIsAfter(username,(byte) 1);
            if (acSubmitWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(acSubmitWithUserinfoEntities));
            }
        }catch (Exception E) {
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(404,"用户名不存在或无数据");
    }
    @GetMapping("/api/atcoder/user/contestSubmit/{username}")
    public String getAccountContestSubmit(@PathVariable String username) {
        try{
            List<AcSubmitWithUserinfoEntity> acSubmitWithUserinfoEntities = acSubmitWithUserinfoRepository.getAcSubmitWithUserinfoEntitiesByUsernameAndIsAfter(username,(byte) 0);
            if (acSubmitWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(acSubmitWithUserinfoEntities));
            }
        }catch (Exception E) {
            return ResponseUtil.JSONReturn(404,"数据库异常");
        }
        return ResponseUtil.JSONReturn(404,"用户名不存在或无数据");
    }

    @GetMapping("/api/atcoder/allUserMainAccountRatingChange")
    public String getAllUserMainAccountRatingChange() {
        var eldestTime = ratingChangeService.getTheEldestAtcoderParticipateStartTime();
        var timeList = ratingChangeService.generateDateRangeToNow(eldestTime);
        var userIdList = userRepository.getAllUserId();
        var retData = new JSONObject();
        for (int i = 0; i < timeList.size() - 1; i++) {
            retData.put(Integer.valueOf(timeList.get(i).getYear() +1900).toString() + "-" +
                            Integer.valueOf(timeList.get(i).getMonth() + 1).toString(),
                    JSONObject.toJSON(ratingChangeService.getAtcoderRatingByUserIdsAndTimePoint(userIdList, timeList.get(i + 1).getTime()/1000))
            );
        }
        return ResponseUtil.JSONReturn(200, retData);
    }

    @GetMapping("/api/atcoder/recentOneMonthContest")
    public String getRecentOneMonthContest() {
        var timeL = System.currentTimeMillis() / 1000 - 30 *24 * 60 * 60;
        var timeR = System.currentTimeMillis() / 1000;
        var cList = acContestWithParticipateRepository.getAcContestWithParticipateEntitiesByStartTimeStampBetween(timeL, timeR);
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(cList));
    }
}

package org.algotithmcontestdatacollect.displaybackend.Controllers;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import org.algotithmcontestdatacollect.displaybackend.Entities.TagEntities.TagUserMapEntity;
import org.algotithmcontestdatacollect.displaybackend.Repositories.TagRepositories.TagUserMapRepository;
import org.algotithmcontestdatacollect.displaybackend.Repositories.UserRepository;
import org.algotithmcontestdatacollect.displaybackend.Services.InContestSolveService;
import org.algotithmcontestdatacollect.displaybackend.Services.ParticipateCountService;
import org.algotithmcontestdatacollect.displaybackend.Services.RatingChangeService;
import org.algotithmcontestdatacollect.displaybackend.Services.SolveService;
import org.algotithmcontestdatacollect.displaybackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MonthlyRatingController {
    @Autowired
    InContestSolveService inContestSolveService;

    @Autowired
    RatingChangeService ratingChangeService;

    @Autowired
    SolveService solveService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagUserMapRepository tagUserMapRepository;

    @Autowired
    ParticipateCountService participateCountService;

    @GetMapping("/api/MonthlyRating/{startTime}/{endTime}")
    public String getAllMonthlyRating(@PathVariable Long startTime,@PathVariable Long endTime){
        List<Long> uids = userRepository.getAllUserId();
        var results = getStuListMonthlyRating(uids,startTime,endTime);
        JSONObject ret = new JSONObject();
        for(var st:results.entrySet()) {
            ret.put(st.getKey().toString(),st.getValue());
        }
        return ResponseUtil.JSONReturn(200,ret);
    }

    private Map<Long,JSONObject> getStuListMonthlyRating(List<Long> uids,Long startTime,Long endTime) {
        var cfChange = ratingChangeService.getCodeforcesSpanRatingChange(uids,startTime,endTime);
        var cfTotalSolve = solveService.getCodeforcesSolveByUserIdsAndTimePoint(uids,startTime,endTime);
        var cfInContestSolve = inContestSolveService.getCodeforcesSolveByUserIdsAndTimePoint(uids,startTime,endTime);
        var acChange = ratingChangeService.getAtcoderSpanRatingChange(uids,startTime,endTime);
        var acTotalSolve = solveService.getAtcoderSolveByUserIdsAndTimePoint(uids,startTime,endTime);
        var acInContestSolve = inContestSolveService.getAtcoderSolveByUserIdsAndTimePoint(uids,startTime,endTime);
        var cfRatings = ratingChangeService.getCodeforcesRatingByUserIdsAndTimePoint(uids,endTime);
        var acRatings = ratingChangeService.getAtcoderRatingByUserIdsAndTimePoint(uids,endTime);
        var cfparticipates = participateCountService.getCfTimeSpanParticipate(uids,startTime,endTime);
        var acparticipates = participateCountService.getAcTimeSpanParticipate(uids,startTime,endTime);
        var ret = new HashMap<Long,JSONObject>();
        for(var uid : uids) {
            var json = new JSONObject();
            json.put("cfRatingChange", cfChange.getOrDefault(uid, 0));
            json.put("acRatingChange", acChange.getOrDefault(uid, 0));
            json.put("cfInContestSolve", cfInContestSolve.getOrDefault(uid, 0));
            json.put("cfAfterSolve",cfTotalSolve.getOrDefault(uid,0) - json.getInteger("cfInContestSolve"));
            json.put("acInContestSolve", acInContestSolve.getOrDefault(uid, 0));
            json.put("acAfterSolve",acTotalSolve.getOrDefault(uid,0) - json.getInteger("acInContestSolve"));
            json.put("cfRating",cfRatings.getOrDefault(uid,0));
            json.put("acRating",acRatings.getOrDefault(uid,0));
            json.put("cfParticipate",cfparticipates.getOrDefault(uid,0));
            json.put("acParticipate",acparticipates.getOrDefault(uid,0));
            json.put("score",
                    cfChange.getOrDefault(uid, 0) + acChange.getOrDefault(uid, 0) +
                            cfTotalSolve.getOrDefault(uid,0)*5 +
                            acTotalSolve.getOrDefault(uid,0)*5 +
                            cfRatings.getOrDefault(uid,0) /10+
                            acRatings.getOrDefault(uid,0) /10+
                            cfparticipates.getOrDefault(uid,0) *20 +
                            acparticipates.getOrDefault(uid,0) *20
                    );
            ret.put(uid,json);
        }
        return ret;
    }
    @GetMapping("/api/TagMonthlyRating/{tid}/{startTime}/{endTime}")
    public String getAllMonthlyRating(@PathVariable Long tid,@PathVariable Long startTime,@PathVariable Long endTime){
        List<Long> uids = tagUserMapRepository.getUidsByTid(tid);
        var results = getStuListMonthlyRating(uids,startTime,endTime);
        JSONObject ret = new JSONObject();
        for(var st:results.entrySet()) {
            ret.put(st.getKey().toString(),st.getValue());
        }
        return ResponseUtil.JSONReturn(200,ret);
    }

}

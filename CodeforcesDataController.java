package org.algotithmcontestdatacollect.displaybackend.Controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.Entities.CodeforcesEntities.*;
import org.algotithmcontestdatacollect.displaybackend.Repositories.CfRepository.*;
import org.algotithmcontestdatacollect.displaybackend.Repositories.ProblemTagRepository;
import org.algotithmcontestdatacollect.displaybackend.Repositories.UserRepository;
import org.algotithmcontestdatacollect.displaybackend.Services.RatingChangeService;
import org.algotithmcontestdatacollect.displaybackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import java.util.*;

@RestController
public class CodeforcesDataController {
    @Autowired
    CfStucontestWithCinfoUserinfoRepository cfStucontestWithCinfoUserinfoRepository;

    @Autowired
    CfContestWithParticipateRepository cfContestWithParticipateRepository;

    @Autowired
    CodeforcesAccountWitheUsernameRepository codeforcesAccountWitheUsernameRepository;

    @Autowired
    CfAccountTotalInfoRepository cfAccountTotalInfoRepository;

    @Autowired
    CfStucontestWithUserinfoRepository cfStucontestWithUserinfoRepository;

    @Autowired
    CfSubmitRepository cfSubmitRepository;

    @Autowired
    CfSubmitWithUserinfoRepository cfSubmitWithUserinfoRepository;

    @Autowired
    CfSubmitcodeRepository cfSubmitcodeRepository;

    @Autowired
    RatingChangeService ratingChangeService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CodeforcesProblemsWithTagsRepository codeforcesProblemsWithTagsRepository;

    @Autowired
    ProblemTagRepository problemTagRepository;

    @Autowired
    CodeforcesOkSubmitWithTagsRepository codeforcesOkSubmitWithTagsRepository;

    @GetMapping("/api/codeforces/list")
    public String getAllContest() {
        List<CfContestWithParticipateEntity> l;
        try {
            l = cfContestWithParticipateRepository.findAll();
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/codeforces/rank")
    public String getRank() {
        List<CfAccountTotalInfoEntity> l;
        try {
            l = cfAccountTotalInfoRepository.findAll();
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/codeforces/participateList/{cid}")
    public String getContestParticipate(@PathVariable Long cid) {
        List<CfStucontestWithUserinfoEntity> l;
        try {
            l = cfStucontestWithUserinfoRepository.getCfStucontestWithUserinfoEntitiesByCid(cid);
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/codeforces/submitList/{cid}")
    public String getContestSubmit(@PathVariable Long cid) {
        List<CfSubmitWithUserinfoEntity> l;
        try {
            l = cfSubmitWithUserinfoRepository.getCfSubmitWithUserinfoEntitiesByCid(cid);
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/codeforces/submitcode/{sid}")
    public String getSubmitCode(@PathVariable Long sid) {
        var code = cfSubmitcodeRepository.findById(sid);
        if (code.isPresent()) {
            return ResponseUtil.JSONReturn(200, (JSONObject) JSONObject.toJSON(code.get()));
        } else {
            return ResponseUtil.JSONReturn(404, "数据库尚未收录该提交代码");
        }
    }

    @GetMapping("/api/codeforces/TopTen")
    public String getTopTen() {
        List<CfAccountTotalInfoEntity> l;
        try {
            l = cfAccountTotalInfoRepository.getTopByRatingOrderByRatingDesc(10);
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(l));
    }

    @GetMapping("/api/codeforces/contest/{cid}")
    public String getSpecifiedContest(@PathVariable Long cid) {
        try {
            var op = cfContestWithParticipateRepository.findById(cid);
            if (op.isPresent()) {
                return ResponseUtil.JSONReturn(200, (JSONObject) JSONObject.toJSON(op.get()));
            }
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(404, "不存在该比赛");
    }

    @GetMapping("/api/codeforces/participate/{codeforces_id}")
    public String getUserParticipate(@PathVariable String codeforces_id) {
        try {
            List<CfStucontestWithCinfoUserinfoEntity> cfStucontestWithUserinfoEntities = cfStucontestWithCinfoUserinfoRepository.getCfStucontestWithCinfoUserinfoEntitiesByCodeforcesId(codeforces_id);
            var retData = (JSONArray) JSONArray.toJSON(cfStucontestWithUserinfoEntities);

            if (cfStucontestWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, retData);
            }
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(404, "用户名不存在或无数据");
    }

    @GetMapping("/api/codeforces/afterSubmit/{codeforces_id}")
    public String getUserAfterSubmit(@PathVariable String codeforces_id) {
        try {
            List<CfSubmitWithUserinfoEntity> cfSubmitWithUserinfoEntities = cfSubmitWithUserinfoRepository.getCfSubmitWithUserinfoEntitiesByCodeforcesIdAndIsAfter(codeforces_id, (byte) 1);
            if (cfSubmitWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(cfSubmitWithUserinfoEntities));
            }
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(404, "用户名不存在或无数据");
    }

    @GetMapping("/api/codeforces/contestSubmit/{codeforces_id}")
    public String getUserContestSubmit(@PathVariable String codeforces_id) {
        try {
            List<CfSubmitWithUserinfoEntity> cfSubmitWithUserinfoEntities = cfSubmitWithUserinfoRepository.getCfSubmitWithUserinfoEntitiesByCodeforcesIdAndIsAfter(codeforces_id, (byte) 0);
            if (cfSubmitWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(cfSubmitWithUserinfoEntities));
            }
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(404, "用户名不存在或无数据");
    }


    @GetMapping("/api/codeforces/user/participate/{normalUserName}")
    public String getAccountParticipate(@PathVariable String normalUserName) {
        try {
            List<CfStucontestWithCinfoUserinfoEntity> cfStucontestWithUserinfoEntities = cfStucontestWithCinfoUserinfoRepository.getCfStucontestWithCinfoUserinfoEntitiesByUsername(normalUserName);
            var retData = (JSONArray) JSONArray.toJSON(cfStucontestWithUserinfoEntities);

            if (cfStucontestWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, retData);
            }
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(404, "用户名不存在或无数据");
    }

    @GetMapping("/api/codeforces/user/afterSubmit/{normalUserName}")
    public String getAccountAfterSubmit(@PathVariable String normalUserName) {
        try {
            List<CfSubmitWithUserinfoEntity> cfSubmitWithUserinfoEntities = cfSubmitWithUserinfoRepository.getCfSubmitWithUserinfoEntitiesByUsernameIdAndIsAfter(normalUserName, (byte) 1);
            if (cfSubmitWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(cfSubmitWithUserinfoEntities));
            }
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(404, "用户名不存在或无数据");
    }

    @GetMapping("/api/codeforces/user/contestSubmit/{normalUserName}")
    public String getAccountContestSubmit(@PathVariable String normalUserName) {
        try {
            List<CfSubmitWithUserinfoEntity> cfSubmitWithUserinfoEntities = cfSubmitWithUserinfoRepository.getCfSubmitWithUserinfoEntitiesByUsernameIdAndIsAfter(normalUserName, (byte) 0);
            if (cfSubmitWithUserinfoEntities.size() != 0) {
                return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(cfSubmitWithUserinfoEntities));
            }
        } catch (Exception E) {
            return ResponseUtil.JSONReturn(404, "数据库异常");
        }
        return ResponseUtil.JSONReturn(404, "用户名不存在或无数据");
    }

    @GetMapping("/api/codeforces/allUserMainAccountRatingChange")
    public String getAllUserMainAccountRatingChange() {
        var eldestTime = ratingChangeService.getTheEldestCodeforcesParticipateStartTime();
        var timeList = ratingChangeService.generateDateRangeToNow(eldestTime);
        var userIdList = userRepository.getAllUserId();
        var retData = new JSONObject();
        for (int i = 0; i < timeList.size() - 1; i++) {
            retData.put(Integer.valueOf(timeList.get(i).getYear() +1900).toString() + "-" +
                    Integer.valueOf(timeList.get(i).getMonth() + 1).toString(),
                    JSONObject.toJSON(ratingChangeService.getCodeforcesRatingByUserIdsAndTimePoint(userIdList, timeList.get(i + 1).getTime()/1000))
                    );
        }
        return ResponseUtil.JSONReturn(200, retData);
    }

    @GetMapping("/api/codeforces/recentOneMonthContest")
    public String getRecentOneMonthContest() {
        var timeL = System.currentTimeMillis() / 1000 - 30 *24 * 60 * 60;
        var timeR = System.currentTimeMillis() / 1000;
        var cList = cfContestWithParticipateRepository.getCfContestWithParticipateEntitiesByStartTimeStampBetween(timeL, timeR);
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(cList));
    }

    @GetMapping("/api/codeforces/problemList")
    public String getProblemList(@RequestParam(required = false,defaultValue = "1") Integer page,
                                 @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                 @RequestParam(required = false,defaultValue = "cid") String sortProperty,
                                 @RequestParam(required = false,defaultValue = "DESC") String sortDirection,
                                 @RequestParam(required = false,defaultValue = "0") Integer startDifficulty,
                                 @RequestParam(required = false,defaultValue = "4000") Integer endDifficulty,
                                 @RequestParam(required = false,defaultValue = "") String search)
    {
        Specification<CodeforcesProblemsWithTagsEntity> specification = (Specification<CodeforcesProblemsWithTagsEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(String s : search.split(",")){
                if(s.length() == 0) continue;
                predicates.add(criteriaBuilder.like(root.get("tags"), "%" + s + "%"));
            }
            predicates.add(criteriaBuilder.between(root.get("difficulty"), startDifficulty, endDifficulty));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.valueOf(sortDirection), sortProperty);
        var problemList = codeforcesProblemsWithTagsRepository.findAll(specification,pageable);
        var num = codeforcesProblemsWithTagsRepository.count(specification);
        var retData = new JSONObject();
        retData.put("problemList", (JSONArray) JSONArray.toJSON(problemList.getContent()));
        retData.put("total", num);
        return ResponseUtil.JSONReturn(200, retData);
    }
    @GetMapping("/api/codeforces/getTagList")
    public String getTagList(){
        var tagList = problemTagRepository.findAll();
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(tagList));
    }
    @GetMapping("/api/codeforces/getYearCompare")
    public String getYearCompare(@RequestParam(required = false,defaultValue = "-1") Integer school){
        var users = userRepository.findAll();
        List<CfStucontestWithCinfoUserinfoEntity> codeforcesRecord;
        if(school == -1) {
            codeforcesRecord = cfStucontestWithCinfoUserinfoRepository.getCfStucontestWithCinfoUserinfoEntitiesByIsMain(1);
        }else{
            codeforcesRecord = cfStucontestWithCinfoUserinfoRepository.getCfStucontestWithCinfoUserinfoEntitiesByIsMainAndSchool(1, Long.valueOf(school.toString()));
        }
        Map<String, List<CfStucontestWithCinfoUserinfoEntity>> UserRecordMap = new HashMap<>();
        for (var user : users) {
            UserRecordMap.put(user.getUsername(), new ArrayList<>());
        }
        for (var record : codeforcesRecord) {
            if (record.getDiff() == 0) continue;
            UserRecordMap.get(record.getUsername()).add(record);
        }
        for (var user : users) {
            UserRecordMap.get(user.getUsername()).sort(Comparator.comparing(CfStucontestWithCinfoUserinfoEntity::getStartTimeStamp));
        }
        var thisYear = Calendar.getInstance().get(Calendar.YEAR);
        var thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int fix = thisMonth < 9 ? -1 : 0;
        var currentTime = System.currentTimeMillis() / 1000;
        var retData = new JSONObject();
        for (int year = 2016; year <= thisYear; year++) {
            Set<Integer> yearMap = new HashSet<>();
            var yearData = new JSONObject();
            yearData.put("1000-1200", 0);
            yearData.put("1200-1400", 0);
            yearData.put("1400-1600", 0);
            yearData.put("1600-1800", 0);
            yearData.put("1800+", 0);
            var timePos = currentTime - (long) (thisYear - year) * 365 * 24 * 60 * 60;
            for (int i = year; i >= year - 3; i--) {
                yearMap.add(i + fix);
            }
            for (var user : users) {
                if (!yearMap.contains(user.getYear())) continue;
                if (UserRecordMap.get(user.getUsername()).size() == 0) continue;
                int val = b_search(UserRecordMap.get(user.getUsername()), timePos);
                if (1000 <= val && val < 1200) {
                    yearData.put("1000-1200", yearData.getInteger("1000-1200") + 1);
                } else if (1200 <= val && val < 1400) {
                    yearData.put("1200-1400", yearData.getInteger("1200-1400") + 1);
                } else if (1400 <= val && val < 1600) {
                    yearData.put("1400-1600", yearData.getInteger("1400-1600") + 1);
                } else if (1600 <= val && val < 1800) {
                    yearData.put("1600-1800", yearData.getInteger("1600-1800") + 1);
                } else if (val >= 1800) {
                    yearData.put("1800+", yearData.getInteger("1800+") + 1);
                }
            }
            retData.put(year + "", yearData);
        }
        return ResponseUtil.JSONReturn(200, retData);
    }
    private Integer b_search(List<CfStucontestWithCinfoUserinfoEntity> list,Long time) {
        if(list.size() == 0 || list.get(0).getStartTimeStamp() > time) return 0;
        int l = 0,r = list.size() - 1;
        while(l < r) {
            int mid = (l + r + 1) >> 1;
            if(list.get(mid).getStartTimeStamp() <= time) l = mid;
            else r = mid - 1;
        }
        return list.get(l).getRating();
    }

    @GetMapping("/api/codeforces/OKSubmitInfo/{uids}")
    public String getTagCfOkSubmitInfo(@PathVariable("uids") String uids) {
        List<Long> uidList = new ArrayList<>();
        for (String uid : uids.split(",")) {
            uidList.add(Long.parseLong(uid));
        }
        var cfOkSubmit = codeforcesOkSubmitWithTagsRepository.getCodeforcesOkSubmitWithTagsEntitiesByUids(uidList);
        return ResponseUtil.JSONReturn(200,(JSONArray) JSONArray.toJSON(cfOkSubmit));
    }
}

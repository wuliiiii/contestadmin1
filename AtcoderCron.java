package org.algotithmcontestdatacollect.crawlerdispatcher.CronService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerdispatcher.Dispatcher.DispatcherFactory;
import org.algotithmcontestdatacollect.crawlerdispatcher.Repositories.AcAccountRepository;
import org.algotithmcontestdatacollect.crawlerdispatcher.Repositories.AcContestRepository;
import org.algotithmcontestdatacollect.crawlerdispatcher.Repositories.AcStucontestRepository;
import org.algotithmcontestdatacollect.crawlerdispatcher.Repositories.AcSubmitRepository;
import org.algotithmcontestdatacollect.crawlerdispatcher.Services.UserAccountService;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AtcoderCron {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtcoderCron.class);
    @Autowired
    private AcAccountRepository acAccountRepository;
    @Autowired
    private AcContestRepository acContestRepository;
    @Autowired
    private AcSubmitRepository acSubmitRepository;
    @Autowired
    private AcStucontestRepository acStucontestRepository;
    @Autowired
    private DispatcherFactory dispatcherFactory;

    @Autowired
    private UserAccountService userAccountService;

    @Scheduled(cron = "0 */5 * * * *")
    public void pastContestList() {
        LOGGER.info("执行定时任务时间: " + LocalDateTime.now());
        LOGGER.info("获取atcoder past比赛列表");
        var ret = new JSONObject();
        try {
            LOGGER.info("atcoderPastContestListDispatcher启动");
            dispatcherFactory.dispatch("atcoderPastContestListDispatcher", new JSONArray());
        } catch (Exception e) {
            LOGGER.error("atcoder过去比赛定时任务推送失败");
            throw e;
        }
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void preContestList() {
        LOGGER.info("执行定时任务时间: " + LocalDateTime.now());
        LOGGER.info("获取atcoder pre比赛列表");
        var ret = new JSONObject();
        try {
            LOGGER.info("atcoderPresentContestListDispatcher启动");
            dispatcherFactory.dispatch("atcoderPresentContestListDispatcher", new JSONArray());
        } catch (Exception e) {
            LOGGER.error("atcoder未来比赛定时任务推送失败");
            LOGGER.error(e.toString());
        }
    }

    @Scheduled(cron = "0 20 * * * *")
    public void getStuContest() {
        LOGGER.info("执行静态定时任务时间: " + LocalDateTime.now());
        LOGGER.info("获取atcoder用户参加的比赛和积分变化");
        List<String> acids = new ArrayList<>();
        try {
            List<AcAccountWithUsernameEntity> entities = userAccountService.getAcAccounts();
            for (var entity : entities) {
                acids.add(entity.getAtcoderId());
            }
        } catch (Exception e) {
            LOGGER.error("数据库异常");
            throw e;
        }
        LOGGER.info("getStuContest启动");
        for (var acid : acids) {
            LOGGER.info("预备爬取{}的比赛记录", acid.strip());
            try {
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(acid.strip());//add方法添加参数
                dispatcherFactory.dispatch("atcoderStucontestDispatcher", param);
            } catch (Exception e) {
                LOGGER.error("{}的历史比赛记录爬虫请求推送失败",acid);
                throw e;
            }
        }
    }


    //    @Scheduled(cron = "4 30 0/1 * * ?") // 慎重打开该选项，经测试一小时内无法完成该爬取，且会导致爬虫被封
    public void getAllContestSubmit() {
        LOGGER.info("执行静态定时任务时间: " + LocalDateTime.now());
        LOGGER.info("每一小时获取一次atcoder用户最近五场的提交记录");
        var ret = new JSONObject();
        try {
            LOGGER.info("atcoderGetContestSubmit启动");
            List<AcAccountWithUsernameEntity> entities = null;
            List<AcContestEntity> contests = acContestRepository.findAll(Sort.by(Sort.Direction.DESC, "startTimeStamp"));
            try {
                LOGGER.info("获得比赛记录");
                entities = userAccountService.getAcAccounts();
            } catch (Exception e) {
                LOGGER.info("数据库异常或无此用户");
                throw e;
            }
            for (var entity : entities) {
                int i = 0;
                var acid = entity.getAtcoderId();
                for (var contest : contests) {
                    if (contest.getStartTimeStamp() >= System.currentTimeMillis() / 1000) {
                        continue;
                    }
                    i++;
                    var nickname = contest.getNickname();
                    LOGGER.info("预备爬取{ " + acid.strip() + " }在{" + nickname + " }的提交记录");
                    JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                    param.add(acid.strip());//add方法添加参数
                    param.add(nickname);
                    dispatcherFactory.dispatch("atcoderContestSubmitDispatcher", param);
                }
            }
        } catch (Exception e) {
            LOGGER.error("atcoderContestSubmitDispatcher推送失败");
            throw e;
        }
    }


    @Scheduled(cron="0 40 * * * ?")
    public void getRecent5StuContestSubmit() {
        List<AcContestEntity> contests = acContestRepository.findAll(Sort.by(Sort.Direction.DESC, "startTimeStamp"));
        List<AcContestEntity> toDownload = new ArrayList<>();
        for (var contest : contests) {
            toDownload.add(contest);
            if(toDownload.size() >= 5){
                break;
            }
        }
        getNoExistContestSubmit(toDownload);
    }
    @Scheduled(cron="0 0 * * * ?")
    public void getRandom5ContestSubmit() {
        List<AcContestEntity> contests = acContestRepository.getAcContestEntitiesRandom(5);
        getNoExistContestSubmit(contests);
    }

    @Scheduled(cron = "0 20 * * * ?")
    public void get5NoSubmitRecordContest() {
        List<AcContestEntity> contests = acContestRepository.getNoSubmitRecordContestEntities(5);
        getNoExistContestSubmit(contests);
    }

    @Scheduled(cron = "0 30 * * * ?")
    public void getAllExistStuContestButNoExistContestSubmit() {
        List<AcContestEntity> contests = acContestRepository.findAll();
        List<AcAccountWithUsernameEntity> entities = userAccountService.getAcAccounts();
        Map<Long,String> nickNameMap = new HashMap<>();
        Map<Long,String> acidMap = new HashMap<>();
        for(var contest : contests){
            nickNameMap.put(contest.getId(),contest.getNickname());
        }
        for (var entity : entities) {
            acidMap.put(entity.getId(),entity.getAtcoderId());
        }
        List<AcStucontestEntity> stucontests = acStucontestRepository.getNoSubmitStuContest();
        for(var stucontest : stucontests) {
            if(!acidMap.containsKey(stucontest.getAcid())) {
                continue;
            }
            LOGGER.info("预备爬取{ " + acidMap.get(stucontest.getAcid()) + " }在{" + nickNameMap.get(stucontest.getCid()) + " }的提交记录");
            try{
                JSONArray param = new JSONArray();
                param.add(acidMap.get(stucontest.getAcid()));
                param.add(nickNameMap.get(stucontest.getCid()));
                dispatcherFactory.dispatch("atcoderContestSubmitDispatcher", param);
            } catch (Exception e) {
                LOGGER.error("atcoderContestSubmitDispatcher推送失败");
                throw e;
            }

        }
    }
    private void getNoExistContestSubmit(List<AcContestEntity> contests) {
        List<AcAccountWithUsernameEntity> entities = userAccountService.getAcAccounts();
        for(var entity : entities) {
            for(var contest : contests) {
                LOGGER.info("预备爬取{ " + entity.getAtcoderId() + " }在{" + contest.getNickname() + " }的提交记录");
                try{
                    JSONArray param = new JSONArray();
                    param.add(entity.getAtcoderId());
                    param.add(contest.getNickname());
                    dispatcherFactory.dispatch("atcoderContestSubmitDispatcher", param);
                } catch (Exception e) {
                    LOGGER.error("atcoderContestSubmitDispatcher推送失败");
                    throw e;
                }
            }
        }
    }



    @Scheduled(cron = "4 20 * * * ?")
    public void getSubmitCode() {
        LOGGER.info("执行静态定时任务时间: " + LocalDateTime.now());
        LOGGER.info("每一小时获取一次atcoder用户的提交代码");
        List<AcContestEntity> contests = acContestRepository.findAll(Sort.by(Sort.Direction.DESC, "startTimeStamp"));
        Map<Long,String> nickNameMap = new HashMap<>();
        for (var contest : contests) {
            nickNameMap.put(contest.getId(),contest.getNickname());
        }
        List<AcSubmitEntity> entities = null;
        try {
            LOGGER.info("尝试获取200条随机的未取得的数据");
            entities = acSubmitRepository.get500NoCodeSubmitsRandomly(); //获取所有的提交记录
        } catch (Exception e) {
            LOGGER.info("数据库异常或无数据");
            throw e;
        }
        for (var entity : entities) {
            var nickName = nickNameMap.get(entity.getCid());
            var sid = entity.getSid();
            LOGGER.info("预备爬取{}的提交代码", sid);
            try {
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(sid);//add方法添加参数
                param.add(nickName);
                dispatcherFactory.dispatch("atcoderSubmitCodeDispatcher", param);
            } catch (Exception e) {
                LOGGER.error("atcoderSubmitCodeDispatcher - sid:{} 推送失败", sid);
                throw e;
            }
        }
    }
}

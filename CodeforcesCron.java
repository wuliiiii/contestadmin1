package org.algotithmcontestdatacollect.crawlerdispatcher.CronService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.crawlerdispatcher.Dispatcher.DispatcherFactory;
import org.algotithmcontestdatacollect.crawlerdispatcher.Repositories.CfAccountRepository;
import org.algotithmcontestdatacollect.crawlerdispatcher.Repositories.CfSubmitRepository;
import org.algotithmcontestdatacollect.crawlerdispatcher.Services.UserAccountService;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfAccountEntity;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfAccountWithUsernameEntity;
import org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity.CfSubmitEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeforcesCron {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeforcesCron.class);
    @Autowired
    private CfSubmitRepository cfSubmitRepository;
    @Autowired
    private CfAccountRepository cfAccountRepository;
    @Autowired
    private DispatcherFactory dispatcherFactory;

    @Autowired
    private UserAccountService userAccountService;

    //3.添加定时任务
    @Scheduled(cron = "1 */5 * * * *")
    public void contestList() {
        LOGGER.info("执行静态定时任务时间: " + LocalDateTime.now());
        LOGGER.info("每一小时获取一次codeforces比赛列表");
        try {
            LOGGER.info("codeforcesContestListDispatcher启动");
            dispatcherFactory.dispatch("codeforcesContestListDispatcher", new JSONArray());
        } catch (Exception e) {
            LOGGER.info("codeforcesContestListDispatcher推送失败");
            LOGGER.error(e.toString());
        }
    }

    //添加定时任务
    @Scheduled(cron = "0 10 * * * ?")
    public void getStuContest() {
        LOGGER.info("执行静态定时任务时间: " + LocalDateTime.now());
        LOGGER.info("每一小时获取一次codeforces用户参加的比赛和积分变化");
        try {
            LOGGER.info("getStuContest启动");
            List<String> cfids = new ArrayList<>();
            try {
                LOGGER.info("获取所有的cf用户");
                List<CfAccountWithUsernameEntity> entities = userAccountService.getCfAccounts(); // 获取所有的Cf实体
                for (var entity : entities) {
                    cfids.add(entity.getCodeforcesId());
                }
            } catch (Exception e) {
                LOGGER.info("数据库异常或无此用户");
                return;
            }
            LOGGER.info("预备爬取{}的所有比赛记录", cfids);
            for (var cfid : cfids) {
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(cfid);//add方法添加参数
                dispatcherFactory.dispatch("codeforcesStuContestDispatcher", param);
            }
        } catch (Exception e) {
            LOGGER.info("codeforcesStuContestDispatcher推送失败");
            LOGGER.error(e.toString());
        }
    }

    //添加定时任务
    @Scheduled(cron = "0 30 * * * ?")
    public void getStuRecord() {
        LOGGER.info("执行静态定时任务时间: " + LocalDateTime.now());
        LOGGER.info("每一小时获取一次codeforces用户的提交记录");
        try {
            LOGGER.info("getStuRecord启动");
            List<String> cfids = new ArrayList<>();
            try {
                List<CfAccountWithUsernameEntity> entities = userAccountService.getCfAccounts();
                LOGGER.info("共" + entities.size() + "个账户");
                for (var entity : entities) {
                    cfids.add(entity.getCodeforcesId());
                }
            } catch (Exception e) {
                LOGGER.info("数据库异常或无此用户");
                return;
            }
            LOGGER.info("预备爬取{}的提交记录", cfids);
            for (var cfid : cfids) {
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(cfid);//add方法添加参数
                dispatcherFactory.dispatch("codeforcesContestRecordDispatcher", param);
            }
        } catch (Exception e) {
            LOGGER.info("codeforcesContestRecordDispatcher推送失败");
            LOGGER.error(e.toString());
        }
    }
    @Scheduled(cron = "30 20 * * * ?")
    public void getSubmitCode() {
        LOGGER.info("执行静态定时任务时间: " + LocalDateTime.now());
        LOGGER.info("每一小时获取一次codeforces用户的提交代码");
        List<CfSubmitEntity> entities = null;
        try {
            LOGGER.info("尝试获取500条随机的未取得的数据");
            entities = cfSubmitRepository.get200NoCodeSubmitsRandomly(); //获取所有的提交记录
        } catch (Exception e) {
            LOGGER.info("数据库异常或无数据");
            throw e;
        }
        for (var entity : entities) {
            var cid = entity.getCid();
            var sid = entity.getSid();
            LOGGER.info("预备爬取{}的提交代码", sid);
            try {
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(sid);//add方法添加参数
                param.add(cid);
                dispatcherFactory.dispatch("codeforcesSubmitCodeDispatcher", param);
            } catch (Exception e) {
                LOGGER.error("codeforcesSubmitCodeDispatcher - sid:{} 推送失败", sid);
                throw e;
            }
        }
    }

    @Scheduled(cron = "0 50 * * * ?")
    public void totalProblem(){
        try {
            LOGGER.info("getContestQuestion启动");
            JSONArray param = new JSONArray();
            dispatcherFactory.dispatch("codeforcesContestTotalQuestionDispatcher",param);
        }catch (Exception e){
            LOGGER.info("codeforcesContestQuestionDispatcher推送失败");
            LOGGER.error(e.toString());
        }
    }
}

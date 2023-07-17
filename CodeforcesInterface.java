package org.algorithmcontestdatacollect.crawlerendpoint2.HTTPInterface.normalInterface;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Dispatcher.DispatcherFactory;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.CfAccountRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.CfSubmitRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.CfAccountEntity;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.CfSubmitEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/codeforces")
public class CodeforcesInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeforcesInterface.class);
    @Autowired
    private CfSubmitRepository cfSubmitRepository;
    @Autowired
    private CfAccountRepository cfAccountRepository;
    @Autowired
    private DispatcherFactory dispatcherFactory;
    @GetMapping("/contestList")
    public String contestList(HttpServletRequest request, HttpServletResponse response){
        var ret = new JSONObject();
        try{
            LOGGER.info("codeforcesContestListDispatcher启动");
            dispatcherFactory.dispatch("codeforcesContestListDispatcher",new JSONArray());
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesContestListDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }

    @GetMapping("/GetStuContest/{uid}") //访问/GetStuContest/{uid}启动该方法,注意@PathVariable注解，这个注解用于获取路径参数
    public String getStuContest(@PathVariable Long uid, HttpServletRequest request, HttpServletResponse response){
        var ret = new JSONObject();
        try{
            LOGGER.info("getStuContest启动");
            LOGGER.info("获得参数"+uid);
            List<String> cfids = new ArrayList<>();
            try{
                List<CfAccountEntity> entities = cfAccountRepository.getCfAccountEntitiesByUid(uid); // 根据Uid获取整个Cf实体,这里使用了JPA与数据库连接，如果不会JPA请参考https://www.bilibili.com/video/BV1F54y1B7Gy
                for(var entity:entities){
                    cfids.add(entity.getCodeforcesId());
                }
            }catch (Exception e){
                ret.put("code",404);
                ret.put("msg","数据库异常或无此用户");
                return ret.toJSONString();
            }
            LOGGER.info("预备爬取{}的所有比赛记录",cfids);
            for(var cfid: cfids){
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(cfid);//add方法添加参数
                dispatcherFactory.dispatch("codeforcesStuContestDispatcher",param);//调用Dispatcher，第一个参数是dispatcher的首字母小写，如CodeforcesStuContestDispatcher 变成codeforcesStuContestDispatcher,第二个为刚刚的参数,(可能你会好奇为什么调用的是dispatcherFactory而不是CodeforcesStuContestDispatcher,因为会通过dispatcherFactory来统一调用其它的具体的类)
            }
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesStuContestDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }

    @GetMapping("/getContestSubmit/{uid}/{cid}") //访问/GetContestStatus/{uid}启动该方法,注意@PathVariable注解，这个注解用于获取路径参数
    public String getContestSubmit(@PathVariable Long uid, @PathVariable Long cid, HttpServletRequest request, HttpServletResponse response){
        var ret = new JSONObject();
        try{
            LOGGER.info("getContestSubmit启动");
            LOGGER.info("获得参数"+uid +"/"+cid);
            List<String> cfids = new ArrayList<>();
            try{
                List<CfAccountEntity> entities = cfAccountRepository.getCfAccountEntitiesByUid(uid); // 根据Uid获取整个Cf实体,这里使用了JPA与数据库连接，如果不会JPA请参考https://www.bilibili.com/video/BV1F54y1B7Gy
                for(var entity:entities){
                    cfids.add(entity.getCodeforcesId());
                }
            }catch (Exception e){
                ret.put("code",404);
                ret.put("msg","数据库异常或无此用户");
                return ret.toJSONString();
            }
            LOGGER.info("预备爬取{}的提交记录",cfids);
            for(var cfid: cfids){
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(cfid);//add方法添加参数
                param.add(cid);
                dispatcherFactory.dispatch("codeforcesContestSubmitDispatcher",param);//调用Dispatcher，第一个参数是dispatcher的首字母小写，如CodeforcesStuContestDispatcher 变成codeforcesStuContestDispatcher,第二个为刚刚的参数,(可能你会好奇为什么调用的是dispatcherFactory而不是CodeforcesStuContestDispatcher,因为会通过dispatcherFactory来统一调用其它的具体的类)
            }
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesContestSubmitDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }
    @GetMapping("/GetStuRecord/{uid}") //访问/GetStuContest/{uid}启动该方法,注意@PathVariable注解，这个注解用于获取路径参数
    // 需要加cid
    public String getStuRecord(@PathVariable Long uid, HttpServletRequest request, HttpServletResponse response){
        var ret = new JSONObject();
        try{
            LOGGER.info("getStuRecord启动");
            LOGGER.info("获得参数"+uid);
            List<String> cfids = new ArrayList<>();
            try{
                List<CfAccountEntity> entities = cfAccountRepository.getCfAccountEntitiesByUid(uid); // 根据Uid获取整个Cf实体,这里使用了JPA与数据库连接，如果不会JPA请参考https://www.bilibili.com/video/BV1F54y1B7Gy
                for(var entity:entities){
                    cfids.add(entity.getCodeforcesId());
                }
            }catch (Exception e){
                ret.put("code",404);
                ret.put("msg","数据库异常或无此用户");
                return ret.toJSONString();
            }
            LOGGER.info("预备爬取{}的所有比赛记录",cfids);
            for(var cfid: cfids){
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(cfid);//add方法添加参数
                dispatcherFactory.dispatch("codeforcesContestRecordDispatcher",param);//调用Dispatcher，第一个参数是dispatcher的首字母小写，如CodeforcesStuContestDispatcher 变成codeforcesStuContestDispatcher,第二个为刚刚的参数,(可能你会好奇为什么调用的是dispatcherFactory而不是CodeforcesStuContestDispatcher,因为会通过dispatcherFactory来统一调用其它的具体的类)
            }
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesContestRecordDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }
    @GetMapping("/GetSubmitCode/{sid}") //访问/GetStuContest/{sid}启动该方法,注意@PathVariable注解，这个注解用于获取路径参数
    public String getSubmitCode(@PathVariable Long sid, HttpServletRequest request, HttpServletResponse response) {
        var ret = new JSONObject();
        try {
            LOGGER.info("getSubmitCode启动");
            LOGGER.info("获得参数" + sid);
//            List<String> cfids = new ArrayList<>();
            Long cid;
            try {
                CfSubmitEntity entity = cfSubmitRepository.getCfSubmitEntityBySid(sid); // 根据Uid获取整个Submit实体,这里使用了JPA与数据库连接，如果不会JPA请参考https://www.bilibili.com/video/BV1F54y1B7Gy
//                for(var entity:entities){
//                    cfids.add(entity.getCodeforcesId());
//                }
                cid = entity.getCid();
            } catch (Exception e) {
                ret.put("code", 404);
                ret.put("msg", "数据库异常或无此提交记录");
                return ret.toJSONString();
            }
            LOGGER.info("预备爬取{}的提交代码",sid);

            JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
            param.add(sid);//add方法添加参数
            param.add(cid);
            dispatcherFactory.dispatch("codeforcesSubmitCodeDispatcher",param);//调用Dispatcher，第一个参数是dispatcher的首字母小写，如CodeforcesStuContestDispatcher 变成codeforcesStuContestDispatcher,第二个为刚刚的参数,(可能你会好奇为什么调用的是dispatcherFactory而不是CodeforcesStuContestDispatcher,因为会通过dispatcherFactory来统一调用其它的具体的类)

            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesSubmitCodeDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }
    @GetMapping("/GetContestTotalQuestion")
    public String getContestTotalQuestion(HttpServletRequest request, HttpServletResponse response) {
        var ret = new JSONObject();
        try {
            LOGGER.info("getContestQuestion启动");
            JSONArray param = new JSONArray();
            dispatcherFactory.dispatch("codeforcesContestTotalQuestionDispatcher",param);
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesContestQuestionDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }
    @GetMapping("/GetContestQuestion/{cid}/{index}")
    public String getContestQuestion(@PathVariable Long cid,@PathVariable String index) {
        var ret = new JSONObject();
        try {
            LOGGER.info("getContestQuestion启动");
            LOGGER.info("获得参数" + cid);
            JSONArray param = new JSONArray();
            param.add(cid);
            param.add(index);
            dispatcherFactory.dispatch("codeforcesContestQuestionDispatcher",param);
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesContestQuestionDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }
}
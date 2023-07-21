package org.algorithmcontestdatacollect.crawlerendpoint2.HTTPInterface.normalInterface;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Dispatcher.DispatcherFactory;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcAccountRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcContestRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcSubmitRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcAccountEntity;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcSubmitEntity;
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
//定义了多个接口,用于触发不同的AtCoder爬虫任务，并将执行结果作为JSON格式的响应返回
@RestController
@RequestMapping("/atcoder")
public class AtcoderInterface {
    @Autowired
    private AcAccountRepository acAccountRepository;
    @Autowired
    private AcContestRepository acContestRepository;
    @Autowired
    private AcSubmitRepository acSubmitRepository;
    @Autowired
    DispatcherFactory dispatcherFactory;
    private static Logger LOGGER = LoggerFactory.getLogger(AtcoderInterface.class);
    @GetMapping("/pastContestList")
    public String pastContestList() {
        var ret = new JSONObject();
        try{
            LOGGER.info("atcoderPastContestListDispatcher启动");
            dispatcherFactory.dispatch("atcoderPastContestListDispatcher",new JSONArray());
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesContestListDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }

    @GetMapping("/presentContestList")
    public String preContestList(){
        var ret = new JSONObject();
        try{
            LOGGER.info("atcoderPresentContestListDispatcher启动");
            dispatcherFactory.dispatch("atcoderPresentContestListDispatcher",new JSONArray());
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("codeforcesPreContestListDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();

    }

    @GetMapping("/getContestSubmit/{uid}/{cid}")
    public String getContestSubmit(@PathVariable Long uid, @PathVariable Long cid, HttpServletRequest request, HttpServletResponse response){
        var ret = new JSONObject();
        try{
            LOGGER.info("atcoderGetContestSubmit启动");
            LOGGER.info("获得参数"+uid +"/"+cid);
            List<String> acids = new ArrayList<>();
            String nickname = acContestRepository.getAcContestEntityById(cid).getNickname();
            try{
                List<AcAccountEntity> entities = acAccountRepository.getAcAccountsEntitiesByUid(uid); // 根据Uid获取整个Ac实体,这里使用了JPA与数据库连接，如果不会JPA请参考https://www.bilibili.com/video/BV1F54y1B7Gy
                for(var entity:entities){
                    acids.add(entity.getAtcoderId());
                }
            }catch (Exception e){
                LOGGER.error(e.toString());
                ret.put("code",404);
                ret.put("msg","数据库异常或无此用户");
                return ret.toJSONString();
            }
            LOGGER.info("预备爬取{}的提交记录",acids);
            for(var acid: acids){
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(acid);//add方法添加参数
                param.add(nickname);
                dispatcherFactory.dispatch("atcoderContestSubmitDispatcher",param);//调用Dispatcher，第一个参数是dispatcher的首字母小写，如CodeforcesStuContestDispatcher 变成codeforcesStuContestDispatcher,第二个为刚刚的参数,(可能你会好奇为什么调用的是dispatcherFactory而不是CodeforcesStuContestDispatcher,因为会通过dispatcherFactory来统一调用其它的具体的类)
            }
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("atcoderContestSubmitDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }
    @GetMapping("/stuContest/{uid}")
    public String stuContest(@PathVariable Long uid, HttpServletRequest request, HttpServletResponse response){
        var ret = new JSONObject();
        try{
            LOGGER.info("stuContest启动");
            LOGGER.info("获得参数"+uid);
            List<String> acids = new ArrayList<>();
            try{
                List<AcAccountEntity> entities = acAccountRepository.getAcAccountsEntitiesByUid(uid); // 根据Uid获取整个Cf实体,这里使用了JPA与数据库连接，如果不会JPA请参考https://www.bilibili.com/video/BV1F54y1B7Gy
                for(var entity:entities){
                    acids.add(entity.getAtcoderId());
                }
            }catch (Exception e){
                ret.put("code",404);
                ret.put("msg","数据库异常或无此用户");
                return ret.toJSONString();
            }
            LOGGER.info("预备爬取{}的所有比赛记录",acids);
            for(var acid: acids){
                JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
                param.add(acid);//add方法添加参数
                dispatcherFactory.dispatch("atcoderStucontestDispatcher",param);//调用Dispatcher，第一个参数是dispatcher的首字母小写，如CodeforcesStuContestDispatcher 变成codeforcesStuContestDispatcher,第二个为刚刚的参数,(可能你会好奇为什么调用的是dispatcherFactory而不是CodeforcesStuContestDispatcher,因为会通过dispatcherFactory来统一调用其它的具体的类)
            }
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("atcoderStucontestDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }
    @GetMapping("/GetSubmitCode/{sid}") //访问/GetStuContest/{sid}启动该方法,注意@PathVariable注解，这个注解用于获取路径参数
    public String getSubmitCode(@PathVariable Long sid, HttpServletRequest request, HttpServletResponse response){
        var ret = new JSONObject();
        String nickname;
        try{
            LOGGER.info("atcoderGetSubmitCode启动");
            LOGGER.info("获得参数"+sid);
            try{
                AcSubmitEntity entity = acSubmitRepository.getAcSubmitEntityBySid(sid); // 根据Uid获取整个Ac实体,这里使用了JPA与数据库连接，如果不会JPA请参考https://www.bilibili.com/video/BV1F54y1B7Gy
                nickname = acContestRepository.getAcContestEntityById(entity.getCid()).getNickname();
            }catch (Exception e){
                LOGGER.error(e.toString());
                ret.put("code",404);
                ret.put("msg","数据库异常或无此提交记录");
                return ret.toJSONString();
            }
            LOGGER.info("预备爬取{ "+sid+" }的提交代码");
            JSONArray param = new JSONArray(); // 参数一定是要JSONArrau传递，且一定要基础类型，不能是对象，如果是对象需要先序列化
            param.add(sid);//add方法添加参数
            param.add(nickname);
            dispatcherFactory.dispatch("atcoderSubmitCodeDispatcher",param);//调用Dispatcher，第一个参数是dispatcher的首字母小写，如CodeforcesStuContestDispatcher 变成codeforcesStuContestDispatcher,第二个为刚刚的参数,(可能你会好奇为什么调用的是dispatcherFactory而不是CodeforcesStuContestDispatcher,因为会通过dispatcherFactory来统一调用其它的具体的类)
            ret.put("code",200);
        }catch (Exception e){
            LOGGER.info("atcoderSubmitCodeDispatcher推送失败");
            LOGGER.error(e.toString());
            ret.put("code",404);
        }
        return ret.toJSONString();
    }
}

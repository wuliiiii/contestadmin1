package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.*;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.CfSubmitcodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service // 为该类创建一个Bean实例，一定要加
public class CodeforcesSubmitCodeResultHandler extends AbstractHandler {
    @Autowired
    private CfStucontestRepository cfStucontestRepository;
    @Autowired
    private CfAccountRepository cfAccountRepository;
    @Autowired
    private CfSubmitRepository cfSubmitRepository;
    @Autowired
    private CfSubmitCodeRepository cfSubmitCodeRepository;

    @Override
    public void handle(String result) {
        JSONObject res = JSONObject.parseObject(result);//将数据序列化
        Long sid = res.getLong("sid"); // 获取题目提交记录
        var submitCodes = res.getString("submitCodes");// 提交代码
        var cfSubmitEntity = cfSubmitRepository.getCfSubmitEntityBySid(sid); // 因为不是直接存放，需要获取对应提交记录的ID
        if (cfSubmitEntity == null){
            LOGGER.error("题目"+sid+"不在数据库中");
            return;
        }
        Long cid = cfSubmitEntity.getCid();
        if(!cfSubmitCodeRepository.existsCfSubmitcodeEntityBySid(sid)){
            CfSubmitcodeEntity cfSubmitcodeEntity = new CfSubmitcodeEntity();
            cfSubmitcodeEntity.setSid(sid);
            cfSubmitcodeEntity.setCode(submitCodes);
            cfSubmitCodeRepository.save(cfSubmitcodeEntity);
        }

    }
}

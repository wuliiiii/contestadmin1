package org.algorithmcontestdatacollect.crawlerendpoint2.Services;

import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.AcAccountWithUsernameRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.CfAccountWithUsernameRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.AcAccountWithUsernameEntity;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.CfAccountWithUsernameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserAccountService {

    @Autowired
    Environment environment;

    @Autowired
    AcAccountWithUsernameRepository acAccountWithUsernameRepository;

    @Autowired
    CfAccountWithUsernameRepository cfAccountWithUsernameRepository;

    public List<AcAccountWithUsernameEntity> getAcAccounts() {
//        var useAllUser =  Boolean.getBoolean(environment.getProperty("dispatcher.setting.use-all-user","true"));
//        var date = new Date();
//        if(!useAllUser){
//            if(date.getMonth() > 6) {
//                return acAccountWithUsernameRepository.getAcAccountWithUsernameEntitiesByYearAfter(date.getYear() + 1900 - 4);
//            }
//            return acAccountWithUsernameRepository.getAcAccountWithUsernameEntitiesByYearAfter(date.getYear() + 1900 - 5);
//        }else{
            return acAccountWithUsernameRepository.findAll();
//        }
    }

    public List<CfAccountWithUsernameEntity> getCfAccounts() {
        var useAllUser =  Boolean.getBoolean(environment.getProperty("dispatcher.setting.use-all-user","true"));
        var date = new Date();
//        if(!useAllUser){
//            if(date.getMonth() > 6) {
//                return cfAccountWithUsernameRepository.getCfAccountWithUsernameEntitiesByYearAfter(date.getYear() + 1900 - 4);
//            }
//            return cfAccountWithUsernameRepository.getCfAccountWithUsernameEntitiesByYearAfter(date.getYear() + 1900 - 5);
//        }else{
            return cfAccountWithUsernameRepository.findAll();
//        }
    }
}

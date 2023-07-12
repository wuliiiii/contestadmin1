package org.algotithmcontestdatacollect.displaybackend.Services;

import org.algotithmcontestdatacollect.displaybackend.Repositories.CfRepository.CfAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class CfAccountService {
    @Autowired
    private CfAccountRepository cfAccountRepository;


    public void setMainAccount(Long id, Long uid) {
        cfAccountRepository.resetAllMain(uid);
        cfAccountRepository.setMainAccount(id, uid);
    }
    public boolean existsByUidAndId(Long id,Long uid) {
        return cfAccountRepository.existsByIdAndUid(id, uid);
    }
}

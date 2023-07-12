package org.algotithmcontestdatacollect.displaybackend.Services;

import org.algotithmcontestdatacollect.displaybackend.Repositories.AcRepositories.AcAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class AcAccountService {
    @Autowired
    private AcAccountRepository acAccountRepository;

    public void setMainAccount(Long id, Long uid) {
        acAccountRepository.resetAllMain(uid);
        acAccountRepository.setMainAccount(id, uid);
    }
    public boolean existsByUidAndId(Long id,Long uid) {
        return acAccountRepository.existsByIdAndUid(id, uid);
    }
}

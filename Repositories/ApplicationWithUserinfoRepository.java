package org.algotithmcontestdatacollect.displaybackend.Repositories;

import org.algotithmcontestdatacollect.displaybackend.Entities.ApplicationWithUserinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationWithUserinfoRepository extends JpaRepository<ApplicationWithUserinfoEntity,Long> {
    List<ApplicationWithUserinfoEntity> getApplicationWithUserinfoEntitiesByUid(Long uid);
}

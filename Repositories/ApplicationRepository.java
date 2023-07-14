package org.algotithmcontestdatacollect.displaybackend.Repositories;

import org.algotithmcontestdatacollect.displaybackend.Entities.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity,Long> {

}

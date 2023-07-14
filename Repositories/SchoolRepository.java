package org.algotithmcontestdatacollect.displaybackend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.algotithmcontestdatacollect.displaybackend.Entities.SchoolEntity;
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
    Integer countById(Long id);
}

package org.algotithmcontestdatacollect.displaybackend.Repositories;

import org.algotithmcontestdatacollect.displaybackend.Entities.FutureContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FutureContestRepository extends JpaRepository<FutureContestEntity,Long> {
}

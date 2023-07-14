package org.algotithmcontestdatacollect.displaybackend.Repositories;

import org.algotithmcontestdatacollect.displaybackend.Entities.ProblemTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTagRepository extends JpaRepository<ProblemTagEntity,Integer> {
}

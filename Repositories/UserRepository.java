package org.algotithmcontestdatacollect.displaybackend.Repositories;

import org.algotithmcontestdatacollect.displaybackend.Entities.NormalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<NormalUserEntity,Long> {
    public NormalUserEntity findByUsername(String username);

    @Query(nativeQuery = true,value = "SELECT id FROM normal_user")
    List<Long> getAllUserId();

    boolean existsBySchoolAndStuNo(Long school, String stuNo);
}

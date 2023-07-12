package org.algotithmcontestdatacollect.displaybackend.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolveService {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    public Map<Long,Integer> getCodeforcesSolveByUserIdsAndTimePoint(List<Long> uids, Long start, Long end){
        var entityManager = entityManagerFactory.createEntityManager();
        String sql = """
                SELECT
                    `cf_account`.`uid` AS `uid`,
                	count( DISTINCT `cf_account`.`uid`, `cf_submit`.`cid`, `cf_submit`.`q_index` ) AS `solve`
                FROM
                	( `cf_submit` LEFT JOIN `cf_account` ON ( ( `cf_submit`.`cfid` = `cf_account`.`id` ) ) )
                WHERE
                	cf_submit.submit_time <= :endTime 
                	and cf_submit.submit_time >= :startTime 
                	and `cf_submit`.`status` = 'OK'
                	and `cf_account`.`uid` in :uids
                GROUP BY
                	`cf_account`.`uid`
                """;
        var query = entityManager.createNativeQuery(sql);
        query.setParameter("endTime", end);
        query.setParameter("startTime", start);
        query.setParameter("uids",uids);
        var result = query.getResultList();
        Map<Long,Integer> ret = new HashMap<>();
        for(var item : result){
            var row = (Object[])item;
            ret.put(((BigInteger)row[0]).longValue(),((BigInteger) row[1]).intValue());
        }
        entityManager.close();
        return ret;
    }

    public Map<Long,Integer> getAtcoderSolveByUserIdsAndTimePoint(List<Long> uids, Long start, Long end){
        var entityManager = entityManagerFactory.createEntityManager();
        String sql = """
                SELECT
                	`ac_account`.`uid` AS `uid`,
                	count( DISTINCT `ac_account`.`uid`, `ac_submit`.`cid`, `ac_submit`.`q_index` ) AS `solve`
                FROM
                	( `ac_submit` LEFT JOIN `ac_account` ON ( ( `ac_submit`.`acid` = `ac_account`.`id` ) ) )
                WHERE
                	ac_submit.submit_time <= :endTime 
                	AND ac_submit.submit_time >= :startTime
                	AND `ac_submit`.`status` = 'AC'
                	AND `ac_account`.`uid` IN :uids
                GROUP BY
                	`ac_account`.`uid`
                """;
        var query = entityManager.createNativeQuery(sql);
        query.setParameter("endTime", end);
        query.setParameter("startTime", start);
        query.setParameter("uids",uids);
        var result = query.getResultList();
        Map<Long,Integer> ret = new HashMap<>();
        for(var item : result){
            var row = (Object[])item;
            ret.put(((BigInteger)row[0]).longValue(),((BigInteger) row[1]).intValue());
        }
        entityManager.close();
        return ret;
    }

}

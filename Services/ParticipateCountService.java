package org.algotithmcontestdatacollect.displaybackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParticipateCountService {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    public Map<Long,Integer> getCfTimeSpanParticipate(List<Long> uids, Long start, Long end) {
        var entityManager = entityManagerFactory.createEntityManager();
        var sql = """
                SELECT
                		uid,
                		count( * ) AS participate
                	FROM
                		cf_stucontest
                		LEFT JOIN cf_account ON cf_stucontest.cfid = cf_account.id
                		LEFT JOIN cf_contest ON cf_contest.cid = cf_stucontest.cid
                	WHERE
                		cf_contest.start_time_stamp >= :startTime
                		AND cf_contest.start_time_stamp <= :endTime
                		AND cf_account.uid IN :uids
                	GROUP BY
                		uid
                """;
        var query = entityManager.createNativeQuery(sql);
        query.setParameter("startTime",start);
        query.setParameter("endTime",end);
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
    public Map<Long,Integer> getAcTimeSpanParticipate(List<Long> uids, Long start, Long end) {
        var entityManager = entityManagerFactory.createEntityManager();
        var sql = """
                SELECT
                	uid,
                	count( * ) as participate
                FROM
                	ac_stucontest
                	LEFT JOIN ac_account ON ac_stucontest.acid = ac_account.id
                	LEFT JOIN ac_contest ON ac_contest.id = ac_stucontest.cid
                WHERE
                	ac_contest.start_time_stamp >= :startTime
                	and
                	ac_contest.start_time_stamp <= :endTime
                	and ac_account.uid in :uids
                GROUP BY
                	uid
                """;
        var query = entityManager.createNativeQuery(sql);
        query.setParameter("startTime",start);
        query.setParameter("endTime",end);
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

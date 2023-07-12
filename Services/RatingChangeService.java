package org.algotithmcontestdatacollect.displaybackend.Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigInteger;
import java.util.*;

@Service
public class RatingChangeService {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public Map<Long,Integer> getCodeforcesSpanRatingChange(List<Long> uids,Long start,Long end){
        var startResult = getCodeforcesRatingByUserIdsAndTimePoint(uids,start);
        var endResult = getCodeforcesRatingByUserIdsAndTimePoint(uids,end);
        Map<Long,Integer> ret = new HashMap<>();
        for(var item : startResult.entrySet()){
            ret.put(item.getKey(),endResult.get(item.getKey()) - item.getValue());
        }
        for(var item : endResult.entrySet()) {
            if (!ret.containsKey(item.getKey())) {
                ret.put(item.getKey(),item.getValue());
            }
        }
        return ret;
    }

    public Map<Long,Integer> getAtcoderSpanRatingChange(List<Long> uids,Long start,Long end){
        var startResult = getAtcoderRatingByUserIdsAndTimePoint(uids,start);
        var endResult = getAtcoderRatingByUserIdsAndTimePoint(uids,end);
        Map<Long,Integer> ret = new HashMap<>();
        for(var item : startResult.entrySet()){
            ret.put(item.getKey(),endResult.get(item.getKey()) - item.getValue());
        }
        for(var item : endResult.entrySet()) {
            if (!ret.containsKey(item.getKey())) {
                ret.put(item.getKey(),item.getValue());
            }
        }
        return ret;
    }

    public Map<Long,Integer> getAtcoderRatingByUserIdsAndTimePoint(List<Long> uids,Long time) {
        var entityManager = entityManagerFactory.createEntityManager();
        String atcoderRatingChangerUpperSql = """
                SELECT
                            	t1.uid,
                            	t1.acid,
                            	t1.rating
                            FROM
                            	(
                            	SELECT
                            		ac_stucontest.*,
                            		ac_account.uid,
                            		ac_account.is_main,
                            		ac_contest.start_time_stamp
                            	FROM
                            		ac_stucontest
                            		LEFT JOIN ac_contest ON ac_stucontest.cid = ac_contest.id
                            		LEFT JOIN ac_account ON ac_stucontest.acid = ac_account.id
                            	) AS t1
                            	LEFT JOIN ( SELECT acid, max( start_time_stamp ) AS max_time FROM ac_stucontest_with_cinfo_userinfo WHERE start_time_stamp < :endTime and diff <> 0 GROUP BY acid ) AS t2 ON t1.acid = t2.acid
                            WHERE
                            	t1.start_time_stamp = t2.max_time
                            	AND t1.is_main = 1 and t1.uid in :uids
                """;
        var query = entityManager.createNativeQuery(atcoderRatingChangerUpperSql);
        query.setParameter("endTime", time);
        query.setParameter("uids",  uids );
        Map<Long,Integer> ret = new HashMap<>();
        var results = query.getResultList();
        for(var obj : results){
            Object[] objs = (Object[]) obj;
            ret.put(((BigInteger) objs[0]).longValue(),(Integer) objs[2]);
        }
        entityManager.close();
        return ret;
    }

    public Map<Long,Integer> getCodeforcesRatingByUserIdsAndTimePoint(List<Long> uids,Long time) {
        var entityManager = entityManagerFactory.createEntityManager();
        String codeforcesRatingChangeUpperSql = """
                SELECT
                	t1.uid,
                	t1.cfid,
                	t1.rating
                FROM
                	(
                	SELECT
                		cf_stucontest.*,
                		cf_account.uid,
                		cf_account.is_main,
                		cf_contest.start_time_stamp
                	FROM
                		cf_stucontest
                		LEFT JOIN cf_contest ON cf_stucontest.cid = cf_contest.cid
                		LEFT JOIN cf_account ON cf_stucontest.cfid = cf_account.id
                	) AS t1
                	LEFT JOIN ( SELECT cfid, max( start_time_stamp ) AS max_time FROM cf_stucontest_with_cinfo_userinfo WHERE start_time_stamp < :endTime and diff <> 0 GROUP BY cfid ) AS t2 ON t1.cfid = t2.cfid
                WHERE
                	t1.start_time_stamp = t2.max_time
                	AND t1.is_main = 1 and t1.uid in :uids 
                """;
        var query = entityManager.createNativeQuery(codeforcesRatingChangeUpperSql);
        query.setParameter("endTime", time);
        query.setParameter("uids",  uids );
        Map<Long,Integer> ret = new HashMap<>();
        var results = query.getResultList();
        for(var obj : results){
            Object[] objs = (Object[]) obj;
            ret.put(((BigInteger) objs[0]).longValue(),(Integer) objs[2]);
        }
        entityManager.close();
        return ret;
    }

    public Long getTheEldestCodeforcesParticipateStartTime() {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createNativeQuery("SELECT min(start_time_stamp) FROM cf_stucontest_with_cinfo_userinfo");
        var result = query.getResultList();
        entityManager.close();
        return ((BigInteger) result.get(0)).longValue();
    }

    public Long getTheEldestAtcoderParticipateStartTime() {
        var entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createNativeQuery("SELECT min(start_time_stamp) FROM ac_stucontest_with_cinfo_userinfo");
        var result = query.getResultList();
        entityManager.close();
        return ((BigInteger) result.get(0)).longValue();
    }

    public List<Date> generateDateRangeToNow(Long start){
        List<Date> ret = new ArrayList<>();
        var latest = new Date(start*1000);
        var now = new Date();
        var Year = latest.getYear();
        var Month = latest.getMonth();
        while(Year != now.getYear() || Month != now.getMonth()) {
            ret.add(new Date(Year,Month,1));
            Month++;
            if(Month == 12){
                Year++;
                Month = 0;
            }
        }
        ret.add(new Date(Year,Month,1));
        ret.add(now);
        return ret;
    }



}

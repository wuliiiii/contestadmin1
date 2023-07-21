package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "no_recommend_contest")
public class NoRecommendContest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 这场比赛不应该被推荐
     */
    @Id
    @Column(name = "cid", nullable = false)
    private Long cid;

    /**
     * 这场比赛不应该被推荐
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }

    /**
     * 这场比赛不应该被推荐
     */
    public Long getCid() {
        return cid;
    }

    @Override
    public String toString() {
        return "NoRecommendContest{" +
               "cid=" + cid + '\'' +
               '}';
    }

    public NoRecommendContest(Long cid) {
        this.cid = cid;
    }

    public NoRecommendContest() {
    }
}

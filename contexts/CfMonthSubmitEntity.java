package org.algotithmcontestdatacollect.displaybackend.Entities.CodeforcesEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_month_submit", schema = "graduate")
@IdClass(CfMonthSubmitEntityPK.class)
public class CfMonthSubmitEntity {
    @Basic
    @Id
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Id
    @Column(name = "cfid", nullable = false)
    private Long cfid;
    @Basic
    @Column(name = "ym_date", nullable = true, length = 7)
    private String ymDate;
    @Basic
    @Column(name = "submit_cnt", nullable = false)
    private long submitCnt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCfid() {
        return cfid;
    }

    public void setCfid(Long cfid) {
        this.cfid = cfid;
    }

    public String getYmDate() {
        return ymDate;
    }

    public void setYmDate(String ymDate) {
        this.ymDate = ymDate;
    }

    public long getSubmitCnt() {
        return submitCnt;
    }

    public void setSubmitCnt(long submitCnt) {
        this.submitCnt = submitCnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfMonthSubmitEntity that = (CfMonthSubmitEntity) o;
        return submitCnt == that.submitCnt && Objects.equals(username, that.username) && Objects.equals(cfid, that.cfid) && Objects.equals(ymDate, that.ymDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, cfid, ymDate, submitCnt);
    }
}

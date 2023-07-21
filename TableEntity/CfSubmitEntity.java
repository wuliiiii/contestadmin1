package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import javax.persistence.*;

@Entity
@Table(name = "cf_submit")
public class CfSubmitEntity {
    @Id
    @Column(name = "sid")
    private Long sid;
    @Basic
    @Column(name = "cid")
    private Long cid;
    @Basic
    @Column(name = "cfid")
    private Long cfid;
    @Basic
    @Column(name = "q_index")
    private String Qindex;
    @Basic
    @Column(name = "submit_time")
    private long submitTime;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "is_after")
    private byte isAfter;
    @Basic
    @Column(name = "language")
    private String language;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getCfid() {
        return cfid;
    }

    public void setCfid(Long cfid) {
        this.cfid = cfid;
    }

    public String getQindex() {
        return Qindex;
    }

    public void setQindex(String index) {
        this.Qindex = index;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(byte isAfter) {
        this.isAfter = isAfter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfSubmitEntity that = (CfSubmitEntity) o;

        if (submitTime != that.submitTime) return false;
        if (isAfter != that.isAfter) return false;
        if (sid != null ? !sid.equals(that.sid) : that.sid != null) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (cfid != null ? !cfid.equals(that.cfid) : that.cfid != null) return false;
        if (Qindex != null ? !Qindex.equals(that.Qindex) : that.Qindex != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sid != null ? sid.hashCode() : 0;
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (cfid != null ? cfid.hashCode() : 0);
        result = 31 * result + (Qindex != null ? Qindex.hashCode() : 0);
        result = 31 * result + (int) (submitTime ^ (submitTime >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) isAfter;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}

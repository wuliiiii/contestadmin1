package org.algotithmcontestdatacollect.displaybackend.Entities.CodeforcesEntities;

import javax.persistence.*;

@Entity
@Table(name = "cf_contest_with_participate", schema = "graduate")
public class CfContestWithParticipateEntity {
    @Id
    @Basic
    @Column(name = "cid")
    private Long cid;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "level")
    private Integer level;
    @Basic
    @Column(name = "start_time_stamp")
    private Long startTimeStamp;
    @Basic
    @Column(name = "end_time_stamp")
    private Long endTimeStamp;
    @Basic
    @Column(name = "duration")
    private Long duration;
    @Basic
    @Column(name = "is_normal")
    private Byte isNormal;
    @Basic
    @Column(name = "participate")
    private long participate;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(Long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Byte getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Byte isNormal) {
        this.isNormal = isNormal;
    }

    public long getParticipate() {
        return participate;
    }

    public void setParticipate(long participate) {
        this.participate = participate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfContestWithParticipateEntity that = (CfContestWithParticipateEntity) o;

        if (participate != that.participate) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (startTimeStamp != null ? !startTimeStamp.equals(that.startTimeStamp) : that.startTimeStamp != null)
            return false;
        if (endTimeStamp != null ? !endTimeStamp.equals(that.endTimeStamp) : that.endTimeStamp != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (isNormal != null ? !isNormal.equals(that.isNormal) : that.isNormal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cid != null ? cid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (startTimeStamp != null ? startTimeStamp.hashCode() : 0);
        result = 31 * result + (endTimeStamp != null ? endTimeStamp.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (isNormal != null ? isNormal.hashCode() : 0);
        result = 31 * result + (int) (participate ^ (participate >>> 32));
        return result;
    }
}

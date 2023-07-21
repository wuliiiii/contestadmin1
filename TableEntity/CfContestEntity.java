package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_contest", schema = "graduate")
public class CfContestEntity {
    @Id
    @Column(name = "cid", nullable = false)
    private Long cid;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "type", nullable = false, length = 255)
    private String type;
    @Basic
    @Column(name = "level", nullable = false)
    private int level;
    @Basic
    @Column(name = "start_time_stamp", nullable = false)
    private long startTimeStamp;
    @Basic
    @Column(name = "end_time_stamp", nullable = false)
    private long endTimeStamp;
    @Basic
    @Column(name = "duration", nullable = false)
    private long duration;
    @Basic
    @Column(name = "is_normal", nullable = false)
    private Short isNormal;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Short getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Short isNormal) {
        this.isNormal = isNormal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfContestEntity that = (CfContestEntity) o;
        return level == that.level && startTimeStamp == that.startTimeStamp && endTimeStamp == that.endTimeStamp && duration == that.duration && isNormal == that.isNormal && Objects.equals(cid, that.cid) && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, name, type, level, startTimeStamp, endTimeStamp, duration, isNormal);
    }

    @Override
    public String toString() {
        return "CfContestEntity{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", level=" + level +
                ", startTimeStamp=" + startTimeStamp +
                ", endTimeStamp=" + endTimeStamp +
                ", duration=" + duration +
                ", isNormal=" + isNormal +
                '}';
    }

    public static CfContestEntity CreateCfContestEntityFromCFContestList(JSONObject obejct) {
        var cid = obejct.getLong("id");
        var name = obejct.getString("name");
        var type = obejct.getString("type");
        int level = -1;
        if(name.contains("Div.1")||name.contains("Div. 1")){
            level = 1;
        }else if (name.contains("Div.2")||name.contains("Div. 2")){
            level = 2;
        }else if(name.contains("Div.3")||name.contains("Div. 3")){
            level = 3;
        }else if(name.contains("Div.4")||name.contains("Div. 4")){
            level = 4;
        }
        Long start = obejct.getLong("startTimeSeconds");
        Long duration = obejct.getLong("durationSeconds");
        return new CfContestEntity(cid,name,type,level,start,start+duration,duration, (short) 1);
    }

    public CfContestEntity(Long cid, String name, String type, int level, Long startTimeStamp, Long endTimeStamp, Long duration, Short isNormal) {
        this.cid = cid;
        this.name = name;
        this.type = type;
        this.level = level;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.duration = duration;
        this.isNormal = isNormal;
    }

    public CfContestEntity() {
    }
}

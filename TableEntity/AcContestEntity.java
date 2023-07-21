package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "ac_contest", schema = "graduate")
public class AcContestEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "nickname")
    private String nickname;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "level")
    private Integer level;
    @Basic
    @Column(name = "start_time_stamp")
    private long startTimeStamp;
    @Basic
    @Column(name = "end_time_stamp")
    private long endTimeStamp;
    @Basic
    @Column(name = "duration")
    private long duration;
    @Basic
    @Column(name = "is_normal")
    private byte isNormal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void setDuaration(long duration) {
        this.duration = duration;
    }

    public byte getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(byte isNormal) {
        this.isNormal = isNormal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcContestEntity that = (AcContestEntity) o;

        if (id != that.id) return false;
        if (startTimeStamp != that.startTimeStamp) return false;
        if (endTimeStamp != that.endTimeStamp) return false;
        if (duration != that.duration) return false;
        if (isNormal != that.isNormal) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (int) (startTimeStamp ^ (startTimeStamp >>> 32));
        result = 31 * result + (int) (endTimeStamp ^ (endTimeStamp >>> 32));
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (int) isNormal;
        return result;
    }

    public static AcContestEntity fromJSONObject(JSONObject jsonObject) {
        AcContestEntity acContestEntity = new AcContestEntity();
        acContestEntity.setId(null);
        acContestEntity.setName(jsonObject.getString("name"));
        acContestEntity.setNickname(jsonObject.getString("nickName"));
        acContestEntity.setStartTimeStamp(jsonObject.getLong("startTime"));
        acContestEntity.setDuaration(jsonObject.getLong("duration"));
        acContestEntity.setEndTimeStamp(jsonObject.getLong("startTime") + jsonObject.getLong("duration"));
        acContestEntity.setIsNormal(Byte.parseByte("1"));
        if (acContestEntity.getNickname().contains("abc")){
            acContestEntity.setType("abc");
            acContestEntity.setLevel(1);
        }else if(acContestEntity.getNickname().contains("arc")) {
            acContestEntity.setType("arc");
            acContestEntity.setLevel(2);
        }else if (acContestEntity.getNickname().contains("agc")) {
            acContestEntity.setType("agc");
            acContestEntity.setLevel(3);
        }else {
            acContestEntity.setLevel(2);
            acContestEntity.setType("");
        }
        return acContestEntity;
    }
}

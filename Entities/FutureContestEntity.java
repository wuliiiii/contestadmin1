package org.algotithmcontestdatacollect.displaybackend.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "future_contest", schema = "graduate", catalog = "")
public class FutureContestEntity {
    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "nickname", nullable = true, length = 255)
    private String nickname;
    @Basic
    @Column(name = "start_time_stamp", nullable = false)
    private long startTimeStamp;
    @Basic
    @Column(name = "duration", nullable = false)
    private long duration;
    @Basic
    @Column(name = "type", nullable = false, length = 2)
    private String type;

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

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FutureContestEntity that = (FutureContestEntity) o;
        return startTimeStamp == that.startTimeStamp && duration == that.duration && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nickname, startTimeStamp, duration);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

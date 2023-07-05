package org.algotithmcontestdatacollect.displaybackend.Entities.CodeforcesEntities;

import javax.persistence.*;

@Entity
@Table(name = "cf_account_total_info", schema = "graduate")
public class CfAccountTotalInfoEntity {
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "classname")
    private String classname;
    @Basic
    @Column(name = "school")
        private Long school;
    @Basic
    @Column(name = "status")
    private Byte status;
    @Basic
    @Column(name = "stu_no")
    private String stuNo;
    @Basic
    @Column(name = "year")
    private Integer year;
    @Id
    @Basic
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "uid")
    private Long uid;
    @Basic
    @Column(name = "codeforces_id")
    private String codeforcesId;
    @Basic
    @Column(name = "rating")
    private Integer rating;
    @Basic
    @Column(name = "is_main")
    private byte isMain;
    @Basic
    @Column(name = "is_using")
    private byte isUsing;
    @Basic
    @Column(name = "participate_time")
    private long participateTime;
    @Basic
    @Column(name = "solve")
    private Long solve;
    @Basic
    @Column(name = "after")
    private Long after;
    @Basic
    @Column(name = "realname")
    private String realname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }


    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCodeforcesId() {
        return codeforcesId;
    }

    public void setCodeforcesId(String codeforcesId) {
        this.codeforcesId = codeforcesId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public byte getIsMain() {
        return isMain;
    }

    public void setIsMain(byte isMain) {
        this.isMain = isMain;
    }

    public byte getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(byte isUsing) {
        this.isUsing = isUsing;
    }

    public long getParticipateTime() {
        return participateTime;
    }

    public void setParticipateTime(long participateTime) {
        this.participateTime = participateTime;
    }

    public Long getSolve() {
        return solve;
    }

    public void setSolve(Long solve) {
        this.solve = solve;
    }

    public Long getAfter() {
        return after;
    }

    public void setAfter(Long after) {
        this.after = after;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfAccountTotalInfoEntity that = (CfAccountTotalInfoEntity) o;

        if (isMain != that.isMain) return false;
        if (isUsing != that.isUsing) return false;
        if (participateTime != that.participateTime) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (classname != null ? !classname.equals(that.classname) : that.classname != null) return false;
        if (school != null ? !school.equals(that.school) : that.school != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (stuNo != null ? !stuNo.equals(that.stuNo) : that.stuNo != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (codeforcesId != null ? !codeforcesId.equals(that.codeforcesId) : that.codeforcesId != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (solve != null ? !solve.equals(that.solve) : that.solve != null) return false;
        if (after != null ? !after.equals(that.after) : that.after != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (classname != null ? classname.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (stuNo != null ? stuNo.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (codeforcesId != null ? codeforcesId.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (int) isMain;
        result = 31 * result + (int) isUsing;
        result = 31 * result + (int) (participateTime ^ (participateTime >>> 32));
        result = 31 * result + (solve != null ? solve.hashCode() : 0);
        result = 31 * result + (after != null ? after.hashCode() : 0);
        return result;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}

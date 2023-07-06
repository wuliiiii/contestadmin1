package org.algotithmcontestdatacollect.displaybackend.Entities.CodeforcesEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_stucontest_with_cinfo_userinfo", schema = "graduate")
public class CfStucontestWithCinfoUserinfoEntity {
    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "cid", nullable = false)
    private Long cid;
    @Basic
    @Column(name = "cfid", nullable = false)
    private Long cfid;
    @Basic
    @Column(name = "t_rank", nullable = false)
    private int tRank;
    @Basic
    @Column(name = "solve", nullable = false)
    private int solve;
    @Basic
    @Column(name = "diff", nullable = false)
    private int diff;
    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;
    @Basic
    @Column(name = "codeforces_id", nullable = true, length = 255)
    private String codeforcesId;
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "classname", nullable = true, length = 255)
    private String classname;
    @Basic
    @Column(name = "stu_no", nullable = true, length = 255)
    private String stuNo;
    @Basic
    @Column(name = "school", nullable = true)
    private Long school;
    @Basic
    @Column(name = "realname", nullable = true, length = 255)
    private String realname;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "start_time_stamp", nullable = true)
    private Long startTimeStamp;

    @Basic
    @Column(name = "is_main", nullable = true)
    private Integer isMain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int gettRank() {
        return tRank;
    }

    public void settRank(int tRank) {
        this.tRank = tRank;
    }

    public int getSolve() {
        return solve;
    }

    public void setSolve(int solve) {
        this.solve = solve;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCodeforcesId() {
        return codeforcesId;
    }

    public void setCodeforcesId(String codeforcesId) {
        this.codeforcesId = codeforcesId;
    }

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

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CfStucontestWithCinfoUserinfoEntity)) return false;
        CfStucontestWithCinfoUserinfoEntity that = (CfStucontestWithCinfoUserinfoEntity) o;
        return tRank == that.tRank && solve == that.solve && diff == that.diff && rating == that.rating && Objects.equals(id, that.id) && Objects.equals(cid, that.cid) && Objects.equals(cfid, that.cfid) && Objects.equals(codeforcesId, that.codeforcesId) && Objects.equals(username, that.username) && Objects.equals(classname, that.classname) && Objects.equals(stuNo, that.stuNo) && Objects.equals(school, that.school) && Objects.equals(realname, that.realname) && Objects.equals(name, that.name) && Objects.equals(startTimeStamp, that.startTimeStamp) && Objects.equals(isMain, that.isMain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cid, cfid, tRank, solve, diff, rating, codeforcesId, username, classname, stuNo, school, realname, name, startTimeStamp, isMain);
    }
}

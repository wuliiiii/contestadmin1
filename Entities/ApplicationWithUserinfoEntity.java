package org.algotithmcontestdatacollect.displaybackend.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "application_with_userinfo", schema = "graduate", catalog = "")
public class ApplicationWithUserinfoEntity {
    @Basic
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "uid", nullable = true)
    private Long uid;
    @Basic
    @Column(name = "time", nullable = false)
    private long time;
    @Basic
    @Column(name = "opertation", nullable = false, length = 255)
    private String opertation;
    @Basic
    @Column(name = "parameter", nullable = false)
    private String parameter;
    @Basic
    @Column(name = "status", nullable = false)
    private byte status;
    @Basic
    @Column(name = "admin_name", nullable = true, length = 255)
    private String adminName;
    @Basic
    @Column(name = "operation_time", nullable = true)
    private Long operationTime;
    @Basic
    @Column(name = "school", nullable = true)
    private Long school;
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "stu_no", nullable = true, length = 255)
    private String stuNo;
    @Basic
    @Column(name = "classname", nullable = true, length = 255)
    private String classname;
    @Basic
    @Column(name = "year", nullable = true)
    private Integer year;
    @Basic
    @Column(name = "realname", nullable = true, length = 255)
    private String realname;
    @Basic
    @Column(name = "ustatus", nullable = true)
    private Byte ustatus;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getOpertation() {
        return opertation;
    }

    public void setOpertation(String opertation) {
        this.opertation = opertation;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Byte getUstatus() {
        return ustatus;
    }

    public void setUstatus(Byte ustatus) {
        this.ustatus = ustatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationWithUserinfoEntity that = (ApplicationWithUserinfoEntity) o;
        return time == that.time && status == that.status && Objects.equals(id, that.id) && Objects.equals(uid, that.uid) && Objects.equals(opertation, that.opertation) && Objects.equals(parameter, that.parameter) && Objects.equals(adminName, that.adminName) && Objects.equals(operationTime, that.operationTime) && Objects.equals(school, that.school) && Objects.equals(username, that.username) && Objects.equals(stuNo, that.stuNo) && Objects.equals(classname, that.classname) && Objects.equals(year, that.year) && Objects.equals(realname, that.realname) && Objects.equals(ustatus, that.ustatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, time, opertation, parameter, status, adminName, operationTime, school, username, stuNo, classname, year, realname, ustatus);
    }
}

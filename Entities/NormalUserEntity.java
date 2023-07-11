package org.algotithmcontestdatacollect.displaybackend.Entities;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "normal_user", schema = "graduate", catalog = "")
public class NormalUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "username", nullable = false, length = 255)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "school", nullable = true)
    private Long school;
    @Basic
    @Column(name = "classname", nullable = true, length = 255)
    private String classname;
    @Basic
    @Column(name = "stu_no", nullable = false, length = 255)
    private String stuNo;
    @Basic
    @Column(name = "year", nullable = true)
    private Integer year;
    @Basic
    @Column(name = "status", nullable = true)
    private Byte status;

    @Basic
    @Column(name = "realname", nullable = true, length = 255)
    private String realname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalUserEntity that = (NormalUserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(school, that.school) && Objects.equals(classname, that.classname) && Objects.equals(stuNo, that.stuNo) && Objects.equals(year, that.year) && Objects.equals(status, that.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, school, classname, stuNo, year, status);
    }
}

package org.algotithmcontestdatacollect.displaybackend.Entities;

import javax.persistence.*;

import net.minidev.json.JSONObject;

import java.util.Objects;

@Entity
@Table(name = "application", schema = "graduate")
public class ApplicationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "admin_name", nullable = true)
    private String adminName;
    @Basic
    @Column(name = "operation_time", nullable = true)
    private Long operationTime;

    @Basic
    @Column(name = "school", nullable = false)
    private Long school;

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

    public Long getOperationTime() {
        return operationTime;
    }

    public String getAdminName() {
        return adminName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ApplicationEntity that = (ApplicationEntity) o;
        return time == that.time && status == that.status && operationTime == that.operationTime
                && Objects.equals(id, that.id) && Objects.equals(uid, that.uid)
                && Objects.equals(opertation, that.opertation) && Objects.equals(parameter, that.parameter)
                && Objects.equals(adminName, that.adminName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, time, opertation, parameter, status, adminName, operationTime);
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    static public ApplicationEntity createNewUser(String username, String password, Long school, String classname, Integer year, String stuno,String realname) {
        ApplicationEntity entity = new ApplicationEntity();
        var parameter = new JSONObject();
        parameter.put("username", username);
        parameter.put("password", password);
        parameter.put("school", school);
        parameter.put("classname", classname);
        parameter.put("year", year);
        parameter.put("stuno", stuno);
        parameter.put("realname",realname);
        entity.opertation = "createNewUser";
        entity.parameter = parameter.toJSONString();
        entity.school = school;
        entity.time = System.currentTimeMillis();
        entity.status = 0;
        entity.adminName = null;
        entity.operationTime = null;
        return entity;
    }
    static public ApplicationEntity addCodeforcesAccount(String codeforcesAccount,Long uid,Byte is_main,Long school) {
        ApplicationEntity entity = new ApplicationEntity();
        var parameter = new JSONObject();
        entity.uid = uid;
        parameter.put("codeforcesAccount", codeforcesAccount);
        parameter.put("is_main", is_main);
        entity.opertation = "addCodeforcesAccount";
        entity.parameter = parameter.toJSONString();
        entity.school = school;
        entity.time = System.currentTimeMillis();
        entity.status = 0;
        entity.adminName = null;
        entity.operationTime = null;
        return entity;
    }
    static public ApplicationEntity addAtcoderAccount(String atcoderAccount,Long uid,Byte is_main,Long school) {
        ApplicationEntity entity = new ApplicationEntity();
        var parameter = new JSONObject();
        entity.uid = uid;
        parameter.put("atcoderAccount", atcoderAccount);
        parameter.put("is_main", is_main);
        entity.opertation = "addAtcoderAccount";
        entity.parameter = parameter.toJSONString();
        entity.school = school;
        entity.time = System.currentTimeMillis();
        entity.status = 0;
        entity.adminName = null;
        entity.operationTime = null;
        return entity;
    }
}

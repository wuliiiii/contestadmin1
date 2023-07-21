package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import org.algorithmcontestdatacollect.crawlerendpoint2.OtherEntities.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.OtherEntities.RequestParams;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "spider_log", schema = "graduate")
public class SpiderLogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "message", nullable = true, length = 255)
    private String message;
    @Basic
    @Column(name = "stage", nullable = true, length = 255)
    private String stage;
    @Basic
    @Column(name = "type", nullable = true, length = 255)
    private String type;
    @Basic
    @Column(name = "status_code", nullable = true)
    private Integer statusCode;
    @Basic
    @Column(name = "request_url", nullable = true, length = 255)
    private String requestUrl;
    @Basic
    @Column(name = "request_method", nullable = true, length = 255)
    private String requestMethod;
    @Basic
    @Column(name = "spider_name", nullable = true,length = 255)
    private String spiderName;
    @Basic
    @Column(name = "timestamp", nullable = true)
    private Long timestamp;

    public String getSpiderName() {
        return spiderName;
    }

    public void setSpiderName(String spiderName) {
        this.spiderName = spiderName;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpiderLogEntity that = (SpiderLogEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(message, that.message) && Objects.equals(stage, that.stage) && Objects.equals(type, that.type) && Objects.equals(statusCode, that.statusCode) && Objects.equals(requestUrl, that.requestUrl) && Objects.equals(requestMethod, that.requestMethod) && Objects.equals(spiderName, that.spiderName) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, stage, type, statusCode, requestUrl, requestMethod, spiderName, timestamp);
    }

    public static SpiderLogEntity fromFaultAndRequestParam(Fault fault,RequestParams requestParams,String spiderName) {
        var ret = new SpiderLogEntity();
        ret.setStatusCode(fault.getStatusCode());
        ret.setStage(fault.getStage());
        ret.setMessage(fault.getMessage());
        ret.setType(fault.getType());
        ret.setTimestamp(fault.getTimeStamp());
        if (requestParams != null) {
            ret.setRequestMethod(requestParams.getMethod());
            ret.setRequestUrl(requestParams.getUrl());
        }
        ret.setSpiderName(spiderName);
        return ret;
    }
}

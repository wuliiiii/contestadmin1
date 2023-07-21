package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders;

public class Fault {
    private Integer statusCode;
    private String message;
    private String stage;
    private String type;
    private Long timeStamp;
    public Fault(){
        this.timeStamp = System.currentTimeMillis() / 1000;
    }

    public Fault(String stage, String type,String message) {
        this.message = message;
        this.stage = stage;
        this.type = type;
        this.timeStamp = System.currentTimeMillis() / 1000;
    }

    public Fault(String stage, String type,Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.stage = stage;
        this.type = type;
        this.timeStamp = System.currentTimeMillis() / 1000;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

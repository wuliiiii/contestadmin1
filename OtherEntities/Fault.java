package org.algorithmcontestdatacollect.crawlerendpoint2.OtherEntities;
//供了一种表示错误信息的数据结构，可以用于在程序中传递和记录错误相关的信息
public class Fault {
    private Integer statusCode;
    private String message;
    private String stage;
    private String type;
    private Long timeStamp;
    public Fault(){}

//    public Fault(String stage, String type,String message) {
//        this.message = message;
//        this.stage = stage;
//        this.type = type;
//    }
//
//    public Fault(String stage, String type,Integer statusCode, String message) {
//        this.statusCode = statusCode;
//        this.message = message;
//        this.stage = stage;
//        this.type = type;
//    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
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
}

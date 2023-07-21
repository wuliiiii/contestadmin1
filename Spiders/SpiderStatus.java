package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders;



public class SpiderStatus {
    private String spiderName;
    private Integer runningTaskNum;
    private Integer queueTaskNum;

    public String getSpiderName() {
        return spiderName;
    }

    public void setSpiderName(String spiderName) {
        this.spiderName = spiderName;
    }

    public Integer getRunningTaskNum() {
        return runningTaskNum;
    }

    public void setRunningTaskNum(Integer runningTaskNum) {
        this.runningTaskNum = runningTaskNum;
    }

    public Integer getQueueTaskNum() {
        return queueTaskNum;
    }

    public void setQueueTaskNum(Integer queueTaskNum) {
        this.queueTaskNum = queueTaskNum;
    }
}

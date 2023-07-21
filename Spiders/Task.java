package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders;


import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    protected Spider spider;

    protected RequestParams requestParams;

    protected Fault fault;
    protected final static int INIT = 0;
    protected final static int IN_QUEUE = 1;
    protected final static int RUNNING = 2;
    protected final static int DOWNLOAD_SUCCESS = 3;
    protected final static int DOWNLOAD_ERROR = 4;
    protected final static int ANALYSE_SUCCESS = 5;
    protected final static int ANALYSE_ERROR = 6;
    Map<String,Object> result;
    Queue<Task> theQueue;
    String responseDetail = "";
    protected AtomicInteger status = new AtomicInteger(INIT);

    public Task(RequestParams requestParams) {
        this.requestParams = requestParams;
    }

    public void inQueue(Queue<Task> q) {
        theQueue = q;
        status.compareAndSet(INIT,IN_QUEUE);
    }
    public void running() {
        status.compareAndSet(IN_QUEUE,RUNNING);
    }
    public void downloadSuccess(String res) {
        this.responseDetail = res;
        status.set(DOWNLOAD_SUCCESS);
    }
    public void processSuccess(Map<String,Object> result) {
        this.result = result;
        status.set(ANALYSE_SUCCESS);
    }
    public void processError(Fault fault) {
        this.fault = fault;
        status.set(ANALYSE_ERROR);
    }
    public void downloadError(Fault fault) {
        this.fault = fault;
        status.set(DOWNLOAD_ERROR);
    }
    public Queue<Task> getTheQueue() {
        return theQueue;
    }

    public String getResponseDetail() {
        return responseDetail;
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }

    public Integer getStatus() {
        return status.get();
    }

    public Fault getFault() {
        return fault;
    }

    public void setFault(Fault fault) {
        this.fault = fault;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public Spider getSpider() {
        return spider;
    }
}

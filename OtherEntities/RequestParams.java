package org.algorithmcontestdatacollect.crawlerendpoint2.OtherEntities;
//用于封装请求的方法和URL信息，方便在程序中传递和处理请求参数
public class RequestParams {
    protected String method;
    protected String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}

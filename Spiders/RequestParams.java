package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class RequestParams {

    protected Map<String,String> data;
    protected Map<String,String> header;
    protected Map<String,String> cookies;
    protected HttpMethod method;
    protected String url;

    public RequestParams(String url) {
        this(url,HttpMethod.GET,null);
    }

    public RequestParams(String url, Map<String, String> header, Map<String, String> cookies) {
        this(url,HttpMethod.GET,null,header,cookies);
    }

    public RequestParams(String url, HttpMethod method, Map<String, String> data) {
        this(url,method,data,null,null);
    }

    public RequestParams(String url, HttpMethod method, Map<String, String> data, Map<String, String> header, Map<String, String> cookies) {
        this.url = url;
        this.method = method;
        this.data = data;
        this.header = header;
        this.cookies = cookies;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public MultiValueMap<String,Object> getMultiValueMapData() {
        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
        if(getData() != null) {
            for (var set : getData().entrySet()) {
                data.add(set.getKey(),set.getValue());
            }
        }
        return data;
    }
    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if(header != null) {
            for (var set : getHeader().entrySet()) {
                headers.add(set.getKey(),set.getValue());
            }
        }
        return headers;
    }
    public HttpEntity<MultiValueMap<String, Object>> getRequestEntity() {
        return new HttpEntity<>(getMultiValueMapData(),getHttpHeaders());
    }
}

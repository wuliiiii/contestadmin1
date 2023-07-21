package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Downloaders;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.algorithmcontestdatacollect.crawlerendpoint2.Utils.UserAgentUtil;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateDownloader implements IDownloader {

    RestTemplate restTemplate;

    public RestTemplateDownloader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void download(Task task) {
        RequestParams requestParams = task.getRequestParams();
        var url = requestParams.getUrl();
        var method = requestParams.getMethod();
        var entity = requestParams.getRequestEntity();
        var newHeader = new HttpHeaders();
        newHeader.addAll(entity.getHeaders());
        newHeader.add(HttpHeaders.USER_AGENT,UserAgentUtil.randomUserAgent());
        var newEntity = new HttpEntity<MultiValueMap<String, Object>>(entity.getBody(),newHeader);
        try {
            var result = restTemplate.exchange(url,method,newEntity,String.class);
            task.downloadSuccess(result.getBody());
        }catch (HttpClientErrorException exception) {
            int exp = exception.getRawStatusCode();
            var fault = new Fault();
            fault.setStage("download");
            fault.setStatusCode(exp);
            fault.setMessage(exception.getStatusText());
            fault.setType("HttpClient");
            task.downloadError(fault);
        }catch (HttpServerErrorException exception) {
            int exp = exception.getRawStatusCode();
            var fault = new Fault();
            fault.setStage("download");
            fault.setStatusCode(exp);
            fault.setMessage(exception.getStatusText());
            fault.setType("HttpServer");
            task.downloadError(fault);
        }catch (Exception exception) {
            var fault = new Fault();
            fault.setStage("download");
            fault.setMessage(exception.getMessage());
            fault.setType("Other");
            task.downloadError(fault);
        }
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

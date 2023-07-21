package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.failPipelines;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.IPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerFailPipeline implements IPipeline {
    private final Logger logger = LoggerFactory.getLogger(LoggerFailPipeline.class);
    String errMassageTemplate = """
            %s Stage Error
            Url: %s;
            StatusCode: %s;
            Type: %s;
            Message:%s;
            """;
    @Override
    public void process(Task task) {
        var fault = task.getFault();
        if(fault.getStage().equals("download")) {
            logger.error(String.format(errMassageTemplate,fault.getStage(),task.getRequestParams().getUrl(),fault.getStatusCode(),fault.getType(),fault.getMessage()));
        }else{
            logger.error(String.format(errMassageTemplate,fault.getStage(),task.getRequestParams().getUrl(),"null",fault.getType(),fault.getMessage()));
        }
    }


}

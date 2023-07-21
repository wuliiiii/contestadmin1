package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.sucessPipelines;


import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.IPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class LogSuccessPipeline implements IPipeline {

    private final Logger logger = LoggerFactory.getLogger(LogSuccessPipeline.class);
    @Override
    public void process(Task task) {
        logger.info(task.getRequestParams().getUrl() + " success");
//        logger.info(task.getResult().toString());
    }
}

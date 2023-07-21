package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.sucessPipelines;

import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.IPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.context.ApplicationContext;

public class DirectPipeline implements IPipeline {
    public DirectPipeline(ApplicationContext context) {
        this.context = context;
    }
    ApplicationContext context;
    @Override
    public void process(Task task) {
        context.getBean(task.getResult().get("handler").toString(), AbstractHandler.class).handle(task.getResult().get("result").toString());
    }
}

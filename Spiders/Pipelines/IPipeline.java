package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;

public interface IPipeline {
    void process(Task task);
}

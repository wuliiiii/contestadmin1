package org.algorithmcontestdatacollect.crawlerendpoint2.Services;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Fault;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.RequestParams;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public interface IErrorHandler {
    void handleTask(Fault fault,@Nullable RequestParams requestParams);
}

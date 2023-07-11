package org.algorithmcontestdatacollect.crawlerendpoint2.Dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AtcoderContestSubmitDispatcher extends AbstractDispatcher {
    @Autowired
    private Environment environment;

    @Override
    protected String getSpider() {
        return "AtcoderContestSubmitSpider";
    }

    @Override
    protected String getHandler() {
        return "SingleAtcoderContestSubmitHandler";
    }

    @Override
    protected String getStream() {
        return environment.getProperty("crawlerTask.stream");
    }
}

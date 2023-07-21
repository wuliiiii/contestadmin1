package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Schedules;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;

public interface ISchedule {
    void push(Task task);

    int size();

    Task poll();
}

package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Schedules;

import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NormalSchedule implements ISchedule{

    protected BlockingQueue<Task> queue = new ArrayBlockingQueue<>(10000);
    @Override
    public void push(Task task) {
        task.inQueue(queue);
        queue.add(task);
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public Task poll() {
        try {
            return queue.poll(100, TimeUnit.MILLISECONDS);
        }catch (InterruptedException exception) {
            return null;
        }
    }
}

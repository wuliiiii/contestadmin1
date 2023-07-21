package org.algorithmcontestdatacollect.crawlerendpoint2.Spiders;

import org.algorithmcontestdatacollect.crawlerendpoint2.Services.IErrorHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Downloaders.IDownloader;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Pipelines.IPipeline;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Processors.IProcessor;
import org.algorithmcontestdatacollect.crawlerendpoint2.Spiders.Schedules.ISchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Spider implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Spider.class);
    protected ISchedule schedule;
    private String SpiderName;
    private Thread spiderMainThread;
    private IDownloader downloader;
    private IProcessor processor;
    private Executor executor;
    private final Set<Task> handlingTasks = new CopyOnWriteArraySet<>();
    private final List<IPipeline> successPipelines = new ArrayList<>();
    private final List<IPipeline> failPipelines = new ArrayList<>();
    private final AtomicInteger workcnt = new AtomicInteger(0);

    private final List<Thread> threadList = new ArrayList<>();
    private IErrorHandler errorHandler;


    private Integer workThreadNum = 1;
    private AtomicBoolean running = new AtomicBoolean(false);

    public Spider(String spiderName) {
        SpiderName = spiderName;
    }

    public void handleTask(Task task) {
        try{
            task.running();
            downloader.download(task);
            if (task.getStatus() == Task.DOWNLOAD_ERROR) {
                for (var pipeline : failPipelines) {
                    pipeline.process(task);
                }
                return;
            }
            processor.process(task);
            if (task.getStatus() == Task.ANALYSE_ERROR) {
                for (var pipeline : failPipelines) {
                    pipeline.process(task);
                }
            } else if (task.getStatus() == Task.ANALYSE_SUCCESS) {
                for (var pipeline : successPipelines) {
                    pipeline.process(task);
                }
            }
        }catch (Exception e) {
            Fault fault = new Fault("SpiderMainProcess",e.getClass().getTypeName(),e.getMessage());
            errorHandler.handleTask(fault,task.getRequestParams());
        }
    }

    //    @Override
//    public void run() {
//        initComponent();
//        for(int i = 0;i<workThreadNum;i++) {
//            Thread t = new Thread(() -> {
//                while (running.get()) {
//                    Task task = schedule.poll();
//                    if (task == null) {
//                        try {
//                            Thread.sleep(100);
//                            continue;
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                    handleTask(task);
//                }
//            });
//            t.start();
//            threadList.add(t);
//        }
//        for (Thread thread : threadList) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    @Override
    public void run() {
        initComponent();
        while (running.get()) {
            Task task = schedule.poll();
            if (task == null) {
                continue;
            }
            while (handlingTasks.size() >= workThreadNum) {
                synchronized (handlingTasks) {
                    try {
                        handlingTasks.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            handlingTasks.add(task);
            executor.execute(() -> {
                logger.info(SpiderName + " run "+workcnt.addAndGet(1));
                handleTask(task);
                synchronized (handlingTasks) {
                    handlingTasks.remove(task);
                    handlingTasks.notify();
                }
            });
        }
    }

    public void initComponent() {
        if (downloader == null) {
            throw new RuntimeException("There is no downloader in Spider " + SpiderName);
        }
        if (processor == null) {
            throw new RuntimeException("There is no processor in Spider " + SpiderName);
        }
        if (schedule == null) {
            throw new RuntimeException("There is no schedule in Spider " + SpiderName);
        }
    }

    public Spider setSchedule(ISchedule schedule) {
        this.schedule = schedule;

        return this;
    }


    public Spider setDownloader(IDownloader downloader) {
        this.downloader = downloader;
        return this;
    }

    public Spider setProcessor(IProcessor processor) {
        this.processor = processor;
        return this;
    }

    public Spider addSuccessPipeline(IPipeline pipeline) {
        successPipelines.add(pipeline);
        return this;
    }

    public Spider addFailPipeline(IPipeline pipeline) {
        failPipelines.add(pipeline);
        return this;
    }

    public void start() {
        running.set(true);
        spiderMainThread = new Thread(this);
        spiderMainThread.start();
    }

    public void stop() {
        running.set(false);
        logger.info(this.getSpiderName() + "stop");
        try {
            spiderMainThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Spider setWorkThreadNum(Integer workThreadNum) {
        this.workThreadNum = workThreadNum;
        return this;
    }

    public void addTask(Task task) {
        task.setSpider(this);
        schedule.push(task);
    }

    public String getSpiderName() {
        return SpiderName;
    }

    public Spider setSpiderName(String spiderName) {
        SpiderName = spiderName;
        return this;
    }

    public Spider setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public Spider setErrorHandler(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    public SpiderStatus getStatus() {
        SpiderStatus spiderStatus = new SpiderStatus();
        spiderStatus.setSpiderName(getSpiderName());
        synchronized (handlingTasks) {
            spiderStatus.setRunningTaskNum(handlingTasks.size());
        }
        spiderStatus.setQueueTaskNum(schedule.size());
        return spiderStatus;
    }
}

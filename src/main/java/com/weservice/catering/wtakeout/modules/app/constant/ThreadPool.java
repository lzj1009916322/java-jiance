package com.weservice.catering.wtakeout.modules.app.constant;

import java.util.concurrent.*;

public class ThreadPool {
    public static ExecutorService locationThread = new ThreadPoolExecutor(1,
            2,
            30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000));

}

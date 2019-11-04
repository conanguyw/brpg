/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package site.guyw.grpg.util;

import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author gyw33188
 * @version $Id: PoolUtils.java, v0.1 2017/7/13 gyw33188 Exp $$
 */
public class PoolUtils {
    private static int corePoolSize;
    private static int maximumPoolSize;
    private static long keepAliveTime;
    private static TimeUnit unit;
    private static BlockingQueue<Runnable> workQueue;
    private static ThreadFactory threadFactory;
    private static int corePoolSize2;
    private static int maximumPoolSize2;
    private PoolUtils() {
    }
    private static class loadPool3 {
        private static final ExecutorService pool = FutureExecutor.newWithInheritedMdc(3,35,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
    public static ExecutorService getThreadPool() {
        return loadPool3.pool;
    }
    public static synchronized ExecutorService getThreadPool(int corePoolSize1, int maximumPoolSize1, long keepAliveTime1, TimeUnit unit1, BlockingQueue<Runnable> workQueue1,ThreadFactory threadFactory1) {
        corePoolSize = corePoolSize1;
        maximumPoolSize = maximumPoolSize1;
        keepAliveTime = keepAliveTime1;
        unit = unit1;
        workQueue = workQueue1;
        threadFactory = threadFactory1;
        return loadPool.pool1;
    }
    public static synchronized ExecutorService getThreadPool(int corePoolSize1, int maximumPoolSize1) {
        corePoolSize2 = corePoolSize1;
        maximumPoolSize2 = maximumPoolSize1;
        return loadPool2.pool1;
    }
    private static class loadPool {
        private static final ExecutorService pool1 =FutureExecutor.newWithInheritedMdc(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,threadFactory);;
    }
    private static class loadPool2 {
        private static final ExecutorService pool1 =FutureExecutor.newWithInheritedMdc(corePoolSize2,maximumPoolSize2,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),Executors.defaultThreadFactory());;
    }

    public static synchronized ExecutorService getThreadPool4(int corePoolSize1, int maximumPoolSize1, long keepAliveTime1, TimeUnit unit1, BlockingQueue<Runnable> workQueue1,ThreadFactory threadFactory1) {
        corePoolSize = corePoolSize1;
        maximumPoolSize = maximumPoolSize1;
        keepAliveTime = keepAliveTime1;
        unit = unit1;
        workQueue = workQueue1;
        threadFactory = threadFactory1;
        return loadPool4.pool1;
    }
    private static class loadPool4 {
        private static final ExecutorService pool1 =FutureExecutor.newWithInheritedMdc(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,threadFactory);;
    }
    /**
     * 默认获取线程方法, 根据线程名称创建线程(注意:要根据实际每分钟的任务数来决定线程池大小)
     * corePoolSize    线程池核心线程数
     * maximumPoolSize 线程池最大线程数
     * keepAliveTime   线程的最大空闲时间
     * unit            空闲时间的单位
     * workQueue       任务队列
     * poolName        线程池名称
     * @param poolName
     * @return
     */
    public static ExecutorService newDefaultThreadPool(String poolName) {
        return FutureExecutor.newWithInheritedMdc(
                8, 32, 60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(32),
                new ThreadFactoryBuilder().setNameFormat(poolName + "-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }

    public static ExecutorService newThreadPoolWithSize(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, int queueSize) {
        return FutureExecutor.newWithInheritedMdc(
                corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),
                new ThreadFactoryBuilder().setNameFormat(poolName + "-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }
    public static synchronized ExecutorService getThreadPool5(int corePoolSize1, int maximumPoolSize1, long keepAliveTime1, TimeUnit unit1, BlockingQueue<Runnable> workQueue1,ThreadFactory threadFactory1) {
        corePoolSize = corePoolSize1;
        maximumPoolSize = maximumPoolSize1;
        keepAliveTime = keepAliveTime1;
        unit = unit1;
        workQueue = workQueue1;
        threadFactory = threadFactory1;
        return loadPool5.pool1;
    }
    private static class loadPool5 {
        private static final ExecutorService pool1 =FutureExecutor.newWithInheritedMdc(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,threadFactory);;
    }
    public static synchronized ExecutorService getThreadPool6(int corePoolSize1, int maximumPoolSize1, long keepAliveTime1, TimeUnit unit1, BlockingQueue<Runnable> workQueue1,ThreadFactory threadFactory1) {
        corePoolSize = corePoolSize1;
        maximumPoolSize = maximumPoolSize1;
        keepAliveTime = keepAliveTime1;
        unit = unit1;
        workQueue = workQueue1;
        threadFactory = threadFactory1;
        return loadPool6.pool1;
    }
    private static class loadPool6 {
        private static final ExecutorService pool1 =FutureExecutor.newWithInheritedMdc(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,threadFactory);;
    }
    public static synchronized ExecutorService getThreadPool7(int corePoolSize1, int maximumPoolSize1, long keepAliveTime1, TimeUnit unit1, BlockingQueue<Runnable> workQueue1,ThreadFactory threadFactory1) {
        corePoolSize = corePoolSize1;
        maximumPoolSize = maximumPoolSize1;
        keepAliveTime = keepAliveTime1;
        unit = unit1;
        workQueue = workQueue1;
        threadFactory = threadFactory1;
        return loadPool7.pool1;
    }
    private static class loadPool7 {
        private static final ExecutorService pool1 =FutureExecutor.newWithInheritedMdc(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,threadFactory);;
    }
    public static synchronized ExecutorService getThreadPool8(int corePoolSize1, int maximumPoolSize1, long keepAliveTime1, TimeUnit unit1, BlockingQueue<Runnable> workQueue1,ThreadFactory threadFactory1) {
        corePoolSize = corePoolSize1;
        maximumPoolSize = maximumPoolSize1;
        keepAliveTime = keepAliveTime1;
        unit = unit1;
        workQueue = workQueue1;
        threadFactory = threadFactory1;
        return loadPool8.pool1;
    }
    private static class loadPool8 {
        private static final ExecutorService pool1 =FutureExecutor.newWithInheritedMdc(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,threadFactory);;
    }
    public static synchronized ExecutorService getThreadPool9(int corePoolSize1, int maximumPoolSize1, long keepAliveTime1, TimeUnit unit1, BlockingQueue<Runnable> workQueue1,ThreadFactory threadFactory1) {
        corePoolSize = corePoolSize1;
        maximumPoolSize = maximumPoolSize1;
        keepAliveTime = keepAliveTime1;
        unit = unit1;
        workQueue = workQueue1;
        threadFactory = threadFactory1;
        return loadPool9.pool1;
    }
    private static class loadPool9 {
        private static final ExecutorService pool1 =FutureExecutor.newWithInheritedMdc(corePoolSize,maximumPoolSize,keepAliveTime, unit,
                workQueue,threadFactory);;
    }
}
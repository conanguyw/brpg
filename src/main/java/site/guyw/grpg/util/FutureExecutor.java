package site.guyw.grpg.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;



/**
 * @author gyw33188
 * @createTime 2019-09-27 15:55
 * @description 封装线程池
 */
public class FutureExecutor extends ThreadPoolExecutor {
    /** logger */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    final private boolean             useFixedContext;
    final private Map<String, String> fixedContext;

    // 保存任务开始执行的时间，当任务结束时，用任务结束时间减去开始时间计算任务执行时间
    private ConcurrentHashMap<String, Date> startTimes;
    private ConcurrentHashMap<String, String> poolNames;


    /**
     * Pool where task threads take MDC from the submitting thread.
     */
    public static FutureExecutor newWithInheritedMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return new FutureExecutor(null, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
    public static FutureExecutor newWithInheritedMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory) {
        return new FutureExecutor(null, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public static FutureExecutor newWithInheritedMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                                                            ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        return new FutureExecutor(null, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    /**
     * Pool where task threads take fixed MDC from the thread that creates the pool.
     */
    @SuppressWarnings("unchecked")
    public static FutureExecutor newWithCurrentMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return new FutureExecutor(MDC.getCopyOfContextMap(), corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * Pool where task threads always have a specified, fixed MDC.
     */
    public static FutureExecutor newWithFixedMdc(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                                        BlockingQueue<Runnable> workQueue) {
        return new FutureExecutor(fixedContext, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    private FutureExecutor(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.startTimes = new ConcurrentHashMap<>();
        this.poolNames = new ConcurrentHashMap<>();
        this.fixedContext = fixedContext;
        useFixedContext = (fixedContext != null);
    }
    private FutureExecutor(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                           ThreadFactory threadFactory){
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,threadFactory);
        this.startTimes = new ConcurrentHashMap<>();
        this.poolNames = new ConcurrentHashMap<>();
        this.fixedContext = fixedContext;
        useFixedContext = (fixedContext != null);
    }
    private FutureExecutor(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable>
            workQueue, ThreadFactory threadFactory,
                           RejectedExecutionHandler handler){
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,threadFactory, handler);
        this.startTimes = new ConcurrentHashMap<>();
        this.poolNames = new ConcurrentHashMap<>();
        this.fixedContext = fixedContext;
        useFixedContext = (fixedContext != null);
    }
    @SuppressWarnings("unchecked")
    private Map<String, String> getContextForTask() {
        return useFixedContext ? fixedContext : MDC.getCopyOfContextMap();
    }

    /**
     * 任务执行之前，记录任务开始时间
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        String key = String.valueOf(r.hashCode());

        startTimes.put(key, new Date());
        poolNames.put(key, t.getName());
    }

    /**
     * 增加对线程的监控, 异常记载
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        String key = String.valueOf(r.hashCode());
        Date startDate = startTimes.remove(key);
        Date finishDate = new Date();
        long diff = finishDate.getTime() - startDate.getTime();
        String poolName = StringUtils.trimToEmpty(poolNames.remove(key));
        // 统计任务耗时、初始线程数、正在执行的任务数量、已完成任务数量、任务总数、队列里缓存的任务数量、池中存在的最大线程数、核心线程数、最大允许的线程数、线程空闲时间、线程池是否关闭、线程池是否终止
        String message = String.format(poolName
                        + "-monitor: Duration: %d ms, PoolSize: %d, Active: %d, Task Completed: %d, Task Count: %d, " +
                        "Queue(Pending tasks): %d, LargestPoolSize: %d, CorePoolSize: %d, MaximumPoolSize: %d,  " +
                        "KeepAliveTime: %d, isShutdown: %s, isTerminated: %s",
                diff, this.getPoolSize(), this.getActiveCount(), this.getCompletedTaskCount(), this.getTaskCount(),
                this.getQueue().size(), this.getLargestPoolSize(), this.getCorePoolSize(), this.getMaximumPoolSize(), this.getKeepAliveTime(TimeUnit.MILLISECONDS),
                Boolean.toString(this.isShutdown()), Boolean.toString(this.isTerminated()));
        LogUtils.info("pool status:"+ message);
        if (t != null) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                t.printStackTrace(new PrintStream(baos));
                LogUtils.error("pool error:"+ "线程异常" + poolName + ":" + baos.toString());
            } catch (Exception e) {
                LogUtils.error("pool error:线程异常" + poolName+e.getCause() );
            }
        }
    }

    /**
     * All executions will have MDC injected. {@code ThreadPoolExecutor}'s submission methods ({@code submit()} etc.)
     * all delegate to this.
     */
    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, getContextForTask()));
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return new Runnable() {
            @Override
            public void run() {
                Map previous = MDC.getCopyOfContextMap();
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                try {
                    runnable.run();
                } finally {
                    if (previous == null) {
                        MDC.clear();
                    } else {
                        MDC.setContextMap(previous);
                    }
                }
            }
        };
    }

}

package com.f.thread.schedule;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f.dpay.common.Constants;
import com.f.dpay.config.ServerConfig;
import com.f.utils.StringUtils;

import lombok.extern.log4j.Log4j2;
/**
 * 线程池管理
 * @author max
 *
 */
@Service
@Log4j2
public class ThreadPoolManager {
	
	@Autowired
	private ServerConfig serverConfig;

    /**
     * CPU core size
     */
    public int corePoolSize;
    
    private static final class RunnableWrapper implements Runnable {
        private final Runnable _r;

        public RunnableWrapper(final Runnable r) {
            _r = r;
        }

        @Override
        public final void run() {
            try {
                _r.run();
            } catch (final Throwable e) {
                final Thread t = Thread.currentThread();
                final UncaughtExceptionHandler h = t.getUncaughtExceptionHandler();
                if (h != null) {
                    h.uncaughtException(t, e);
                }
            }
        }
    }

    protected ScheduledThreadPoolExecutor _generalScheduledThreadPool;
    private ThreadPoolExecutor _generalThreadPool;

    /** temp workaround for VM issue */
    private static final long MAX_DELAY = Long.MAX_VALUE / 1000000 / 2;

    private boolean _shutdown;

    
    @PostConstruct
    public void init() {
    	this.corePoolSize = serverConfig.corePoolSize;
        _generalScheduledThreadPool = new ScheduledThreadPoolExecutor(this.corePoolSize, new PriorityThreadFactory("GENERAL_SCHEDULED_THREAD_POOL", Thread.NORM_PRIORITY));
        _generalThreadPool = new ThreadPoolExecutor(this.corePoolSize, this.corePoolSize + 2, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new PriorityThreadFactory("GENERAL_THREAD_POOL", Thread.NORM_PRIORITY));
        // Initial 10 minutes, delay 5 minutes.
        scheduleGeneralAtFixedRate(new PurgeTask(), 600000L, 300000L);
        System.err.println("-----------------------ThreadPoolManager");
	}

    public long validateDelay(long delay) {
        if (delay < 0) {
            delay = 0;
        } else if (delay > MAX_DELAY) {
            delay = MAX_DELAY;
        }
        return delay;
    }

    public ScheduledFuture<?> scheduleGeneral(Runnable r, long delay) {
        try {
            delay = validateDelay(delay);
            return _generalScheduledThreadPool.schedule(new RunnableWrapper(r), delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            return null; /* shutdown, ignore */
        }
    }

    public ScheduledFuture<?> scheduleGeneralAtFixedRate(Runnable r, long initial, long delay) {
        try {
            delay = validateDelay(delay);
            initial = validateDelay(initial);
            return _generalScheduledThreadPool.scheduleAtFixedRate(new RunnableWrapper(r), initial, delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            return null; /* shutdown, ignore */
        }
    }
    
    public ScheduledFuture<?> scheduleGeneralWithFixedDelay(Runnable r, long initial, long delay) {
        try {
            delay = validateDelay(delay);
            initial = validateDelay(initial);
            return _generalScheduledThreadPool.scheduleWithFixedDelay(new RunnableWrapper(r), initial, delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            return null; /* shutdown, ignore */
        }
    }


    @Deprecated
    public boolean removeGeneral(RunnableScheduledFuture<?> r) {
        return _generalScheduledThreadPool.remove(r);
    }

    public void executeTask(Runnable r) {
        _generalThreadPool.execute(r);
    }

    public String[] getStats()
    {
        return new String[]
        {
            "STP:",
            " + General:",
            " |- ActiveThreads:   " + _generalScheduledThreadPool.getActiveCount(),
            " |- getCorePoolSize: " + _generalScheduledThreadPool.getCorePoolSize(),
            " |- PoolSize:        " + _generalScheduledThreadPool.getPoolSize(),
            " |- MaximumPoolSize: " + _generalScheduledThreadPool.getMaximumPoolSize(),
            " |- CompletedTasks:  " + _generalScheduledThreadPool.getCompletedTaskCount(),
            " |- ScheduledTasks:  " + (_generalScheduledThreadPool.getTaskCount() - _generalScheduledThreadPool.getCompletedTaskCount()),
            " | -------",
            "TP:",
            " + General Tasks:",
            " |- ActiveThreads:   " + _generalThreadPool.getActiveCount(),
            " |- getCorePoolSize: " + _generalThreadPool.getCorePoolSize(),
            " |- MaximumPoolSize: " + _generalThreadPool.getMaximumPoolSize(),
            " |- LargestPoolSize: " + _generalThreadPool.getLargestPoolSize(),
            " |- PoolSize:        " + _generalThreadPool.getPoolSize(),
            " |- CompletedTasks:  " + _generalThreadPool.getCompletedTaskCount(),
            " |- QueuedTasks:     " + _generalThreadPool.getQueue().size(),
            " | -------"
        };
    }

    private static class PriorityThreadFactory implements ThreadFactory {
        private final int _prio;
        private final String _name;
        private final AtomicInteger _threadNumber = new AtomicInteger(1);
        private final ThreadGroup _group;

        public PriorityThreadFactory(String name, int prio) {
            _prio = prio;
            _name = name;
            _group = new ThreadGroup(_name);
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(_group, r, _name + "-" + _threadNumber.getAndIncrement());
            t.setPriority(_prio);
            return t;
        }

        public ThreadGroup getGroup() {
            return _group;
        }
    }

    public void shutdown() {
        for(String line : getStats()) {
        	log.info(line);
        }
        log.info("ThreadPoolManager: Shutting down.");
        _shutdown = true;
        try {
            _generalScheduledThreadPool.awaitTermination(1, TimeUnit.SECONDS);
            _generalThreadPool.awaitTermination(1, TimeUnit.SECONDS);
            _generalScheduledThreadPool.shutdown();
            _generalThreadPool.shutdown();
            log.info("All ThreadPools are now stopped");

        } catch (InterruptedException e) {
            log.warn("shutdown error", e);
        }
    }

    public boolean isShutdown() {
        return _shutdown;
    }

    public void purge() {
        _generalScheduledThreadPool.purge();
        _generalThreadPool.purge();
    }

    public String getGeneralStats() {
        final StringBuilder sb = new StringBuilder(1000);
        ThreadFactory tf = _generalThreadPool.getThreadFactory();

        if (tf instanceof PriorityThreadFactory) {
            PriorityThreadFactory ptf = (PriorityThreadFactory) tf;
            int count = ptf.getGroup().activeCount();
            Thread[] threads = new Thread[count + 2];
            ptf.getGroup().enumerate(threads);
            StringUtils.append(sb, "General Thread Pool:" + Constants.EOL + "Tasks in the queue: ", String.valueOf(_generalThreadPool.getQueue().size()), Constants.EOL + "Showing threads stack trace:" + Constants.EOL + "There should be ", String.valueOf(count), " Threads" + Constants.EOL);

            for (Thread t : threads) {
                if (t == null) {
                    continue;
                }

                StringUtils.append(sb, t.getName(), Constants.EOL);

                for (StackTraceElement ste : t.getStackTrace()) {
                    StringUtils.append(sb, ste.toString(), Constants.EOL);
                }
            }
        }

        sb.append("Packet Tp stack traces printed." + Constants.EOL);

        return sb.toString();
    }

    protected class PurgeTask implements Runnable {
        @Override
        public void run() {
            _generalScheduledThreadPool.purge();
        }
    }

}
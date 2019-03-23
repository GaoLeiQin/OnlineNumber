import com.ledo.manager.FileManager;
import com.ledo.task.MonitorThreadPoolTask;
import com.ledo.task.SaveUrlContentTask;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.ledo.common.ThreadContant.*;

/**
 * 定时任务测试
 * @author qgl
 * @date 2018/11/13
 */
public class TaskTest extends BaseTest {

    @Test
    public void threadPoolStatus() {

        ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);
        System.out.println(" ，任务数量：" + s.getTaskCount());
        logger.info("是否关闭：" + s.isShutdown() + "，关闭后所有任务是否都已完成：" + s.isTerminated());
        MonitorThreadPoolTask urlContentTask = new MonitorThreadPoolTask(null, null, null);
        urlContentTask.setThreadName(MONITOR_THREAD_NAME);
        logger.info("启动之前：" + " 线程名称：" + urlContentTask.getName() + "，线程Id：" + urlContentTask.getId() + "，线程优先级：" + urlContentTask.getPriority() +
                " ，线程状态：" + urlContentTask.getState() + " ，线程组：" + urlContentTask.getThreadGroup() + "，isAlive：" + urlContentTask.isAlive() +
                "，isDaemon：" + urlContentTask.isDaemon() + "，isInterrupted：" + urlContentTask.isInterrupted());

        s.scheduleAtFixedRate(urlContentTask, 1, 10, TimeUnit.SECONDS);
        logger.info("线程队列：" + s.getQueue() + " ，活跃的线程数：" + s.getActiveCount() + " ，任务数量：" + s.getTaskCount() +
                "，已执行任务数量：" + s.getCompletedTaskCount() + "，是否允许核心线程超时：" + s.allowsCoreThreadTimeOut());

        List<Runnable> runnableList = s.shutdownNow();
        System.out.println(" 已关闭任务数量：" + s.getTaskCount());
        for (Runnable runnable : runnableList) {
            System.out.println(runnable.toString());
        }
        logger.info("是否关闭：" + s.isShutdown() + "，关闭后所有任务是否都已完成：" + s.isTerminated());

    }

    @Test
    public void threadName() {
        // 子类使用父类的setThreadName方法
        com.ledo.task.SaveUrlContentTask urlContentTask = new com.ledo.task.SaveUrlContentTask(null);
        urlContentTask.setThreadName(URLCONTENT_THREAD_NAME);
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(4);
        pool.scheduleAtFixedRate(urlContentTask, URLCONTENT_TASK_INITIALDELAY, SAVE_URLCONTENT_PERIOD, TimeUnit.MILLISECONDS);
        com.ledo.task.SaveServerInfoTask serverInfoTask = new com.ledo.task.SaveServerInfoTask(null, null);
        serverInfoTask.setThreadName(SERVERINFO_THREAD_NAME);
    }

    @Test
    public void suitTime() {
        long now = System.currentTimeMillis();

        for (long i = now; i < now + 600000; i++) {
            if (i % 600000 == 0) {
                System.out.println(i - now);
            }
        }
        logger.info(FileManager.getMilliSecondByFormatDate("2018-11-14 13:39:00"));
        logger.info(FileManager.getMilliSecondByFormatDate("2018-11-14 13:40:00"));
        logger.info(FileManager.getMilliSecondByFormatDate("2018-11-14 13:49:00"));
        logger.info(FileManager.getMilliSecondByFormatDate("2018-11-14 13:49:00") - FileManager.getMilliSecondByFormatDate("2018-11-14 13:39:00"));
    }

    public class SaveServerInfoTask implements Runnable {
        private int count;

        public SaveServerInfoTask(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            System.out.println("保存服务器信息！" + count);
        }
    }

    public class SaveUrlContentTask2 implements Runnable {
        private int count;

        public SaveUrlContentTask2(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            System.out.println("存储网页内容：" + count);
        }
    }
}

import com.ledo.manager.FileManager;
import org.junit.Test;

/**
 * 定时任务测试
 * @author qgl
 * @date 2018/11/13
 */
public class TaskTest extends BaseTest {


    @Test
    public void test() {
        long now = System.currentTimeMillis();
        logger.info(now);
        logger.info(FileManager.getMilliSecondByFormatDate("2018-11-14 13:39:00") % 60000);
        logger.info(FileManager.getMilliSecondByFormatDate("2018-11-14 13:40:00") % 60000);
        logger.info(FileManager.getMilliSecondByFormatDate("2018-11-14 13:41:00") % 60000);
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

    public class SaveUrlContentTask implements Runnable {
        private int count;

        public SaveUrlContentTask(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            System.out.println("存储网页内容：" + count);
        }
    }
}

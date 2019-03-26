import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static javax.management.timer.Timer.*;

/**
 * 充值日志文件处理
 * @author qgl
 * @date 2018/9/28
 */
public class DateUtilTest extends BaseTest{
    @Test
    public void test() {
        long time = ONE_HOUR * 242;
        System.out.println(getRemainTime(time));
        int index = getRemainTimeByTimer(time).indexOf("天");
        System.out.println(getRemainTimeByTimer(time).substring(0, index));
    }

    /**
     * 将毫秒数转换为 x天x小时x分钟x秒
     * 来源于博客
     * @param time
     * @return
     */
    public static String getRemainTime(long time) {
        String remainTime = null;
        long second = time / 1000;
        long minute = 0;
        long hour = 0;
        long day = 0;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
            if (minute > 60) {
                hour = minute / 60;
                minute = minute % 60;
                if (hour > 24) {
                    day = hour / 24;
                    hour = hour % 24;
                }
            }
        }

        if (day > 0) {
            remainTime = day + "天 " + hour + "小时 " + minute + "分钟 " + second + "秒";
        }else if (hour > 0) {
            remainTime = hour + "小时 " + minute + "分钟 " + second + "秒";
        }else if (minute > 0) {
            remainTime = minute + "分 " + second + "秒";
        }else{
            remainTime = second + "秒";
        }

        return remainTime;
    }

    /**
     * 将毫秒数转换为 x天x小时x分钟x秒
     * 自己敲
     * @param time
     * @return
     */
    public static String getRemainTimeByTimer(long time) {
        String remainTime = " ";
        long second = 0;
        long minute = 0;
        long hour = 0;
        long day = 0;

        if (time <= ONE_MINUTE) {
            second = time / ONE_SECOND;
            remainTime = second + "秒";
        }else if (time <= ONE_HOUR){
            minute = time / ONE_MINUTE;
            second = (time % ONE_MINUTE) / ONE_SECOND;
            remainTime = minute + "分 " + second + "秒";
        }else if (time <= ONE_DAY){
            hour = time / ONE_HOUR;
            minute = (time % ONE_HOUR) / ONE_MINUTE;
            second = (time % ONE_MINUTE) / ONE_SECOND;
            remainTime = hour + "小时 " + minute + "分钟 " + second + "秒";
        }else{
            day = time / ONE_DAY;
            hour = (time % ONE_DAY) / ONE_HOUR;
            minute = (time % ONE_HOUR) / ONE_MINUTE;
            second = (time % ONE_MINUTE) / ONE_SECOND;
            remainTime = day + "天 " + hour + "小时 " + minute + "分钟 " + second + "秒";
        }

        return remainTime;
    }
}

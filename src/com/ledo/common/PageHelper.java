package com.ledo.common;

/**
 * 分页插件的bean
 * @author qgl
 * @date 2018/10/11
 */
public class PageHelper {
    private int start = 0;
    private int count = 5;
    private int last = 0;

    public PageHelper() {
    }

    public PageHelper(int start, int count, int last) {
        this.start = start;
        this.count = count;
        this.last = last;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", last=" + last +
                '}';
    }

    /**
     * 计算最后一页的开始值
     * @param total
     */
    public void caculateLast(int total) {
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count) {
            last = total - count;
        }else {
            // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
            last = total - total % count;
        }
    }
}

package com.ledo.manager;

import com.ledo.beans.Page;

/** 对数据进行分页处理
 * @author qgl
 * @date 2018/11/17
 */
public class PageManager extends BaseManager{
    /** 分页后每页显示 25 条数据 */
    private static final int PAGE_SIZE = 25;
    public static PageManager instance = new PageManager();

    public static PageManager getInstance() {
        return instance;
    }

    /**
     * 处理页面
     * @param page
     * @param size
     */
    public Page setPageInfo(Page page, int size) {
        Integer currentPage = page.getCurrentPage();
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
            page.setCurrentPage(currentPage);
        }
        int startRow = currentPage == 1 ? 0 : (currentPage - 1) * PAGE_SIZE;

        // 总页数 = 总条数 / 页大小 + 1
        int totalPages = (size % PAGE_SIZE == 0) ? (size / PAGE_SIZE) : (size / PAGE_SIZE + 1);
        page.setStartRow(startRow);
        page.setPageSize(PAGE_SIZE);
        page.setTotalPage(totalPages);
        page.setTotalRows(size);
        return page;
    }
}

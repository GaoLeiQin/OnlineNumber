package com.ledo.beans;

/**
 * 分页bean
 * @author qgl
 * @date 2018/9/24
 */
public class Page {
    private Integer startRow;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalRows;

    public Page() {
    }

    public Page(Integer startRow, Integer currentPage, Integer pageSize, Integer totalPage, Integer totalRows) {
        this.startRow = startRow;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRows = totalRows;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    @Override
    public String toString() {
        return "Page{" +
                "startRow=" + startRow +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", totalRows=" + totalRows +
                '}';
    }
}

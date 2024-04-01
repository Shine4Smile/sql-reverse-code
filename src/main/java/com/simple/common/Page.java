package com.simple.common;

import java.io.Serializable;

/**
 * 分页参数对象
 *
 * @author Simple
 */
public class Page implements Serializable {
    private static final long serialVersionUID = 4594071922809227379L;
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    private Long pageNo;
    private Long pageSize;
    private Long totalPage;
    private Long totalRows;

    public Page() {
    }

    public Page(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Page(Long pageNo, Long pageSize, Long totalRows) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRows = totalRows;
        this.totalPage = totalRows % pageSize == 0 ? totalRows / pageSize : totalRows / pageSize + 1L;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = Math.max(pageNo, 1L);
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = Math.max(pageSize, 1L);
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Long totalRows) {
        this.totalRows = Math.max(totalRows, 0);
        Long realPageCount = totalRows % this.pageSize == 0 ? totalRows / this.pageSize : totalRows / this.pageSize + 1L;
        this.totalPage = realPageCount;
        if (this.pageNo > realPageCount) {
            if (this.pageNo - 1L > this.pageNo - realPageCount) {
                this.pageNo = realPageCount;
            } else {
                this.pageNo = 1L;
            }
        }
    }
}

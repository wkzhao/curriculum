package com.curriculum.domain;

public class PageBean
{
    private int currentPage;
    private int pageSize;
    private int recordCount;
    private int recordIndex;
    private int pageCount;
    private int beginPageIndex;
    private int endPageIndex;

    public PageBean(int currentPage, int pageSize, int recordCount)
    {
        this.currentPage = currentPage;

        this.pageSize = pageSize;

        this.recordCount = recordCount;

        this.pageCount = ((recordCount + pageSize - 1) / pageSize);

        this.recordIndex = ((currentPage - 1) * pageSize);

        if (this.pageCount <= 10)
        {
            this.beginPageIndex = 1;

            this.endPageIndex = this.pageCount;
        }
        else
        {
            this.beginPageIndex = (currentPage - 4);

            this.endPageIndex = (currentPage + 5);

            if (this.beginPageIndex < 1)
            {
                this.beginPageIndex = 1;

                this.endPageIndex = 10;
            }

            if (this.endPageIndex > this.pageCount)
            {
                this.endPageIndex = this.pageCount;

                this.beginPageIndex = (this.pageCount - 10 + 1);
            }
        }
    }

    public int getRecordCount()
    {
        return this.recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getRecordIndex() {
        return this.recordIndex;
    }

    public void setRecordIndex(int recordIndex) {
        this.recordIndex = recordIndex;
    }

    public int getBeginPageIndex() {
        return this.beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    public int getEndPageIndex() {
        return this.endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
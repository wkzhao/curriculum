package com.curriculum.domain;

public class UserPaper
{
    private int userId;
    private int paperId;
    private String paperName;

    public int getUserId()
    {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaperId() {
        return this.paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return this.paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }
}
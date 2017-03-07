package com.curriculum.domain;

public class Analysis
{
    private String knowledgePointName;
    private int rightTimes;
    private int wrongTimes;
    private int totalNums;

    public String getKnowledgePointName()
    {
        return this.knowledgePointName;
    }

    public void setKnowledgePointName(String knowledgePointName) {
        this.knowledgePointName = knowledgePointName;
    }

    public int getRightTimes() {
        return this.rightTimes;
    }

    public void setRightTimes(int rightTimes) {
        this.rightTimes = rightTimes;
    }

    public int getWrongTimes() {
        return this.wrongTimes;
    }

    public void setWrongTimes(int wrongTimes) {
        this.wrongTimes = wrongTimes;
    }

    public int getTotalNums() {
        return this.totalNums;
    }

    public void setTotalNums(int totalNums) {
        this.totalNums = totalNums;
    }
}
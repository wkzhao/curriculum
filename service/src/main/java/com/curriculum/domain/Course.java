package com.curriculum.domain;

import java.util.Date;

public class Course
{
    private int id;
    private String title;
    private String content;
    private String knowlwdgePointName;
    private int knowledgePointId;
    private String knowledgePointName;
    private String creator;
    private Date createTime;
    private String videoUrl;

    public int getId()
    {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getKnowledgePointId() {
        return this.knowledgePointId;
    }

    public void setKnowledgePointId(int knowledgePointId) {
        this.knowledgePointId = knowledgePointId;
    }

    public String getKnowledgePointName() {
        return this.knowledgePointName;
    }

    public void setKnowledgePointName(String knowledgePointName) {
        this.knowledgePointName = knowledgePointName;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getKnowlwdgePointName() {
        return knowlwdgePointName;
    }

    public void setKnowlwdgePointName(String knowlwdgePointName) {
        this.knowlwdgePointName = knowlwdgePointName;
    }
}
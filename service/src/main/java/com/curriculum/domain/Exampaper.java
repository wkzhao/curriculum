package com.curriculum.domain;

import java.util.List;
import java.util.Map;

public class Exampaper
{
    private int id;
    private int time;
    private int paperTypeId;
    private String name;
    private String content;
    private int status;
    private String creator;
    private Map<Integer, List<Question>> questionMap;
    private String questionIds;
    private Map<Integer,Integer> questionTypeNumMap;
    private List<Integer> knowledgePoints;

    public int getId()
    {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPaperTypeId() {
        return this.paperTypeId;
    }

    public void setPaperTypeId(int paperTypeId) {
        this.paperTypeId = paperTypeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Map<Integer, List<Question>> getQuestionMap() {
        return this.questionMap;
    }

    public void setQuestionMap(Map<Integer, List<Question>> questionMap) {
        this.questionMap = questionMap;
    }

    public String getQuestionIds() {
        return this.questionIds;
    }

    public void setQuestionIds(String questionIds) {
        this.questionIds = questionIds;
    }

    public Map<Integer, Integer> getQuestionTypeNumMap() {
        return questionTypeNumMap;
    }

    public void setQuestionTypeNumMap(Map<Integer, Integer> questionTypeNumMap) {
        this.questionTypeNumMap = questionTypeNumMap;
    }

    public List<Integer> getKnowledgePoints() {
        return knowledgePoints;
    }

    public void setKnowledgePoints(List<Integer> knowledgePoints) {
        this.knowledgePoints = knowledgePoints;
    }
}
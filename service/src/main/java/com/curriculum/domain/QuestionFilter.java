package com.curriculum.domain;

public class QuestionFilter
{
    private long knowledgeId;
    private String knowledgePointId;
    private long questionTypeId;

    public long getKnowledgeId()
    {
        return this.knowledgeId;
    }

    public void setKnowledgeId(long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getKnowledgePointId() {
        return this.knowledgePointId;
    }

    public void setKnowledgePointId(String knowledgePointId) {
        this.knowledgePointId = knowledgePointId;
    }

    public long getQuestionTypeId() {
        return this.questionTypeId;
    }

    public void setQuestionTypeId(long questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public QuestionFilter(long knowledgeId, String knowledgePointId, long questionTypeId)
    {
        this.knowledgeId = knowledgeId;
        this.knowledgePointId = knowledgePointId;
        this.questionTypeId = questionTypeId;
    }
}
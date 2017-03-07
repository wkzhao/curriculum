package com.curriculum.domain;

public class UserQuestion
{
    private int id;
    private int userId;
    private int questionId;
    private int wrongOrRight;
    private String userAnswer;
    private String questionPoints;

    public int getId()
    {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getWrongOrRight() {
        return this.wrongOrRight;
    }

    public void setWrongOrRight(int wrongOrRight) {
        this.wrongOrRight = wrongOrRight;
    }

    public String getUserAnswer() {
        return this.userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getQuestionPoints() {
        return this.questionPoints;
    }

    public void setQuestionPoints(String questionPoints) {
        this.questionPoints = questionPoints;
    }
}
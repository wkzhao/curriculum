package com.curriculum.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("question")
public class Question
{

    @XStreamAlias("id")
    private int id;

    @XStreamAlias("name")
    private String name;

    @XStreamAlias("analysis")
    private String analysis;

    @XStreamAlias("questionContent")
    private QuestionContent questionContent;

    @XStreamAlias("content")
    private String content;

    @XStreamAlias("creator")
    private String creator;

    @XStreamAlias("answer")
    private String answer;

    @XStreamAlias("questionTypeId")
    private int questionTypeId;

    @XStreamAlias("questionTypeName")
    private String questionTypeName;

    @XStreamAlias("points")
    private String points;
    private String pointsName;

    @XStreamAlias("uAnswer")
    private String uAnswer;
    @XStreamAlias("wrongOrRight")
    private int wrongOrRight;

    public String getAnswer()
    {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnalysis()
    {
        return this.analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public QuestionContent getQuestionContent() {
        return this.questionContent;
    }

    public void setQuestionContent(QuestionContent questionContent) {
        this.questionContent = questionContent;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionTypeId()
    {
        return this.questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionTypeName() {
        return this.questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public String getPoints() {
        return this.points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPointsName() {
        return this.pointsName;
    }

    public void setPointsName(String pointsName) {
        this.pointsName = pointsName;
    }

    public String getuAnswer() {
        return uAnswer;
    }

    public void setuAnswer(String uAnswer) {
        this.uAnswer = uAnswer;
    }

    public int getWrongOrRight() {
        return wrongOrRight;
    }

    public void setWrongOrRight(int wrongOrRight) {
        this.wrongOrRight = wrongOrRight;
    }
}
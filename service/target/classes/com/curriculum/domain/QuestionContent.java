package com.curriculum.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.LinkedHashMap;

@XStreamAlias("QuestionContent")
public class QuestionContent
{

    @XStreamAlias("title")
    private String title;

    @XStreamAlias("titleImg")
    private String titleImg;

    @XStreamAlias("choiceList")
    private LinkedHashMap<String, String> choiceList;

    @XStreamAlias("choiceImgList")
    private LinkedHashMap<String, String> choiceImgList;

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImg() {
        return this.titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public LinkedHashMap<String, String> getChoiceList() {
        return this.choiceList;
    }

    public void setChoiceList(LinkedHashMap<String, String> choiceList) {
        this.choiceList = choiceList;
    }

    public LinkedHashMap<String, String> getChoiceImgList() {
        return this.choiceImgList;
    }

    public void setChoiceImgList(LinkedHashMap<String, String> choiceImgList) {
        this.choiceImgList = choiceImgList;
    }
}
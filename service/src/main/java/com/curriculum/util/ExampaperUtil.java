package com.curriculum.util;

import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionContent;

import java.util.*;

public class ExampaperUtil
{
    public static String questionListToXml(List<Question> questionList)
    {
        StringBuilder result = new StringBuilder("");
        for (Question question : questionList) {
            if(question.getuAnswer() == null || "".equals(question.getuAnswer())) {
                question.setuAnswer("noAnswer");
            }
            result.append(new StringBuilder().append(Object2Xml.toXml(question)).append("#").toString());
        }
        return result.toString().substring(0, result.length() - 1);
    }

    public static List<Question> xmlToQuestionList(String content) {
        if ((content == null) || ("".equals(content))) {
            return null;
        }
        List questionList = new ArrayList();
        String[] questions = content.split("#");

        for (int i = 0; i < questions.length; i++) {
            Question temp = Object2Xml.toBean(questions[i], Question.class);
            temp.setQuestionContent(Object2Xml.toBean(temp.getContent(), QuestionContent.class));
            questionList.add(temp);
        }
        return questionList;
    }

    public static Map<Integer, List<Question>> XmlToQuestionMap(String str) {
        if ((str == null) || ("".equals(str))) {
            return Collections.emptyMap();
        }
        List questionList = null;
        String[] questions = str.split("#");

        Map map = new HashMap();
        for (int i = 0; i < questions.length; i++) {
            Question temp = Object2Xml.toBean(questions[i], Question.class);
            temp.setQuestionContent(Object2Xml.toBean(temp.getContent(), QuestionContent.class));
            if (map.get(Integer.valueOf(temp.getQuestionTypeId())) == null) {
                questionList = new ArrayList();
                questionList.add(temp);
                map.put(Integer.valueOf(temp.getQuestionTypeId()), questionList);
            } else {
                questionList = (List)map.get(Integer.valueOf(temp.getQuestionTypeId()));
                questionList.add(temp);
            }
        }
        return map;
    }
}
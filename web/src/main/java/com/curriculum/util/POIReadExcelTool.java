package com.curriculum.util;
import com.curriculum.domain.Question;
import com.curriculum.domain.QuestionContent;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIReadExcelTool {

    public static List<Question> readXls(InputStream is) throws Exception {
        HSSFWorkbook excel = new HSSFWorkbook(is);
        List<Question> questionList = new ArrayList<>();
        Question question = null;

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < excel.getNumberOfSheets(); numSheet++) {
            HSSFSheet sheet = excel.getSheetAt(numSheet);
            if (sheet == null)
                continue;
            // 循环行Row
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                QuestionContent questionContent = new QuestionContent();
                if (row == null)
                    continue;
               question = new Question();
                // 循环列Cell
                HSSFCell cell= row.getCell(0);
                if (cell == null)
                    continue;
                questionContent.setTitle(cell.getStringCellValue());
                if(cell.getStringCellValue().length()>10){
                    question.setName(cell.getStringCellValue().substring(0,10)+"...");
                }else{
                    question.setName(cell.getStringCellValue());
                }
                cell = row.getCell(1);
                if (cell == null)
                    continue;
                question.setQuestionTypeId((int)cell.getNumericCellValue());
                cell = row.getCell(2);
                if (cell == null)
                    continue;
                question.setAnswer(cell.getStringCellValue());
                cell = row.getCell(3);
                if (cell == null)
                    continue;
                question.setAnalysis(cell.getStringCellValue());
                cell = row.getCell(4);
                if (cell == null)
                    continue;
                question.setPoints(cell.getStringCellValue());
                if(row.getCell(5) != null) {
                    String choice = null;
                    LinkedHashMap<String, String> choiceList = new LinkedHashMap<>();
                    for (int i = 5; i < 11; i++) {
                        cell = row.getCell(i);
                        if( cell == null)
                            break;
                        char ch = (char) ('A' + (i - 5));
                        choice = ch + "";
                        choiceList.put(choice, cell.getStringCellValue());
                    }
                    questionContent.setChoiceList(choiceList);
                }
                question.setQuestionContent(questionContent);
                questionList.add(question);
            }
        }

        return questionList;
    }
}
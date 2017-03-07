package com.curriculum.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Roulette<T>
{
    private List<T> resultList;
    private HashMap<Integer, Float> chanceMap;

    public Roulette(List<T> resultList, HashMap<Integer, Float> chanceMap)
    {
        Iterator it = chanceMap.keySet().iterator();
        float sum = 0.0F;
        while (it.hasNext()) {
            sum = Math.round((sum + ((Float)chanceMap.get(it.next())).floatValue()) * 1000.0F) / 1000.0F;
        }

        if ((sum == 1.0F) && (resultList.size() == chanceMap.size())) {
            this.resultList = resultList;
            this.chanceMap = chanceMap;
        }
    }

    public T getResult() throws Exception {
        double result = Math.random();
        double area = ((Float)this.chanceMap.get(Integer.valueOf(0))).floatValue();

        if (this.resultList.size() == 0) {
            throw new Exception("");
        }

        for (int i = 0; i < this.resultList.size(); i++) {
            if (area > result) {
                return this.resultList.get(i);
            }
            area += ((Float)this.chanceMap.get(Integer.valueOf(i + 1))).floatValue();
        }
        return null;
    }
}
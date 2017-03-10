package com.curriculum.util;


import java.util.Map;
import java.util.Random;

public class Roulette {

    public static int[] roulette(Map<Integer,Integer> pointsPossibilityMap, int[] result){
        int sum = 0;
        Random random = new Random();
        int randomNum = random.nextInt(100);
        for (int i = 0; i < result.length; i++) {
            for(int point : pointsPossibilityMap.keySet()){
                sum += pointsPossibilityMap.get(point);
                if (randomNum <= sum) {
                    result[i] = point;
                    break;
                }
            }
        }
        return result;
    }
}

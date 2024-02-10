package com.chandradip.weatherforecastapi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class Solution {

    public static void printPyramid(int n) {
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=n-i; j++) {
                System.out.print("  ");
            }
            for (int j=1; j<=i; j++) {
                System.out.print("* ");
            }
            for (int j=1; j<=i-1; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int n =10;
        printPyramid(n);
        String name = "chandradip";
        getFrequencyOfChars(name).forEach((k,v)-> System.out.println(k + " : "+v));
        getFrequencyFoCharsUsingArr(name);
    }

    public static Map<Character, Integer> getFrequencyOfChars(String s) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (int i=0; i<s.length(); i++) {
            frequencyMap.put(s.charAt(i), frequencyMap.getOrDefault(s.charAt(i), 0)+1);
        }
        return frequencyMap;
    }
    public static void getFrequencyFoCharsUsingArr(String s) {
        FrequencyClass [] frequencyClasses = new FrequencyClass[s.length()];
        for (int i=0; i<s.length(); i++) {
            boolean flag = true;
            for (int j =0; j<i; j++) {
                if (frequencyClasses[j]!=null && frequencyClasses[j].getC() ==s.charAt(i)) {
                    frequencyClasses[j].setCount(frequencyClasses[j].getCount() + 1);
                    flag = false;
                }
            }
            if (flag) {
                FrequencyClass fre = new FrequencyClass(s.charAt(i), 1);
                frequencyClasses[i] = fre;
            }
        }
        for (FrequencyClass frequencyClass : frequencyClasses) {
            System.out.println(frequencyClass);
        }
    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
class FrequencyClass {
    private char c;
    private Integer count;
}

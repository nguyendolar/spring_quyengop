package com.java.spring_donations.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    public static List<Integer> convertStringArray(String str) {
        List<Integer> arrSeat = new ArrayList<>();
        for(int i = 0; i<str.length();i++) {
            try {
                int first = Integer.parseInt(String.valueOf(str.charAt(i)));
                System.out.println(first);
                int last = Integer.parseInt(String.valueOf(str.charAt(i+1)));
                System.out.println("sdsd"+ last);
                String f=String.valueOf(first);
                String l=String.valueOf(last);
                if(f.length()>0 && l.length()>0) {
                    String sonew = f+l;
                    arrSeat.add(Integer.parseInt(String.valueOf(sonew)));
                }else if(f.length()>0 || l.length()>0) {
                    arrSeat.add(Integer.parseInt(String.valueOf(first)));
                }

            } catch (Exception e) {

                // TODO: handle exception
            }
        }
        return arrSeat;
    }
}

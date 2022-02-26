package com.philippov.maksim;

import com.digdes.school.DatesToCronConvertException;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Solution solution = new Solution();
        ArrayList<String> dates = new ArrayList<>();
        dates.add("2022-01-29T08:10:00");
        dates.add("2022-02-29T09:12:30");
        dates.add("2022-02-29T09:13:30");

        try {
            System.out.println(solution.convert(dates));
        } catch (DatesToCronConvertException e) {
            e.printStackTrace();
        }
    }
}

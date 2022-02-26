package com.philippov.maksim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.digdes.school.DatesToCronConvertException;
import com.digdes.school.DatesToCronConverter;

public class Solution implements DatesToCronConverter {
    public String convert(List<String> dates) throws DatesToCronConvertException {
        Collections.sort(dates);

        ArrayList<Date> parsedDates = parseDates(dates);
        String[] ranges = findRanges(parsedDates);

        return format(ranges);
    }

    private ArrayList<Date> parseDates(List<String> dates) throws DatesToCronConvertException {
        ArrayList<Date> parsedDates = new ArrayList<>();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        for (String date : dates) {
            try {
                parsedDates.add(parser.parse(date));
            } catch (ParseException e) {
                throw new DatesToCronConvertException();
            }
        }
        return parsedDates;
    }

    private String[] findRanges(ArrayList<Date> dates) throws DatesToCronConvertException {
        int limit = dates.size() / 2;

        String[] ranges = new String[8];
        ranges[0] = "";
        ranges[7] = "";
        int[][] chains = new int[dates.size()][6];
        boolean[] disabledChains = new boolean[dates.size()];

        for (int i = 0; i < dates.size(); i++) {
            Date date = dates.get(i);
            int[] mass = new int[6];

            mass[0] = date.getSeconds();
            mass[1] = date.getMinutes();
            mass[2] = date.getHours();
            mass[3] = date.getDate();
            mass[4] = date.getMonth() + 1;
            mass[5] = date.getDay();

            chains[i] = mass;
        }

        for (int i = 0; i < 6; i++) {
            int[] choice = new int[0];
            switch (i) {
                case 0:
                    choice = new int[60];
                    break;
                case 1:
                    choice = new int[60];
                    break;
                case 2:
                    choice = new int[24];
                    break;
                case 3:
                    choice = new int[31];
                    break;
                case 4:
                    choice = new int[12];
                    break;
                case 5:
                    choice = new int[7];
                    break;
            }
            for (int j = 0; j < chains.length; j++) {
                if (disabledChains[j] == false) {
                    choice[chains[j][i]]++;
                }
            }
            int max = 0;
            for (int ind = 0; ind < choice.length; ind++) {
                if (choice[ind] > choice[max]) {
                    max = ind;
                }
            }
            for (int j = 0; j < disabledChains.length; j++) {
                if (chains[j][i] != max) {
                    disabledChains[j] = true;
                }
            }
            ranges[1 + i] = String.valueOf(max);
        }

        String[][] stringChains = new String[dates.size()][8];
        for (int i = 0; i < chains.length; i++) {
            stringChains[i][0] = "";
            for (int j = 1; j < 7; j++) {
                stringChains[i][j] = String.valueOf(chains[i][j - 1]);
            }
            stringChains[i][7] = "";
        }

        for (int i = 6; i >= 1; i--) {
            for (String[] chain : stringChains) {

                if (!chain[i].equals(ranges[i]) && chain[i - 1].equals(ranges[i - 1]) && chain[i + 1].equals(ranges[i + 1])) {
                    String merge = ranges[i] + "," + chain[i];
                    String target2 = chain[i];//target1 is ranges[i]
                    for (int j = 0; j < stringChains.length; j++) {
                        if (stringChains[j][i].equals(ranges[i]) || stringChains[j][i].equals(target2)) {
                            stringChains[j][i] = merge;
                        }
                    }
                    ranges[i] = merge;
                }
            }
        }

        //Count solutions
        int solutions = 0;
        for (int i = 0; i < stringChains.length; i++) {
            boolean equality = true;
            for (int j = 0; j < stringChains[i].length; j++) {
                if (!stringChains[i][j].equals(ranges[j])) {
                    equality = false;
                    break;
                }
            }
            if (equality) {
                solutions++;
            }
        }

        if (solutions < limit) {
            throw new DatesToCronConvertException();
        }
        return ranges;
    }


    private String format(String[] ranges) {

        return ranges[1] + " " + ranges[2] + " " + ranges[3] + " " + ranges[4] + " " + ranges[5] + " " + ranges[6];
    }

    public String getImplementationInfo() {
        return "Филиппов Максим Леонидович, Solution, com.philippov.maksim";
    }
}

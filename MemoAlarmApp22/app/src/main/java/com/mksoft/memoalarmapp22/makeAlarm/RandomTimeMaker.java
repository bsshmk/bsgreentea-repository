package com.mksoft.memoalarmapp22.makeAlarm;

import java.util.ArrayList;

public class RandomTimeMaker {

    int[] days = {0,31,28,31,30,31,30,31,31,30,31,30,31};

    private ArrayList<ArrayList<Integer>> Randomize(int limit_year, int limit_month, int limit_day,
                                                    int now_year, int now_month, int now_day,
                                                    int hour, int min, int min_interval,
                                                    int comfort_hourA, int comfort_hourB) {
        // 알람이 울리는 연도, 달, 날짜, 시간, 분
        ArrayList<ArrayList<Integer>> timeList = new ArrayList<ArrayList<Integer>>();

        int interval;
        while(now_year <= limit_year){

            ArrayList<Integer> tempList = new ArrayList<Integer>();
            interval = MakeInterval(min_interval);

            min += interval;
            if(min >= 60){
                hour += min/60; min %= 60;
            }
            if(hour >= 24){
                now_day += hour/24;
                hour %= 24;
            }
            // 간격의 최대값(interval*2)이 31일을 넘지 않게 해야 함
            // 윤년 처리
            if(now_year%4 == 0){
                if(now_month == 2){
                    if(now_day > 29){
                        now_day %= 29; now_month++;
                    }
                }
            }
            else {
                if (now_day > days[now_month]) {
                    now_day %= days[now_month];
                    now_month++;
                }
            }
            if(now_month > 12){
                now_month %= 12; now_year++;
            }

            if(now_year == limit_year){
                if(now_month > limit_month) break;
                else if(now_month == limit_month){
                    if(now_day > limit_day) break;
                }
            }

            // 알람 금지 시간
            if(CheckComfort(hour,comfort_hourA,comfort_hourB)) continue;

            tempList.add(now_year);
            tempList.add(now_month);
            tempList.add(now_day);
            tempList.add(hour);
            tempList.add(min);

            timeList.add(tempList);
        }
        return timeList;
    }

    private boolean CheckComfort(int hour, int comfortA, int comfortB){
        if(comfortA <= comfortB)
            return comfortA <= hour && hour < comfortB;
        else
            return comfortA <= hour || hour < comfortB;
    }

    private int MakeInterval(int min_interval){
        double rand = Math.random();
        int interval = (int)(rand*(min_interval+1)+min_interval);
        return interval;
    }
}
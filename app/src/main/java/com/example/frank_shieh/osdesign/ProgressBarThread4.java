package com.example.frank_shieh.osdesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FRANK_SHIEH on 2015/9/4.
 */
public class ProgressBarThread4 extends Thread {

    int runNum = 0;

    public int getRunNum() {
        return runNum;
    }

    public  void setRunNum(int runNum) {
        this.runNum = runNum;
    }

    ArrayList<PCB> arraylistTemp;
    MyAdpter myAdpter;
    ProgressActivity ProgressActivity;
    List<Map<String, Object>> list;
    //  JTable statusTable;
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    int size = 0;

    @SuppressWarnings("unchecked")
    public ProgressBarThread4( List<Map<String, Object>> list,MyAdpter myAdpter,ArrayList<PCB> arraylist,ProgressActivity ProgressActivity) {
        // System.out.println("in ProgressBarThread3");
        // map = new HashMap<Integer, Integer>();
        int row = 0;
        this.ProgressActivity=ProgressActivity;
        this.myAdpter=myAdpter;
        this.list=list;
        // System.out.println("map=" + arraylist.get(0).time);
        this.arraylistTemp = arraylist;
        for (PCB pcb : arraylistTemp) {
            map.put(pcb.name, row);
            row++;
            // System.out.println(pcb.time);
        }

        //   this.statusTable = statusTable;
        Collections.sort(arraylistTemp, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((PCB) o1).time - (((PCB) o2).time);
            }
        });
        // System.out.println("map=" + arraylistTemp.get(0).time);
		/*
		 * for (PCB pcb : arraylistTemp) { System.out.println(pcb.time); }
		 */
    }

    @Override
    public void run() {

        runNum = 0;
        int end = 0;
        int stopCount = 1;
        size = arraylistTemp.size();

        while (end == 0) {

            while (arraylistTemp.get(runNum).time != 0) {

                // System.out.println(runNum + "  " +
                // arraylistTemp.get(runNum).time);
                arraylistTemp.get(runNum).time--;
                // "进程标识符", "进程状态", "进程优先数", "总运行时间","剩余运行时间","进程状态条"
                System.out.println("runNum=" + runNum + "   "
                        + map.get(arraylistTemp.get(runNum).name) + "  map="
                        + map.size());
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("sign",  "进程标记符:"+String.valueOf(arraylistTemp.get(runNum).name));
                map2.put("totalTime", "运行总时间:" + String.valueOf(arraylistTemp.get(runNum).total));
                map2.put("remainingTime", "剩余的时间:" + String.valueOf(arraylistTemp.get(runNum).time));
                map2.put("status", "进程的状态:" + "运行态");
                map2.put("priorityNumber", "进程优先数:" + String.valueOf(arraylistTemp.get(runNum).pri));
                map2.put("numberProgressBar", (arraylistTemp.get(runNum).total - arraylistTemp
                        .get(runNum).time)
                        * 100
                        / arraylistTemp.get(runNum).total);
                list.set(map.get(arraylistTemp.get(runNum).name), map2);
                ProgressActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdpter.notifyDataSetChanged();
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                if (arraylistTemp.get(runNum).time == 0) {
                    map2.put("status", "进程的状态:" + "终止态");
                    runNum++;
                } else {
                    map2.put("status", "进程的状态:" + "就绪态");
                }
                if (runNum >= arraylistTemp.size()) {
                    System.out.println("runNum 000=" + runNum);
                    runNum = arraylistTemp.size()-1;
                    System.out.println("number0 ="
                            + arraylistTemp.get(runNum).time);
                }
                list.set(map.get(arraylistTemp.get(runNum).name), map2);
                ProgressActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdpter.notifyDataSetChanged();
                    }
                });
            }

            stopCount = 0;
            while (arraylistTemp.get(runNum).time == 0) {
                System.out.println(stopCount + "  runNum in while=" + runNum
                        + " time=" + arraylistTemp.get(runNum).time);
                end = 0;
                stopCount++;
                runNum++;
                if (runNum == arraylistTemp.size())
                    runNum = 0;
                if (stopCount > size) {
                    System.out.println("stopCount=" + stopCount);
                    end = 1;
                    //  MainFrame.start_time = false;
                    break;
                }
            }
            // System.out.println(stopCount + "  runNum out while=" + runNum);
        }
        ProgressActivity.canBack=true;
    }

    public boolean IsRunningOver() {
        System.out.println("in IsRunningOver()");
        if (arraylistTemp.get(runNum).time != 0) {
            return true;
        } else {
            return false;
        }

    }
}
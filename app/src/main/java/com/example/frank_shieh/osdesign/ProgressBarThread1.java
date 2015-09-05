package com.example.frank_shieh.osdesign;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FRANK_SHIEH on 2015/9/4.
 */
public class ProgressBarThread1 extends Thread {

    ArrayList<PCB> arraylist;
    MyAdpter myAdpter;
    ProgressActivity ProgressActivity;
    List<Map<String, Object>> list;
    //JTable statusTable;
    int size = 0, number = 0;

    public ProgressBarThread1(List<Map<String, Object>> list,MyAdpter myAdpter,ArrayList<PCB> arraylist,ProgressActivity ProgressActivity) {
        this.arraylist = arraylist;
        this.ProgressActivity=ProgressActivity;
        this.myAdpter=myAdpter;
        this.list=list;
    }

    @Override
    public void run() {

        number = 0;
        int end = 0;
        int stopCount = 1;
        size = arraylist.size();
        while (end == 0) {

            while (arraylist.get(number).time != 0) {
                arraylist.get(number).time--;
                // "进程标识符", "进程状态", "进程优先数", "总运行  时间","剩余运行时间","进程状态条"
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sign",  "进程标记符:"+String.valueOf(arraylist.get(number).name));
                map.put("totalTime","运行总时间:"+ String.valueOf(arraylist.get(number).total));
                map.put("priorityNumber", "进程优先数:"+ String.valueOf(arraylist.get(number).pri));
                map.put("remainingTime","剩余的时间:"+String.valueOf(arraylist.get(number).time) );
                map.put("status", "进程的状态:"+"运行态");
                map.put("numberProgressBar", (arraylist.get(number).total - arraylist
                        .get(number).time)
                        * 100
                        / arraylist.get(number).total);
                list.set(number, map);
                ProgressActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdpter.notifyDataSetChanged();
                    }
                });


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out
                            .println("InterruptedException in ProgressBarThread");
                }
                if (arraylist.get(number).time != 0) {
                    map.put("status", "进程的状态:"+"就绪态");
                    // statusTable.setValueAt("就绪态", number, 1);
                } else {
                    map.put("status", "进程的状态:"+"终止态");
                    //    statusTable.setValueAt("终止态", number, 1);
                }

                list.set(number, map);
                ProgressActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdpter.notifyDataSetChanged();
                    }
                });
                number++;
                if (number >= arraylist.size()) {
                    // System.out.println("number reset=" + number);
                    number = 0;
                }

            }

            stopCount = 0;
            while (arraylist.get(number).time == 0) {
                // System.out.println(stopCount + "  number in while=" + number
                // + " time=" + arraylist.get(number).time);
                end = 0;
                stopCount++;
                number++;
                if (number == arraylist.size())
                    number = 0;
                if (stopCount > size) {
                    System.out.println("stopCount=" + stopCount + "  size="
                            + size);
                    end = 1;
                    //	MainFrame.start_time = false;
                    break;
                }
            }
        }
          ProgressActivity.canBack=true;
    }
}
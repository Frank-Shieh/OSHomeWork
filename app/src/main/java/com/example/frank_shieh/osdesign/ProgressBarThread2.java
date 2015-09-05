package com.example.frank_shieh.osdesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by FRANK_SHIEH on 2015/9/4.
 */
public class ProgressBarThread2 extends Thread {

    ArrayList<PCB> arraylist;
    MyAdpter myAdpter;
    ProgressActivity ProgressActivity;
    List<Map<String, Object>> list;
    //  JTable statusTable;
    Queue<PCB>[] queue = new LinkedList[5];

    public ProgressBarThread2( List<Map<String, Object>> list,MyAdpter myAdpter,ArrayList<PCB> arraylist,ProgressActivity ProgressActivity) {
        this.arraylist = arraylist;
        this.ProgressActivity=ProgressActivity;
        this.myAdpter=myAdpter;
        this.list=list;
        //   this.statusTable = statusTable;
    }

    @Override
    public void run() {
        int size = 0;
        queue[0] = new LinkedList();
        queue[1] = new LinkedList();
        queue[2] = new LinkedList();
        queue[3] = new LinkedList();
        queue[4] = new LinkedList();
        while (size != arraylist.size()) {
            arraylist.get(size).psw = size;
            if (arraylist.get(size).pri == 4)
                queue[4].offer(arraylist.get(size));
            else if (arraylist.get(size).pri == 3)
                queue[3].offer(arraylist.get(size));
            else if (arraylist.get(size).pri == 2)
                queue[2].offer(arraylist.get(size));
            else if (arraylist.get(size).pri == 1)
                queue[1].offer(arraylist.get(size));
            else
                queue[0].offer(arraylist.get(size));

            size++;
        }
        size = 4;
        int number = size;

        while (number >= 0) {
            while (!queue[size].isEmpty()) {
                PCB pcb = queue[size].poll();
                if (pcb.time <= 0)
                    continue;
                // "进程标识符", "进程状态", "进程优先数", "总运行时间","剩余运行时间","进程状态条"
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sign",  "进程标记符:"+String.valueOf(arraylist.get(pcb.psw).name));
                map.put("totalTime","运行总时间:"+ String.valueOf(arraylist.get(pcb.psw).total));
                map.put("priorityNumber", "进程优先数:" + pcb.pri);
                map.put("remainingTime","剩余的时间:"+--pcb.time);
                map.put("status", "进程的状态:"+"运行态");
                //   statusTable.setValueAt(--pcb.time, pcb.psw, 4);
                //    statusTable.setValueAt("ÔËÐÐÌ¬", pcb.psw, 1);
                if (pcb.pri != 0)
                    map.put("priorityNumber", "进程优先数:" + --pcb.pri);
                //   statusTable.setValueAt(--pcb.pri, pcb.psw, 2);
                map.put("numberProgressBar", (arraylist.get(pcb.psw).total - arraylist
                        .get(pcb.psw).time)
                        * 100
                        / arraylist.get(pcb.psw).total);
                list.set(pcb.psw, map);
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
                map.put("status", "进程的状态:" + "就绪态");
                if (pcb.time == 0)
                    map.put("status", "进程的状态:"+"终止态");
                else if (pcb.pri == 0)
                    queue[0].offer(pcb);
                else
                    queue[size - 1].offer(pcb);

                list.set(pcb.psw, map);
                ProgressActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdpter.notifyDataSetChanged();
                    }
                });
            }
            number--;
            size--;
        }
        //   MainFrame.start_time = false;
        ProgressActivity.canBack=true;
    }

}
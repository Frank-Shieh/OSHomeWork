package com.example.frank_shieh.osdesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ProgressActivity extends AppCompatActivity {
    ListView listView;
    private Set<Integer> set = new HashSet<Integer>();
    public ArrayList<PCB> arraylist = new ArrayList<PCB>();
    private Runnable proc;
    int Pnumber;
    int typeNumber;
    private Thread p1 = new Thread(), p2 = null;
    MyAdpter myAdpter;
    List<Map<String, Object>> list;
    public  boolean canBack =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Pnumber = Integer.valueOf(bundle.getString("number"));
        typeNumber = bundle.getInt("typeNumber");
        createRamdonProcess(Pnumber);

        listView = (ListView) findViewById(R.id.list);
        list = getData();
        myAdpter = new MyAdpter(this, list);
        listView.setAdapter(myAdpter);
        init();

    }

    private void init() {
        if (typeNumber == 1) {
            proc =new ProgressBarThread1(list,myAdpter,arraylist,ProgressActivity.this);
            p1 = new Thread(proc);
            p1.start();
        }  else if (typeNumber == 2) {
            proc =new ProgressBarThread2(list,myAdpter,arraylist,ProgressActivity.this);
            p1 = new Thread(proc);
            p1.start();
        }else if (typeNumber == 3) {
            proc =new ProgressBarThread3(list,myAdpter,arraylist,ProgressActivity.this);
            p1 = new Thread(proc);
            p1.start();
        }else if (typeNumber == 4) {
            proc =new ProgressBarThread4(list,myAdpter,arraylist,ProgressActivity.this);
            p1 = new Thread(proc);
            p1.start();
        }

    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Pnumber; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sign", "进程标记符:"+arraylist.get(i).name);
            map.put("status", "进程的状态:"+arraylist.get(i).status);
            map.put("priorityNumber", "进程优先数:"+arraylist.get(i).pri);
            map.put("totalTime", "运行总时间:"+arraylist.get(i).total);
            map.put("remainingTime", "剩余的时间:"+arraylist.get(i).time);
            map.put("numberProgressBar", 0);
            list.add(map);
        }
        return list;
    }

    void createRamdonProcess(int number) {

        Object[][] object = new Object[number + 1][5];

        int numberCount = number;
        int[] array_processName = new int[numberCount + 1];
        int processName;
        int i = 1, count = 0;
        while (count < numberCount) {
            processName = 1 + (int) (Math.random() * 1000);
            if (!set.contains(processName)) {
                set.add(processName);
                array_processName[i++] = processName;
            }
            count++;
        }
        for (i = 1; i <= numberCount; i++) {
            int processPri = (int) (Math.random() * 5);
            int processTime = 1 + (int) (Math.random() * 7);
            PCB pcb = new PCB(array_processName[i], 1, processPri, processTime,
                    processTime);

            object[i] = new Object[]{pcb.name, "就绪态", pcb.pri, pcb.total,
                    pcb.time, 0};


            arraylist.add(pcb);
        }
    }

    @Override
    public void onBackPressed() {
        if(canBack)
            super.onBackPressed();

    }
}

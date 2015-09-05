package com.example.frank_shieh.osdesign;

import com.daimajia.numberprogressbar.NumberProgressBar;


/**
 * 
 * @author laudukang
 */
public class PCB {

	int name;
	int status;
	int pri;
	int total;
	int time;
	int next;
	int psw;
	NumberProgressBar bar;
	
	public PCB(int name, int status, int pri, int total, int time,NumberProgressBar bar) {
		this.name = name;
		this.status = status;
		this.pri = pri;
		this.total = total;
		this.time = time;
		this.bar=bar;
	}

	public PCB(int name, int status, int pri, int total, int time) {
		this.name = name;
		this.status = status;
		this.pri = pri;
		this.total = total;
		this.time = time;
	}

}

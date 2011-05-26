package org.busto.model;

import java.util.ArrayList;

public class Line {

	private ArrayList<Arrival> nextArrivals;
	private String number;
	
	public Line(String n){
		setNumber(n);
		nextArrivals = new ArrayList<Arrival>();
	}

	public void AddArrival(Arrival nxt){
		nextArrivals.add(nxt);
	}

	public ArrayList<Arrival> getArrivals(){
		return this.nextArrivals;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getNumber(){
		return number;
	}
}

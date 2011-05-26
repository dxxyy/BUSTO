package org.busto.model;

import java.util.ArrayList;


public class BusStop {
	
	private String stopInfo;
	private ArrayList<Line> lines;
	
	
	public BusStop(String n){
		stopInfo = n;
	}
	public String getStopInfo() {
		return stopInfo;
	}

	public void setStopN(String nf) {
		stopInfo = nf;
	}
}

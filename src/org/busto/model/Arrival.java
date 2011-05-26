package org.busto.model;

import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalTime;

public class Arrival {
	
	private LocalTime timeOfArrival;
	private Boolean isRealTime;
	private Arrival() {};
	
	public static Arrival GetNewArrival(int hour,int minute,boolean isRT){
		try{
			Arrival a = new Arrival();
			a.timeOfArrival = new LocalTime(hour,minute);
			a.isRealTime = isRT;
			return a;
		}catch(IllegalFieldValueException e){
			return null;
		}
	}
	public String PrintTime(){
		return timeOfArrival.hourOfDay().getAsString() + ":" + timeOfArrival.minuteOfHour().getAsString();
		
	}
	
	public String isRealTime(){
		return isRealTime == true ? "*" : "";
	}
}

package org.busto.util;

import java.util.ArrayList;


import org.busto.model.Arrival;
import org.busto.model.BusStop;
import org.busto.model.Line;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class BusStopCleaner {

	private static final String HTML_LINE_ELEM = "li";
	private static final String HTML_LINE_NUMB_ELEM = "h3";

	//LINE element format is "Linea&nbsp;sss"
	private static final String HTML_LINE_NAME = "Linea";
	private static final char HTML_LINE_SEP = ';';

	//TIME element format is "hh:mm&nbsp;*" or "hh:mm"
	private static final String HTML_TIME_ELEM = "span";
	private static final char HTML_TIME_SEP = '&';
	private static final char HTML_TIME_RT = '*';
	private static final char HTML_TIMEHM_SEP = ':';

	private String dataToClean;
	private TagNode root;
	private HtmlCleaner cleaner;
	private ArrayList<Line> lines;
	
	public void SetData(String data){
		dataToClean=data;
		cleaner = new HtmlCleaner();
		lines = new ArrayList<Line>();
	}

	public BusStop ParseData(){
		root = cleaner.clean(dataToClean);
		return null;
	}

	public void PrintLines(){
		for(int i=0;i<lines.size();i++){
			System.out.println("\r\n");
			System.out.println("Line number:"+lines.get(i).getNumber());
			ArrayList<Arrival> ar = lines.get(i).getArrivals();
			for(int j=0;j<ar.size();j++)
				System.out.print(ar.get(j).PrintTime() + ar.get(j).isRealTime() + " - ");
		}
	}

	public void Parse()
	{
		root = cleaner.clean(dataToClean);
		Line aLine;

		TagNode lineElements[] = root.getElementsByName(HTML_LINE_ELEM, true);
		for (int i = 0; lineElements != null && i < lineElements.length; i++)
		{
			//System.out.println(lineElements[i].getName() + " " + lineElements[i].getChildTags().length);

			TagNode lineNumberChild = lineElements[i].getElementsByName(HTML_LINE_NUMB_ELEM, false)[0];
			TagNode timeChilds[] = lineElements[i].getElementsByName(HTML_TIME_ELEM, true);
			aLine = ParseLine(lineNumberChild);
			if(aLine!=null) {
				ParseTimes(aLine,timeChilds);
				lines.add(aLine);
			}
		}
	}

	//TIME element format is "hh:mm&nbsp;*" or "span - hh:mm"
	private void ParseTimes(Line t,TagNode[] timeChilds) {
		String tmp;
		Arrival arr;
		for(int i=0;i<timeChilds.length;i++){
			tmp = timeChilds[i].getText().toString().trim();	
			if(tmp.length()<=1 || (arr = GetArrivalFromTime(tmp))==null ) continue;
			t.AddArrival(arr);
		}
	}


	private Arrival GetArrivalFromTime(String tmp){

		boolean isRT = false;
		int i1;
		if(tmp.indexOf(HTML_TIME_RT)!=-1 && (i1=tmp.indexOf(HTML_TIME_SEP))!=-1){ 
			isRT = true;
			tmp = tmp.substring(0,i1);
		}
		if((i1=tmp.indexOf(HTML_TIMEHM_SEP))!=-1){
			try{
				int hours = Integer.parseInt(tmp.substring(0, i1));
				int mins = Integer.parseInt(tmp.substring(i1+1,tmp.length()));
				return Arrival.GetNewArrival(hours, mins, isRT);
			}catch(NumberFormatException e){
				return null;
			}
		}
		return null;
	}
	
	//LINE element format is "Linea&nbsp;sss"
	private Line ParseLine(TagNode lineNumberChild) {

		String tmp = lineNumberChild.getText().toString().trim();
		int i1=0;		
		if(tmp.indexOf(HTML_LINE_NAME)!=-1 && (i1 = tmp.indexOf(HTML_LINE_SEP))!=-1 ){
			return new Line(tmp.substring(i1+1,tmp.length()));	
		}
		return null;
	}
}

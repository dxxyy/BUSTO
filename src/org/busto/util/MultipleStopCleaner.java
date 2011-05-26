package org.busto.util;

import org.busto.model.BusStop;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class MultipleStopCleaner {

	private static final String CLASS  = "def";

	private static String dataToClean;
	private static TagNode root;
	private static HtmlCleaner cleaner = new HtmlCleaner();
	
	public static void SetData(String data){
		dataToClean=data;
	}
	
	public static BusStop ParseData(){
		root = cleaner.clean(dataToClean);
		return null;
	}
}

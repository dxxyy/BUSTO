package org.busto.gttloader;

import org.busto.model.BusStop;
import org.busto.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class GttLoaderStopSearch {


	private static final String GETURLSEARCHSTOP = "http://www.5t.torino.it/pda/it/arrivi.jsp";
	private static final String SEARCHSTOPBODYPARAM = "?n=";

	private static final String HEADER_REFERER = "http://www.comune.torino.it/gtt/index.shtml";
	private static final String HEADER_ORIGIN = "http://www.comune.torino.it";
	private static final String HEADER_CONTENT = "application/x-www-form-urlencoded";
	private static final String HEADER_USERAGENT = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.68 Safari/534.24";

	private HttpClient httpClient;
	private HttpGet httpG;
	private HttpResponse httpR;

	private String data;
	
	public void InitGttDataLoader() {
		httpClient = new DefaultHttpClient();
	}

	public void DoSearchStopRequest(BusStop bs) throws ClientProtocolException, IOException {

		
		httpG = new HttpGet(SetRequest(bs.getStopInfo()));
		InitGetHeaders();
		httpR = httpClient.execute(httpG);
		HttpEntity entity = httpR.getEntity();
		
		if (entity != null) { 
			InputStream instream = entity.getContent();
			data = Utils.convertStreamToString(instream);
			System.out.println(data);
			instream.close();
		}
	}

	private void InitGetHeaders() {
		httpG.setHeader("Referer", HEADER_REFERER);
		httpG.setHeader("Origin",HEADER_ORIGIN);
		httpG.setHeader("User-Agent",HEADER_USERAGENT);
		httpG.setHeader("Content-Type",HEADER_CONTENT);
	}

	private String SetRequest(String stopI){
		return GETURLSEARCHSTOP+SEARCHSTOPBODYPARAM+stopI;	
	}

	public void ShutDownConnection(){

		httpClient.getConnectionManager().shutdown();
	}
	
	public String GetDataString(){	
		return data;
	}

	public void AbortConnection() {
		httpG.abort();
		
	}
}

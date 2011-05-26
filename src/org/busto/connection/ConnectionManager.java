package org.busto.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicHttpRequest;
import org.busto.util.Utils;

public class ConnectionManager {

	public static void GetConnection() throws IOException, HttpException, InterruptedException{
		
		Scheme http = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
		SchemeRegistry sr = new SchemeRegistry();
		sr.register(http);
		ClientConnectionManager connMrg = new SingleClientConnManager(null, sr);
		
		ClientConnectionRequest connRequest = connMrg.requestConnection(
		        new HttpRoute(new HttpHost("http://www.5t.torino.it", 80)), null);
		ManagedClientConnection conn = connRequest.getConnection(10, TimeUnit.SECONDS);
		//conn.open(arg0, arg1, arg2)
		try {
		    BasicHttpRequest request = new BasicHttpRequest("GET", "/pda/it/arrivi.jsp?n=788");
		    conn.sendRequestHeader(request);
		    HttpResponse response = conn.receiveResponseHeader();
		    conn.receiveResponseEntity(response);
		    HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        BasicManagedEntity managedEntity = new BasicManagedEntity(entity, conn, true);
		        // Replace entity
		        response.setEntity(managedEntity);
		       
				InputStream instream = entity.getContent();
				String data = Utils.convertStreamToString(instream);
				System.out.println(data);
				instream.close();
				
		    }
		 
		    // Do something useful with the response
		    // The connection will be released automatically 
		    // as soon as the response content has been consumed
		} catch (IOException ex) {
		    // Abort connection upon an I/O error.
		    conn.abortConnection();
		    throw ex;
		}
		
		
	}
	
}

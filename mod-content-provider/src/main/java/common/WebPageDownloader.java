package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebPageDownloader {

	public static String getContent(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
	    BufferedReader in = new BufferedReader(
	    new InputStreamReader(url.openStream()));
	
	    StringBuffer sb = new StringBuffer();
	    
	    String inputLine;
	    while ((inputLine = in.readLine()) != null)
	      sb.append(inputLine + "\n");
	    in.close();
	    
	    if (sb.length() > 0) {
	    	return sb.toString();
	    }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}
	
}

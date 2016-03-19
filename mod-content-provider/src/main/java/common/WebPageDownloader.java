package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPageDownloader {

  public static Document getContent(URL url) {
    try {
      BufferedReader in = new BufferedReader(
      new InputStreamReader(url.openStream()));
  
      StringBuffer sb = new StringBuffer();
      
      String inputLine;
      while ((inputLine = in.readLine()) != null)
        sb.append(inputLine + "\n");
      in.close();
      
      if (sb.length() > 0) {
        Document doc = Jsoup.parse(sb.toString());
        return doc;
      }
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
    } catch (IOException e) {
      // TODO Auto-generated catch block
    }
    return null;
  }
  
	public static Document getContent(String urlString) {
		try {
      return getContent(new URL(urlString));
    } catch (MalformedURLException e) {
    }
		return null;
	}
	
}

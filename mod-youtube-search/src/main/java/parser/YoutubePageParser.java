package parser;

import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.WebPageDownloader;
import data.YoutubeData;
import data.YoutubeVideoResultLink;

public class YoutubePageParser {
  
  public static final String URL_BASE = "https://youtube.com";
  
  public YoutubeData parse(URL url) {
    Document content = WebPageDownloader.getContent(url);
    
    return parse(content);
  }
  
  public YoutubeData parse(Document content) {
    
    if (content != null) {
      // TODO null content
    }
    
    YoutubeData data = new YoutubeData();
    
    parseSearchResults(data, content);
    
    return data;
  }

  private void parseSearchResults(YoutubeData data, Document content) {
    Elements items = content.getElementsByClass("yt-lockup-content");
    
    if (items != null) {
      for (Element item: items) {
        
        Elements aElements = item.getElementsByTag("a");
        if (aElements != null) {
          Element aElement = aElements.first();
            
          try {
            String href = aElement.attr("href");
            String title = aElement.attr("title");
            if (!StringUtil.isBlank(href)) {
              URL link = new URL(URL_BASE + href);
              
              YoutubeVideoResultLink resultLink = new YoutubeVideoResultLink(link);
              resultLink.setTitle(title);
              
              data.getResultLinkList().add(resultLink);
            }
            
          } catch (MalformedURLException e) {
            continue;
          }
            
        }
        
      }
    }
    
  }
  
}

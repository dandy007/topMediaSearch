package data;

import java.net.URL;
import java.sql.Timestamp;

public class YoutubeVideoResultLink {

  private URL url;
  private String title;
  private Timestamp oldTimestamp;
  private int viewCount;
  private String author;
  private int type;
  
  public YoutubeVideoResultLink(URL url) {
    this.url = url;
  }  

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public URL getUrl() {
    return url;
  }
  public void setUrl(URL url) {
    this.url = url;
  }
  public Timestamp getOldTimestamp() {
    return oldTimestamp;
  }
  public void setOldTimestamp(Timestamp oldTimestamp) {
    this.oldTimestamp = oldTimestamp;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
  public String getAuthor() {
    return author;
  }
  public void setAuthor(String author) {
    this.author = author;
  }
  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }
  
}

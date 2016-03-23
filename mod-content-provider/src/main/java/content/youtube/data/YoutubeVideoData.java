package content.youtube.data;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.api.client.util.DateTime;

public class YoutubeVideoData {

  private String videoId;
  private URL url;
  private String title;
  private DateTime oldTimestamp = null;
  private BigInteger viewCount = null;
  private double viewCountPerDay = 0;
  private String author;
  private int type;
  
  public YoutubeVideoData(String videoId) {
    this.videoId = videoId;
    
    // TODO somethin clever
    try {
      url = new URL("https://youtube.com/watch?v=" + videoId);
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
    }
  }  

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  
  public double getViewCountPerDay() {
    return viewCountPerDay;
  }

  public void setViewCountPerDay(double viewCountPerDay) {
    this.viewCountPerDay = viewCountPerDay;
  }

  public String getVideoId() {
    return videoId;
  }

  public URL getUrl() {
    return url;
  }
  public void setUrl(URL url) {
    this.url = url;
  }
  public DateTime getOldTimestamp() {
    return oldTimestamp;
  }
  public void setOldTimestamp(DateTime oldTimestamp) {
    this.oldTimestamp = oldTimestamp;
  }
  public BigInteger getViewCount() {
    return viewCount;
  }
  public void setViewCount(BigInteger viewCount) {
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

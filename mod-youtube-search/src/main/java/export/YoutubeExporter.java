package export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import data.YoutubeVideoData;

public class YoutubeExporter {
  
  public static void exportData(List<YoutubeVideoData> dataList) {
    if (dataList == null || dataList.isEmpty()) throw new IllegalArgumentException("Empty data");
    File outFile = new File("/home/dandy/youtube.html");
    try {
      
      BufferedWriter bwr = new BufferedWriter(new FileWriter(outFile));
      bwr.write("<html>\n");
      for (YoutubeVideoData data: dataList) {
        bwr.write("<a href=\"" + data.getUrl().toExternalForm() + "\">" + data.getTitle() + "</a>" + data.getViewCountPerDay() + " views/day<br/>");
      }
      bwr.write("</html>\n");
      bwr.flush();
      bwr.close();
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}

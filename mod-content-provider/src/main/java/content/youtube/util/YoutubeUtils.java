package content.youtube.util;

import java.util.List;

import com.google.api.services.youtube.model.SearchResult;

public class YoutubeUtils {

  public static String constructStringFromList(List<String> videoIdList) {
    if (videoIdList != null) {
      StringBuilder sb = new StringBuilder();
      
      for (String id: videoIdList) {
        if (sb.length() > 0) {
          sb.append("," + id);
        } else {
          sb.append(id);
        }
      }
      return sb.toString();
    }
    return null;
  }
  
  public static String constructIdsStringFromItemsList(List<SearchResult> list) {
    if (list != null) {
      StringBuilder sb = new StringBuilder();
      for (SearchResult result: list) {
        if (sb.length() > 0) {
          sb.append("," + result.getId().getVideoId());
        } else {
          sb.append(result.getId().getVideoId());
        }
      }
      return sb.toString();
    }
    return null;
  }
  
}

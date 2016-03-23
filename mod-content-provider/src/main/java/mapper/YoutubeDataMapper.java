package mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatistics;

import data.YoutubeVideoData;

public class YoutubeDataMapper {

  public static List<YoutubeVideoData> buildYoutubeDataList(List<SearchResult> searchResultList, List<Video> detailList) {
    List<YoutubeVideoData> resultList = new ArrayList<>();
    if (searchResultList != null) {
      
      Map<String, Video> detailMap = new HashMap<>();
      if (detailList != null) {
        for (Video detail: detailList) {
          if (detail.getId() != null) {
            detailMap.put(detail.getId(), detail);
          }
        }
      }
      
      for (SearchResult searchResult: searchResultList) {
        if (   searchResult.getId() != null
            && searchResult.getId().getVideoId() != null
        ) {
          Video detail = detailMap.get(searchResult.getId().getVideoId());
          resultList.add(buildYoutubeData(searchResult, detail));
        }
      }
    }
    return resultList;
  }
  
  public static YoutubeVideoData buildYoutubeData(SearchResult searchResult, Video video) {
    
    YoutubeVideoData data = null;
    
    if (searchResult != null) {
      ResourceId resourceId = searchResult.getId();
      
      if (   resourceId != null
          && resourceId.getVideoId() != null
      ) {
        data = new YoutubeVideoData(resourceId.getVideoId());
        
        SearchResultSnippet snippet = searchResult.getSnippet();
        if (snippet != null) {
          if (snippet.getTitle() != null) data.setTitle(snippet.getTitle());
          if (snippet.getPublishedAt() != null) data.setOldTimestamp(snippet.getPublishedAt());
        }
      }
    }
    
    if (   data != null 
        && video != null
    ) {
      VideoStatistics statistics = video.getStatistics();
      if (statistics != null) {
        if (statistics.getViewCount() != null) data.setViewCount(statistics.getViewCount());
        
        if (data.getOldTimestamp() != null) {
          DateTime now = new DateTime(System.currentTimeMillis());
          double daysCountOld = ((now.getValue() - data.getOldTimestamp().getValue()) / (1000.0 * 60 * 60 * 24));
          if (   daysCountOld >= 0
              && data.getViewCount() != null
          ) {
            data.setViewCountPerDay(data.getViewCount().longValue() / daysCountOld);
          }
        }
      }
      
//      VideoContentDetails details = video.getContentDetails();
//      if (details != null) {
//      }
    }
    
    
    return data;
  }
  
}

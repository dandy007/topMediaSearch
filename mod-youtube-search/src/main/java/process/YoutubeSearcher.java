package process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;

import content.youtube.data.YoutubeVideoData;
import content.youtube.data.mapper.YoutubeDataMapper;
import content.youtube.provider.YoutubeContentProvider;
import content.youtube.util.YoutubeUtils;

public class YoutubeSearcher {

  YoutubeContentProvider provider;
  
  public YoutubeSearcher() {
    provider = new YoutubeContentProvider();
  }
  
  public List<YoutubeVideoData> getTopVideos(String query, long count) throws IOException {
    List<SearchResult> itemsResult = provider.fetchItems(
      null, // relatedTo
      query,
      YoutubeContentProvider.ORDER__VIEWCOUNT, 
      YoutubeContentProvider.TYPE__VIDEO, 
      null, // no token 
      count
    );
    
    List<Video> detailDataList = getDetails(itemsResult);
    
    return YoutubeDataMapper.buildYoutubeDataList(itemsResult, detailDataList);
  }
  
  public List<YoutubeVideoData> deepSearch(String relatedToVideoId, long count) throws IOException {
    
    ConcurrentLinkedQueue<SearchResult> processQueue = new ConcurrentLinkedQueue<>(getRelatedVideos(relatedToVideoId, YoutubeContentProvider.MAX_NUMBER_OF_VIDEOS_RETURNED));
    ConcurrentLinkedQueue<SearchResult> fetchQueue = new ConcurrentLinkedQueue<>();
    
    Map<String, SearchResult> resultMap = new HashMap<>();
    
    if (   processQueue != null
        && !processQueue.isEmpty()
    ) {
      
      SearchResult video = null;
      while ((video = processQueue.poll()) != null) {
        ResourceId resId = video.getId();
        if (resId != null) {
          SearchResult mapItem = resultMap.get(resId.getVideoId());
          if (mapItem == null) {
            resultMap.put(resId.getVideoId(), video);
            fetchQueue.add(video);
            if (resultMap.values().size() < count) {
              if (resultMap.values().size() % 100 == 0) {
                System.out.println("Fetched " + resultMap.values().size() + " records.");
              }
            } else {
              System.out.println("Fetched " + resultMap.values().size() + " records.");
              break;
            }
          }
        }
        if (processQueue.isEmpty()) {
          SearchResult nextVideo = fetchQueue.poll();
          if (   nextVideo != null
              && nextVideo.getId() != null
              && nextVideo.getId().getVideoId() != null
          ) {
            processQueue.addAll(getRelatedVideos(nextVideo.getId().getVideoId(), YoutubeContentProvider.MAX_NUMBER_OF_VIDEOS_RETURNED));
          }
        }
      }
      
    }
    
    if (resultMap.isEmpty()) {
      return null;
    } else {
      Collection<SearchResult> result = resultMap.values();
      ArrayList<SearchResult> resultList = new ArrayList<>();
      resultList.addAll(result);
      
      System.out.println("Fetching video details for " + resultMap.values().size() + " records.");
      List<Video> detailDataList = getDetails(resultList);
      
      return YoutubeDataMapper.buildYoutubeDataList(resultList, detailDataList);
    }
    
  }
  
  public List<SearchResult> getRelatedVideos(String relatedToVideoId, long count) throws IOException {
    return provider.fetchItems(
      relatedToVideoId,
      null, // all
      YoutubeContentProvider.ORDER__VIEWCOUNT, 
      YoutubeContentProvider.TYPE__VIDEO, 
      null, // no token 
      count
    );
  }
  
//  public List<YoutubeVideoData> getRelatedVideos(String relatedToVideoId, long count) throws IOException {
//    List<SearchResult> itemsResult = provider.fetchItems(
//      relatedToVideoId,
//      null, // all
//      YoutubeContentProvider.ORDER__VIEWCOUNT, 
//      YoutubeContentProvider.TYPE__VIDEO, 
//      null, // no token 
//      count
//    );
//    
//    List<Video> detailDataList = getDetails(itemsResult);
//    
//    return YoutubeDataMapper.buildYoutubeDataList(itemsResult, detailDataList);
//  }
  
  public List<Video> getDetails(List<SearchResult> searchResultList) throws IOException {
    return provider.fetchItemDetails(
      YoutubeUtils.constructIdsStringFromItemsList(
        searchResultList
      )
    );
  }
  
}

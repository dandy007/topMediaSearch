package process;

import java.io.IOException;
import java.util.List;

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
  
  public List<YoutubeVideoData> getRelatedVideos(String relatedToVideoId, long count) throws IOException {
    List<SearchResult> itemsResult = provider.fetchItems(
      relatedToVideoId,
      null, // all
      YoutubeContentProvider.ORDER__VIEWCOUNT, 
      YoutubeContentProvider.TYPE__VIDEO, 
      null, // no token 
      count
    );
    
    List<Video> detailDataList = getDetails(itemsResult);
    
    return YoutubeDataMapper.buildYoutubeDataList(itemsResult, detailDataList);
  }
  
  public List<Video> getDetails(List<SearchResult> searchResultList) throws IOException {
    return provider.fetchItemDetails(
      YoutubeUtils.constructIdsStringFromItemsList(
        searchResultList
      )
    );
  }
  
}

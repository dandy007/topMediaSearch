package content.youtube;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.base.Joiner;

public class YoutubeContentProvider {
  
  private static final String PROPERTIES_FILENAME = "youtube.properties";

  private static final int MAX_NUMBER_OF_VIDEOS_RETURNED = 50;

  private YouTube youtube;
  private Properties properties;
  private static String API_KEY = null;

  private static final String PROPERTY_KEY__API_KEY = "youtube.apikey";
  
  public static final String PART__ID = "id";
  public static final String PART__SNIPPET = "snippet";
  public static final String PART__CONTENT_DETAILS = "contentDetails";
  public static final String PART__STATISTICS = "statistics";
  
  public static final String TYPE__VIDEO = "video";
  
  public static final String ORDER__VIEWCOUNT = "viewCount";
  
  
  
  // videoSyndicated = true !!!!!!!!!
  
  // get list of most viewed videos - query points 
  // https://www.googleapis.com/youtube/v3/search?part=snippet&q=stop&key=AIzaSyDxu7GXLm7JbHmY009FTsDGkxFRaPRMszA&pageToken=CAUQAA
  
  // get time(contentDetails) + viewCount/likes(statistics) - query points 
  // https://www.googleapis.com/youtube/v3/videos?part=contentDetails,statistics&order=viewCount&id=TruIq5IxuiU,-VoFbH8jTzE,RPNDXrAvAMg,gmQmYc9-zcg&key=AIzaSyDxu7GXLm7JbHmY009FTsDGkxFRaPRMszA
  
  // get related video to video id - query points 
  // https://www.googleapis.com/youtube/v3/search?part=snippet&relatedToVideoId=9bZkp7q19f0&type=video&key=AIzaSyDxu7GXLm7JbHmY009FTsDGkxFRaPRMszA
  
  public YoutubeContentProvider() {
    properties = new Properties();
    try {
        InputStream in = Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
        properties.load(in);
        
        API_KEY = properties.getProperty(PROPERTY_KEY__API_KEY);
        
        youtube = new YouTube.Builder(
          new NetHttpTransport(), 
          new JacksonFactory(), 
          new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
          }
        ).setApplicationName("youtube-cmdline-search-sample").build();

    } catch (IOException e) {
//        System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
//                + " : " + e.getMessage());
//        System.exit(1);
    }
  }
  
  
  public SearchListResponse query(YouTube.Search.List search) throws IOException {
    SearchListResponse searchResponse = search.execute();
    return searchResponse;
  }
  
  public VideoListResponse query(YouTube.Videos.List search) throws IOException {
    VideoListResponse videoResponse = search.execute();
    return videoResponse;
  }
  
  public List<Video> fetchItemDetails(String videoIdList) throws IOException {
    if (videoIdList == null) return null;
    
    List<Video> videoResult = new ArrayList<>();
    
    YouTube.Videos.List search = youtube.videos().list(PART__CONTENT_DETAILS + "," + PART__STATISTICS);
    search.setKey(API_KEY);
    
    String idArray[] = videoIdList.split(",");
    int count = 0;
    
    if (idArray != null) {
      count = idArray.length;
    }
    
    String idArrayPart[] = null;
    int lastIndex = 0;
    while (count > 0) {
      if (count > MAX_NUMBER_OF_VIDEOS_RETURNED) {
        idArrayPart = new String[MAX_NUMBER_OF_VIDEOS_RETURNED];
        System.arraycopy(idArray, lastIndex, idArrayPart, 0, MAX_NUMBER_OF_VIDEOS_RETURNED);
      } else if (count <= MAX_NUMBER_OF_VIDEOS_RETURNED) {
        idArrayPart = new String[count];
        System.arraycopy(idArray, lastIndex, idArrayPart, 0, count);
      }
      
      search.setId(Joiner.on(',').join(idArrayPart));
      
      VideoListResponse result = query(search);
      
      if (  result != null
          & result.getItems() != null
      ) {
        videoResult.addAll(result.getItems());
      }
      
      lastIndex += idArrayPart.length;
      count -= idArrayPart.length;
    }

    return videoResult.isEmpty() ? null : videoResult;
  }
  
  public List<SearchResult> fetchItems(String videoId, String query, String order, String type, String pageToken, Long numRows) throws IOException {
    YouTube.Search.List search = youtube.search().list(PART__ID + "," + PART__SNIPPET);
    search.setKey(API_KEY);
    
    if (query != null) search.setQ(query);
    if (type != null) search.setType(type);
    if (order != null) search.setOrder(order);
    if (pageToken != null) search.setPageToken(pageToken);
    if (videoId != null) search.setRelatedToVideoId(videoId);

    //search.setFields("items(id/videoId,id/kind,snippet/title)"); TODO optimize
    
    List<SearchResult> resultList = new ArrayList<>();
    
    boolean goNext = true;
    
    while (goNext) {
      
      if (numRows != null) {
        if (numRows <= MAX_NUMBER_OF_VIDEOS_RETURNED) {
          search.setMaxResults(numRows);
          numRows = 0L;
        } else {
          search.setMaxResults((long) MAX_NUMBER_OF_VIDEOS_RETURNED);
          numRows -= MAX_NUMBER_OF_VIDEOS_RETURNED;
        }
      } else {
        search.setMaxResults((long) MAX_NUMBER_OF_VIDEOS_RETURNED);
      }
      
      SearchListResponse searchResponse = query(search);
      
      if (   searchResponse != null
          && !searchResponse.isEmpty()
      ) {
        
        if (   searchResponse.getItems() == null 
            || searchResponse.getItems().size() == 0
        ) {
          goNext = false;
        } else {
          resultList.addAll(searchResponse.getItems());
        }
        
        if (searchResponse.getNextPageToken() != null) {
          search.setPageToken(searchResponse.getNextPageToken());
        } else {
          goNext = false;
          continue;
        }
        
        if (   numRows != null 
            && numRows <= 0
        ) {
          goNext = false;
          continue;
        }
        
      } else {
        // TODO handle empty result
      }
      
    }
    return resultList;
  }

/*
 * Prompt the user to enter a query term and return the user-specified term.
 */
//private static String getInputQuery() throws IOException {
//
//    String inputQuery = "";
//
//    System.out.print("Please enter a search term: ");
//    BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
//    inputQuery = bReader.readLine();
//
//    if (inputQuery.length() < 1) {
//        // Use the string "YouTube Developers Live" as a default.
//        inputQuery = "YouTube Developers Live";
//    }
//    return inputQuery;
//}

/*
 * Prints out all results in the Iterator. For each result, print the
 * title, video ID, and thumbnail.
 *
 * @param iteratorSearchResults Iterator of SearchResults to print
 *
 * @param query Search query (String)
 */
//private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {
//
//    System.out.println("\n=============================================================");
//    System.out.println(
//            "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
//    System.out.println("=============================================================\n");
//
//    if (!iteratorSearchResults.hasNext()) {
//        System.out.println(" There aren't any results for your query.");
//    }
//
//    while (iteratorSearchResults.hasNext()) {
//
//        SearchResult singleVideo = iteratorSearchResults.next();
//        ResourceId rId = singleVideo.getId();
//
//        // Confirm that the result represents a video. Otherwise, the
//        // item will not contain a video ID.
//        if (rId.getKind().equals("youtube#video")) {
//
//            System.out.println(" Video Id" + rId.getVideoId());
//            System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
//            System.out.println(" Views: " + singleVideo.getSnippet().getTitle());
//            System.out.println("\n-------------------------------------------------------------\n");
//        }
//    }
//  }

}

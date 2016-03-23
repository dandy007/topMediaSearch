package main;

import java.io.IOException;
import java.util.List;

import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;

import content.youtube.provider.YoutubeContentProvider;
import content.youtube.util.YoutubeUtils;

public class Launcher {

	public static void main(String[] args) throws IOException {
		
		YoutubeContentProvider provider = new YoutubeContentProvider();
		
		
		List<SearchResult> result = provider.fetchItems(
		  null, // relatedTo
		  null, // all
		  YoutubeContentProvider.ORDER__VIEWCOUNT, 
		  YoutubeContentProvider.TYPE__VIDEO, 
		  null, // no token 
		  1L
		);
		
		
		List<Video> videoResult = provider.fetchItemDetails(
		  YoutubeUtils.constructIdsStringFromItemsList(
		    result
		  )
		);
		
		
		if (videoResult != null) {
		  int counter = 0;
  		for (Video video: videoResult) {
  		  System.out.println(++counter + ". " + video.getId() + ": " + video.getStatistics().getViewCount().longValue() + " views");
  		}
		}
		
		
		

		
		
	}

}

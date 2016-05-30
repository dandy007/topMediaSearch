package main;

import java.io.IOException;
import java.util.List;

import content.youtube.data.YoutubeVideoData;
import content.youtube.db.YoutubeDbDao;
import export.YoutubeExporter;
import process.YoutubeSearcher;

public class YoutubeLauncher {

	public static void main(String[] args) throws IOException {
	  YoutubeSearcher searcher = new YoutubeSearcher();
	  List<YoutubeVideoData> result = searcher.deepSearch("OXWrjWDQh7Q", 10000);
	  System.out.println("Search complete");
	  
//	  Collections.sort(result, new YoutubeDataVideoComparator());
	  
	  int count = 0;
	  for (YoutubeVideoData data: result) {
	    count++;
	    if (count % 100 == 0) {
	      System.out.println("Perform db operations " + count + " / " + + result.size());
	    }
	    YoutubeDbDao.insertData(data);
	  }
	  
	  YoutubeExporter.exportData(result);
	}

}

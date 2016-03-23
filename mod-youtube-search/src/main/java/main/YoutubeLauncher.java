package main;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import content.youtube.data.YoutubeVideoData;
import content.youtube.db.YoutubeDbDao;
import export.YoutubeExporter;
import ordering.YoutubeDataVideoComparator;
import process.YoutubeSearcher;

public class YoutubeLauncher {

	public static void main(String[] args) throws IOException {
	  YoutubeSearcher searcher = new YoutubeSearcher();
	  List<YoutubeVideoData> result = searcher.getTopVideos(null, 100000);
	  System.out.println("Search complete");
	  
	  Collections.sort(result, new YoutubeDataVideoComparator());
	  
	  for (YoutubeVideoData data: result) {
	    YoutubeDbDao.insertData(data);
	  }
	  
	  YoutubeExporter.exportData(result);
	}

}

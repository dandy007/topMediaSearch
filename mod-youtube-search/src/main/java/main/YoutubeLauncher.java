package main;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import data.YoutubeVideoData;
import export.YoutubeExporter;
import ordering.YoutubeDataVideoComparator;
import process.YoutubeSearcher;

public class YoutubeLauncher {

	public static void main(String[] args) throws IOException {
	  YoutubeSearcher searcher = new YoutubeSearcher();
	  List<YoutubeVideoData> result = searcher.getTopVideos("avicii levels", 1000);
	  System.out.println("Search complete");
	  
	  Collections.sort(result, new YoutubeDataVideoComparator());
	  
	  YoutubeExporter.exportData(result);
	}

}

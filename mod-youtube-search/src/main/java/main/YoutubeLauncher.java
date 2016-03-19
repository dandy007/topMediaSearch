package main;

import java.net.MalformedURLException;
import java.net.URL;

import parser.YoutubePageParser;

public class YoutubeLauncher {

	public static void main(String[] args) {
	  
	  try {
      new YoutubePageParser().parse(new URL("https://www.youtube.com/results?q=stop&sp=CAM%253D"));
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
	  
	}

}

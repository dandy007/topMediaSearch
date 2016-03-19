package main;

import common.WebPageDownloader;

public class Launcher {

	public static void main(String[] args) {
		System.out.println(WebPageDownloader.getContent("https://www.youtube.com/watch?v=txsTnw8DQS4"));
	}

}

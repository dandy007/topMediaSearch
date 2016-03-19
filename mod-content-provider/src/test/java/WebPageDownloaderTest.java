import static org.junit.Assert.*;

import org.junit.Test;

import common.WebPageDownloader;

public class WebPageDownloaderTest {

	@Test
	public void getContentTest() {
		String url = "https://youtube.com";
		String result = null;
		try {
			result = WebPageDownloader.getContent(url);
		} catch (Exception e) {}
		
		assertTrue(result != null);
	}
	
}

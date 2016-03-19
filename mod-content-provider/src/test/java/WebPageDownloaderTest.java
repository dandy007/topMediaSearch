import static org.junit.Assert.assertTrue;

import org.jsoup.nodes.Document;
import org.junit.Test;

import common.WebPageDownloader;

public class WebPageDownloaderTest {

	@Test
	public void getContentTest() {
		String url = "https://youtube.com";
		Document result = null;
		try {
			result = WebPageDownloader.getContent(url);
		} catch (Exception e) {}
		
		assertTrue(result != null);
	}
	
}

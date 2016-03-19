import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import data.YoutubeData;
import parser.YoutubePageParser;

public class YoutubePageParserTest {

  @Test
  public void parse() throws MalformedURLException {
    YoutubeData result = new YoutubePageParser().parse(new URL("https://www.youtube.com/results?q=stop&sp=CAM%253D"));
    Assert.assertTrue(result != null && result.getResultLinkList() != null && result.getResultLinkList().size() > 0);
  }
  
}

package ordering;

import java.util.Comparator;

import data.YoutubeVideoData;

public class YoutubeDataVideoComparator implements Comparator<YoutubeVideoData> {

  @Override
  public int compare(YoutubeVideoData o1, YoutubeVideoData o2) {
    
    if (o1.equals(o2)) return 0;
    
    if (o2.getViewCountPerDay() == o1.getViewCountPerDay()) return 0;
    if (o2.getViewCountPerDay() > o1.getViewCountPerDay()) return 1;
    if (o2.getViewCountPerDay() < o1.getViewCountPerDay()) return -1;
    return 0;
  }

}

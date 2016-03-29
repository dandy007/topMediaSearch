package content.youtube.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import content.youtube.data.YoutubeVideoData;

public class YoutubeDbDao {
  
  //JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  static final String DB_URL = "jdbc:mysql://localhost:3306/youtube";
  static final String USER = "root";
  static final String PASS = "dandy";

  //STEP 2: Register JDBC driver
  static Class driverClass = null;
  static Connection conn = null;

  static {
    try {
      driverClass = Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  //  Database credentials
  
  public static boolean selectData(String videoId) {
    PreparedStatement preparedStatement = null;
    try{
      String selectSQL = "select videoId from YOUTUBE_VIDEO where videoId = ?";
      preparedStatement = conn.prepareStatement(selectSQL);
      preparedStatement.setString(1, videoId);
      // execute insert SQL stetement
      ResultSet result = preparedStatement.executeQuery();

      if (result.next()) {
        return true;
      }
      return false;

   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(preparedStatement!=null)
           preparedStatement.close();
      }catch(SQLException se2){
      }// nothing we can do
//      try{
//         if(conn!=null)
//            conn.close();
//      }catch(SQLException se){
//         se.printStackTrace();
//      }
   }//end try
    return true;
  }
  
  public static void insertData(YoutubeVideoData data) {
    PreparedStatement preparedStatement = null;
    try{
      
      if (selectData(data.getVideoId())) {
        String updateTableSQL = "UPDATE YOUTUBE_VIDEO set "
            + " viewCount=?,"
            + " viewCountDay=?"
            + " where videoId=?"
        ;
        preparedStatement = conn.prepareStatement(updateTableSQL);
        preparedStatement.setBigDecimal(1, BigDecimal.valueOf(data.getViewCount().longValue()));
        preparedStatement.setBigDecimal(2, BigDecimal.valueOf(data.getViewCountPerDay()));
        preparedStatement.setString(3, data.getVideoId());
      } else {
        String insertTableSQL = "INSERT INTO YOUTUBE_VIDEO"
            + "(videoId, title, created, viewCount, viewCountDay, author) VALUES"
            + "(?,?,?,?,?,?)";
        preparedStatement = conn.prepareStatement(insertTableSQL);
        preparedStatement.setString(1, data.getVideoId());
        preparedStatement.setString(2, data.getTitle());
        preparedStatement.setTimestamp(3, new Timestamp(data.getOldTimestamp().getValue()));
        preparedStatement.setBigDecimal(4, BigDecimal.valueOf(data.getViewCount().longValue()));
        preparedStatement.setBigDecimal(5, BigDecimal.valueOf(data.getViewCountPerDay()));
        preparedStatement.setString(6, data.getAuthor());
      }
      // execute insert SQL statement
      preparedStatement .executeUpdate();
      preparedStatement.close();
//      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(preparedStatement!=null)
           preparedStatement.close();
      }catch(SQLException se2){
      }// nothing we can do
//      try{
//         if(conn!=null)
//            conn.close();
//      }catch(SQLException se){
//         se.printStackTrace();
//      }
   }//end try
  }
}

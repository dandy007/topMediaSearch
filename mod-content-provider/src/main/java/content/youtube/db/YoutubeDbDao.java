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

  //  Database credentials
  static final String USER = "root";
  static final String PASS = "dandy";
  
  public static boolean selectData(String videoId) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
//      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
//      System.out.println("Creating statement...");
      
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
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
    return true;
  }
  
  public static void insertData(YoutubeVideoData data) {
    if (selectData(data.getVideoId())) return;
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
//      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
//      System.out.println("Creating statement...");
      
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
      // execute insert SQL stetement
      preparedStatement .executeUpdate();
      
//      ResultSet rs = stmt.executeUpdate(sql);

//      //STEP 5: Extract data from result set
//      while(rs.next()){
//         //Retrieve by column name
//         int id  = rs.getInt("id");
//         int age = rs.getInt("age");
//         String first = rs.getString("first");
//         String last = rs.getString("last");
//
//         //Display values
//         System.out.print("ID: " + id);
//         System.out.print(", Age: " + age);
//         System.out.print(", First: " + first);
//         System.out.println(", Last: " + last);
//      }
      //STEP 6: Clean-up environment
//      rs.close();
      preparedStatement.close();
      conn.close();
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
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
  }
}

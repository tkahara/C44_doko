package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MuttersDAO {
  // データベース接続に使用する情報
  private final String JDBC_URL = "jdbc:mysql://localhost/keg_db";
  private final String DB_USER = "keg_user";
  private final String DB_PASS = "keg_pass";

  public List<Mutter> findAll() {
    List<Mutter> mutterList = new ArrayList<Mutter>();
    // JDBCドライバを読み込む
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new IllegalStateException("JDBCドライバを読み込めませんでした");
    }
    // データベース接続
    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

      // SELECT文の準備
      String sql = "SELECT ID,NAME,TEXT,CREATED_AT,FLAG, IMAGE FROM MUTTERS WHERE FLAG = 1 ORDER BY ID DESC";
      PreparedStatement pStmt = conn.prepareStatement(sql);

      // SELECT文を実行
      ResultSet rs = pStmt.executeQuery();

      // SELECT文の結果をArrayListに格納
      while (rs.next()) {
        int id = rs.getInt("ID");
        String userName = rs.getString("NAME");
        String text = rs.getString("TEXT");
        Timestamp createdAt = rs.getTimestamp("CREATED_AT");
        byte[] image = rs.getBytes("IMAGE");
        Mutter mutter = new Mutter(id, userName, text, createdAt, image);
        mutterList.add(mutter);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return mutterList;
  }
//INSERT: つぶやき投稿（画像付き）
 public void insert(Mutter mutter) {
   String sql = "INSERT INTO MUTTERS (NAME, TEXT,  FLAG, IMAGE) VALUES (?, ?, 1, ?)";
   try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
        PreparedStatement stmt = conn.prepareStatement(sql)) {
     
		     stmt.setString(1, mutter.getUserName());
		     stmt.setString(2, mutter.getText());
	//	     stmt.setTimestamp(3, mutter.getCreatedAt());
		     stmt.setBytes(3, mutter.getImage());  // 画像データ（nullでも可）
		     stmt.executeUpdate();
   		} catch (SQLException e) {
     e.printStackTrace();
   }
 }
 
  public boolean create(Mutter mutter) {
	  // JDBCドライバを読み込む
	  try {
		  Class.forName("com.mysql.cj.jdbc.Driver");
	  } catch(ClassNotFoundException e) {
	      throw new IllegalStateException("JDBCドライバを読み込めませんでした");
	  }
    // データベース接続
    try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

      // INSERT文の準備(idは自動連番なので指定しなくてよい）
      String sql = "INSERT INTO MUTTERS(NAME, TEXT) VALUES(?, ?)";
      PreparedStatement pStmt = conn.prepareStatement(sql);
      
      // INSERT文中の「?」に使用する値を設定しSQLを完成
      pStmt.setString(1, mutter.getUserName());
      pStmt.setString(2, mutter.getText());

      // INSERT文を実行（resultには追加された行数が代入される）
      int result = pStmt.executeUpdate();
      if (result != 1) {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
public void deleteMutterById(int id) {
	  Connection conn = null;
	  PreparedStatement stmt = null;

	  try {
	    conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	    String sql = "UPDATE MUTTERS SET FLAG = 0 WHERE ID = ?";
	    stmt = conn.prepareStatement(sql);
	    stmt.setInt(1, id);
	    stmt.executeUpdate();
	  } catch (SQLException e) {
	    e.printStackTrace();
	  } finally {
	    try {
	      if (stmt != null) stmt.close();
	      if (conn != null) conn.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }
	}
public void updateMutterText(int id, String text) {
	  Connection conn = null;
	  PreparedStatement stmt = null;

	  try {
	    conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	    String sql = "UPDATE MUTTERS SET TEXT = ? WHERE ID = ? AND FLAG = 1";
	    stmt = conn.prepareStatement(sql);
	    stmt.setString(1, text);
	    stmt.setInt(2, id);
	    stmt.executeUpdate();
	  } catch (SQLException e) {
	    e.printStackTrace();
	  } finally {
	    try {
	      if (stmt != null) stmt.close();
	      if (conn != null) conn.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }
	}
public Mutter findById(int id) {
	  Connection conn = null;
	  PreparedStatement stmt = null;
	  ResultSet rs = null;
	  Mutter mutter = null;

	  try {
	    conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	    String sql = "SELECT ID, NAME, TEXT FROM MUTTERS WHERE ID = ? AND FLAG = 1";
	    stmt = conn.prepareStatement(sql);
	    stmt.setInt(1, id);
	    rs = stmt.executeQuery();

	    if (rs.next()) {
	      String userName = rs.getString("NAME");
	      String text = rs.getString("TEXT");
//	      Timestamp createdAt = rs.getTimestamp("CREATED_AT");
//	      byte[] image = rs.getBytes("IMAGE");
         mutter = new Mutter(id, userName, text); // コンストラクタに合わせて調整
	    }
	  } catch (SQLException e) {
	    e.printStackTrace();
	  } finally {
	    try {
	      if (rs != null) rs.close();
	      if (stmt != null) stmt.close();
	      if (conn != null) conn.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }

	  return mutter;
	}

//画像データ取得用メソッド（ImageServlet用）
public byte[] getImageById(int id) {
 String sql = "SELECT IMAGE FROM MUTTERS WHERE ID = ? AND FLAG = 1";
 byte[] imageData = null;
 try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
      PreparedStatement stmt = conn.prepareStatement(sql)) {
   
   stmt.setInt(1, id);
   try (ResultSet rs = stmt.executeQuery()) {
     if (rs.next()) {
       imageData = rs.getBytes("IMAGE");
     }
   }
 } catch (SQLException e) {
   e.printStackTrace();
 }
 return imageData;
}
}
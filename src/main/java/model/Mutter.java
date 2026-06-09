package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Mutter implements Serializable {
  private int id;          // ID
  private String userName; // ユーザー名
  private String text;     // つぶやき内容
  private Timestamp createdAt;
  private byte[] image;  // 追加

  public Mutter() { }

  public Mutter(int id, String userName, String text, Timestamp createdAt,  byte[] image) {
	this.id = id;
    this.userName = userName;
    this.text = text;
    this.createdAt = createdAt;
    this.image = image;
  }
  public Mutter( String userName, String text, byte[] image) {
    this.userName = userName;
    this.text = text;
    this.image = image;
  }
  public Mutter( int id, String userName, String text) {
	    this.id = id;
	    this.userName = userName;
	    this.text = text;
}
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName;  }
  public String getText() { return text; }
  public void setText(String text) { this.text = text; }
  public Timestamp getCreatedAt() { return createdAt; }
  public byte[] getImage() {  return image; }
  public void setImage(byte[] image) { this.image = image; }
}
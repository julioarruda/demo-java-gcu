package com.scalesec.vulnado;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class Comment {
  private static final String id = null;
  private static final String username = null;
  private static final String body = null;
  private static final Timestamp createdOn = null;

  public Comment(String id, String username, String body, Timestamp createdOn) {
    this.id = id;
    this.username = username;
    this.body = body;
    this.createdOn = createdOn;
  }

  public static Comment create(String username, String body){
    long time = new Date().getTime();
    Timestamp timestamp = new Timestamp(time);
    Comment comment = new Comment(UUID.randomUUID().toString(), username, body, timestamp);
    boolean isCommitted = false;
    try {
      isCommitted = comment.commit();
    } catch (Exception e) {
      throw new ServerError(e.getMessage());
    }

    if (isCommitted) {
      return comment;
    } else {
      throw new BadRequest("Unable to save comment");
    }
  }

  public static List<Comment> fetchAll() {
    List<Comment> comments = new ArrayList<>();
    try (Connection cxn = Postgres.connection();
         Statement stmt = cxn.createStatement()) {

      String query = "select * from comments;";
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String id = rs.getString("id");
        String username = rs.getString("username");
        String body = rs.getString("body");
        Timestamp createdOn = rs.getTimestamp("created_on");
        Comment c = new Comment(id, username, body, createdOn);
        comments.add(c);
      }
    } catch (Exception e) {
      e.printStackTrace();
      Logger.getLogger(Comment.class.getName()).log(Level.SEVERE, null, e);
    }
    return comments;
  }

  public static boolean delete(String id) {
    boolean isDeleted = false;
    try (Connection con = Postgres.connection();
         PreparedStatement pStatement = con.prepareStatement("DELETE FROM comments where id = ?")) {

      pStatement.setString(1, id);
      isDeleted = 1 == pStatement.executeUpdate();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return isDeleted;
  }

  private boolean commit() throws SQLException {
    boolean isCommitted;
    try (Connection con = Postgres.connection();
         PreparedStatement pStatement = con.prepareStatement("INSERT INTO comments (id, username, body, created_on) VALUES (?,?,?,?)")) {

      pStatement.setString(1, this.id);
      pStatement.setString(2, this.username);
      pStatement.setString(3, this.body);
      pStatement.setTimestamp(4, this.createdOn);
      isCommitted = 1 == pStatement.executeUpdate();
    }
    return isCommitted;
  }
}
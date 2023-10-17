package com.scalesec.vulnado;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class Comment {
  private static final Logger logger = Logger.getLogger(Comment.class.getName());
  private String id, body;
  private String userName;
  private Timestamp createdOn;

  public Comment(String id, String userName, String body, Timestamp createdOn) {
    this.id = id;
    this.userName = userName;
    this.body = body;
    this.createdOn = createdOn;
  }

  public static Comment create(String userName, String body){
    long time = new Date().getTime();
    Timestamp timestamp = new Timestamp(time);
    Comment comment = new Comment(UUID.randomUUID().toString(), userName, body, timestamp);
    try (Connection con = Postgres.connection()){
      if (comment.commit(con)) {
        return comment;
      } else {
        throw new BadRequest("Unable to save comment");
      }
    } catch (Exception e) {
      throw new ServerError(e.getMessage());
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
        String userName = rs.getString("username");
        String body = rs.getString("body");
        Timestamp createdOn = rs.getTimestamp("created_on");
        Comment c = new Comment(id, userName, body, createdOn);
        comments.add(c);
      }
    } catch (Exception e) {
      logger.severe(e.getClass().getName()+": "+e.getMessage());
    } 
    return comments;
  }

  public static boolean delete(String id) {
    try (Connection con = Postgres.connection();
         PreparedStatement pStatement = con.prepareStatement("DELETE FROM comments where id = ?")) {
      pStatement.setString(1, id);
      return 1 == pStatement.executeUpdate();
    } catch(Exception e) {
      logger.severe(e.getClass().getName()+": "+e.getMessage());
    } 
    return false;
  }

  private boolean commit(Connection con) throws SQLException {
    try (PreparedStatement pStatement = con.prepareStatement("INSERT INTO comments (id, username, body, created_on) VALUES (?,?,?,?)")) {
      pStatement.setString(1, this.id);
      pStatement.setString(2, this.userName);
      pStatement.setString(3, this.body);
      pStatement.setTimestamp(4, this.createdOn);
      return 1 == pStatement.executeUpdate();
    }
  }
}
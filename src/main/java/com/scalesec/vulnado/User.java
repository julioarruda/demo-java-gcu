package com.scalesec.vulnado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;

public class User {
  private static Logger logger = Logger.getLogger(User.class.getName());
  
  private String id;
  private String username;
  private String hashedPassword;

  public User(String id, String username, String hashedPassword) {
    this.id = id;
    this.setUsername(username);
    this.setHashedPassword(hashedPassword);
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  public String token(String secret) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.builder().setSubject(this.username).signWith(key).compact();
  }

  public static void assertAuth(String secret, String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
      Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token);
    } catch(Exception exception) {
      logger.severe(exception.getMessage());
      throw new Unauthorized(exception.getMessage());
    }
  }

  public static User fetch(String un) {
    User user = null;
    try (Connection cxn = Postgres.connection();
         PreparedStatement stmt = cxn.prepareStatement("select * from users where username = ? limit 1")) {
      logger.info("Opened database successfully");

      stmt.setString(1, un);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          String userId = rs.getString("user_id");
          String username = rs.getString("username");
          String password = rs.getString("password");
          user = new User(userId, username, password);
        }
      }
    } catch (Exception exception) {
      logger.severe(exception.getMessage());
    }
    return user;
  }
}
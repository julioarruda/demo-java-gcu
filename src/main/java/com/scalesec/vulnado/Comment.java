package com.scalesec.vulnado;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class Comment {
    private static final Logger LOGGER = Logger.getLogger(Comment.class.getName());
    private static String id;
    private static String username;
    private static String body;
    private static Timestamp createdOn;

    private Comment(String id, String username, String body, Timestamp createdOn) {
        Comment.id = id;
        Comment.username = username;
        Comment.body = body;
        Comment.createdOn = createdOn;
    }

    public static Comment create(String username, String body){
        long time = new Date().getTime();
        Timestamp timestamp = new Timestamp(time);
        Comment comment = new Comment(UUID.randomUUID().toString(), username, body, timestamp);
        try {
            if (comment.commit()) {
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
        try (Connection cxn = Postgres.connection(); Statement stmt = cxn.createStatement()) {
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
            LOGGER.severe(e.getClass().getName()+": "+e.getMessage());
        }
        return comments;
    }

    public static boolean delete(String id) {
        String sql = "DELETE FROM comments where id = ?";
        try (Connection con = Postgres.connection(); PreparedStatement pStatement = con.prepareStatement(sql)) {
            pStatement.setString(1, id);
            return 1 == pStatement.executeUpdate();
        } catch(Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return false;
    }

    private boolean commit() throws SQLException {
        String sql = "INSERT INTO comments (id, username, body, created_on) VALUES (?,?,?,?)";
        try (Connection con = Postgres.connection(); PreparedStatement pStatement = con.prepareStatement(sql)) {
            pStatement.setString(1, Comment.id);
            pStatement.setString(2, Comment.username);
            pStatement.setString(3, Comment.body);
            pStatement.setTimestamp(4, Comment.createdOn);
            return 1 == pStatement.executeUpdate();
        }
    }
}
package vn.qnu.edu.mxh_demo.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class Post implements BaseEntity<Post> {
    private Integer id;
    private String title;
    private String body;
    private Integer user_id;
    private String status;
    private LocalDateTime created_at;

    // Helper field để hiển thị tên tác giả (không có trong DB posts table trực tiếp nhưng hữu ích khi join)
    private String authorName;

    @Override public String getTableName() { return "posts"; }
    @Override public Object getId() { return this.id; }

    @Override
    public Post mapRow(ResultSet rs) throws SQLException {
        Post p = new Post();
        p.id = rs.getInt("id");
        p.title = rs.getString("title");
        p.body = rs.getString("body");
        p.user_id = rs.getInt("user_id");
        p.status = rs.getString("status");
        p.created_at = rs.getTimestamp("created_at").toLocalDateTime();
        
        // Kiểm tra xem có cột username từ câu lệnh JOIN không
        try {
            p.authorName = rs.getString("username");
        } catch (SQLException ignored) {}
        
        return p;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> p = new LinkedHashMap<>();
        p.put("id", this.id);
        p.put("title", this.title);
        p.put("body", this.body);
        p.put("user_id", this.user_id);
        p.put("status", this.status);
        p.put("created_at", this.created_at);
        return p;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public Integer getUser_id() { return user_id; }
    public void setUser_id(Integer user_id) { this.user_id = user_id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}
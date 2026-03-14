package vn.qnu.edu.mxh_demo.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class Follow implements BaseEntity<Follow> {
    private Integer following_user_id;
    private Integer followed_user_id;
    private LocalDateTime created_at;

    @Override public String getTableName() { return "follows"; }
    
    // Composite ID, so we return a combined string or object
    @Override public Object getId() { return following_user_id + "-" + followed_user_id; }

    @Override
    public Follow mapRow(ResultSet rs) throws SQLException {
        Follow f = new Follow();
        f.following_user_id = rs.getInt("following_user_id");
        f.followed_user_id = rs.getInt("followed_user_id");
        f.created_at = rs.getTimestamp("created_at").toLocalDateTime();
        return f;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> p = new LinkedHashMap<>();
        p.put("following_user_id", this.following_user_id);
        p.put("followed_user_id", this.followed_user_id);
        p.put("created_at", this.created_at);
        return p;
    }

    // Getters and Setters
    public Integer getFollowing_user_id() { return following_user_id; }
    public void setFollowing_user_id(Integer following_user_id) { this.following_user_id = following_user_id; }
    public Integer getFollowed_user_id() { return followed_user_id; }
    public void setFollowed_user_id(Integer followed_user_id) { this.followed_user_id = followed_user_id; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}
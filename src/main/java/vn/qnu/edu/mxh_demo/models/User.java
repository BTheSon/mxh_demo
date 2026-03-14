package vn.qnu.edu.mxh_demo.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class User implements BaseEntity<User> {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private LocalDate created_at;

    @Override public String getTableName() { return "users"; }
    @Override public Object getId() { return this.id; }

    @Override
    public User mapRow(ResultSet rs) throws SQLException {
        User u = new User();

        u.id         = rs.getInt("id");
        u.username   = rs.getString("username");
        u.password   = rs.getString("password");

        u.role       = rs.getString("role");
        u.created_at = rs.getTimestamp("created_at")
                        .toLocalDateTime()
                        .toLocalDate();
        return u;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> p = new LinkedHashMap<>();

        p.put("id", this.id);
        p.put("username", this.username);
        p.put("password", this.password);
        p.put("role", this.role);
        p.put("created_at", this.created_at);
        return p;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
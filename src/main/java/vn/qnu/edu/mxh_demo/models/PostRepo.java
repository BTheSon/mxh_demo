package vn.qnu.edu.mxh_demo.models;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostRepo extends CommonRepository<Post> {
    public PostRepo(DataSource dataSource) {
        super(dataSource);
    }

    public List<Post> findFeed() {
        String sql = "SELECT p.*, u.username FROM posts p JOIN users u ON p.user_id = u.id ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Post().mapRow(rs));
    }

    public List<Post> findByUserId(Integer userId) {
        String sql = "SELECT p.*, u.username FROM posts p JOIN users u ON p.user_id = u.id WHERE p.user_id = ? ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Post().mapRow(rs), userId);
    }
}
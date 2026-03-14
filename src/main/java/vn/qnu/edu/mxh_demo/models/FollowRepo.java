package vn.qnu.edu.mxh_demo.models;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class FollowRepo extends CommonRepository<Follow> {
    public FollowRepo(DataSource dataSource) {
        super(dataSource);
    }

    public List<User> findFollowingUsers(Integer followerId) {
        String sql = "SELECT u.* FROM users u JOIN follows f ON u.id = f.followed_user_id WHERE f.following_user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User().mapRow(rs), followerId);
    }

    public List<User> searchUsersToFollow(Integer currentUserId, String query) {
        String sql = "SELECT * FROM users WHERE (username LIKE ? OR id LIKE ?) AND id != ?";
        String searchTerm = "%" + query + "%";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User().mapRow(rs), searchTerm, searchTerm, currentUserId);
    }

    public int deleteFollow(Integer followerId, Integer followedId) {
        String sql = "DELETE FROM follows WHERE following_user_id = ? AND followed_user_id = ?";
        return jdbcTemplate.update(sql, followerId, followedId);
    }

    public boolean isFollowing(Integer followerId, Integer followedId) {
        String sql = "SELECT count(*) FROM follows WHERE following_user_id = ? AND followed_user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, followerId, followedId);
        return count != null && count > 0;
    }
}
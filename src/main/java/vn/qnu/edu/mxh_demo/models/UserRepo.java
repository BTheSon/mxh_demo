package vn.qnu.edu.mxh_demo.models;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepo extends CommonRepository<User>{
    public UserRepo(DataSource dataSource) {
        super(dataSource);
    }

    public User findByUsername(String username) {
        try {
            var prototype = new User();
            var tableName = prototype.getTableName();
            String query = String.format("select * from %s where username = ?", tableName);
            return jdbcTemplate.queryForObject(
                    query,
                    (rs, rowNum) -> prototype.mapRow(rs),
                    username
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

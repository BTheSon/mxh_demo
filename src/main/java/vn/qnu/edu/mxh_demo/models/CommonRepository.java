package vn.qnu.edu.mxh_demo.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CommonRepository<T extends BaseEntity<T>> {

    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommonRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<T> findAll(T prototype) {
        String sql = "SELECT * FROM " + prototype.getTableName();
        return jdbcTemplate.query(sql, (rs, rowNum) -> prototype.mapRow(rs));
    }

    public T findById(T prototype, Object id) {
        String sql = "SELECT * FROM " + prototype.getTableName() + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> prototype.mapRow(rs), id);
    }

    public int save(T entity) {
        Map<String, Object> params = entity.getParams();
        String columns = String.join(", ", params.keySet());
        String placeholders = String.join(", ", params.keySet().stream().map(k -> "?").toList());

        String sql = "INSERT INTO " + entity.getTableName() + " (" + columns + ") VALUES (" + placeholders + ")";
        return jdbcTemplate.update(sql, params.values().toArray());
    }

    public int update(T entity) {
        Map<String, Object> params = entity.getParams();
        // Tạo chuỗi: col1 = ?, col2 = ?
        String setClause = String.join(", ", params.keySet().stream().map(k -> k + " = ?").toList());

        String sql = "UPDATE " + entity.getTableName() + " SET " + setClause + " WHERE id = ?";

        // Danh sách tham số gồm các giá trị mới + ID cuối cùng
        List<Object> values = new ArrayList<>(params.values());
        values.add(entity.getId());

        return jdbcTemplate.update(sql, values.toArray());
    }

    public int delete(T prototype, Object id) {
        String sql = "DELETE FROM " + prototype.getTableName() + " WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
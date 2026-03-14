package vn.qnu.edu.mxh_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class BaseService {
    @Autowired
    private DataSource dataSource;

    public void rawQuery() throws SQLException {
        try (Connection conn = dataSource.getConnection()) { // Mượn 1 kết nối
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("username"));
            }
        }
    }
}

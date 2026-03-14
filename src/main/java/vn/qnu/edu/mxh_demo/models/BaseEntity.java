package vn.qnu.edu.mxh_demo.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface BaseEntity<T> {
    String getTableName();

    // Map dữ liệu từ DB vào Object (Dùng cho READ)
    T mapRow(ResultSet rs) throws SQLException;

    // Trích xuất dữ liệu từ Object ra Map (Dùng cho CREATE/UPDATE)
    Map<String, Object> getParams();

    // Lấy ID của đối tượng (Dùng cho UPDATE/DELETE)
    Object getId();
}
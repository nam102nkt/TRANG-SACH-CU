package database;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBContext {

    // --- CẤU HÌNH THÔNG TIN KẾT NỐI (GIỮ NGUYÊN CỦA BẠN) ---
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://localhost;" 
    //private static final String DB_URL = "jdbc:sqlserver://localhost:1433;" 
            + "instanceName=SQLJAVAWEB;" 
            + "databaseName=OldBookStoreDB;" 
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "123"; 

    // Biến giữ hồ kết nối (Connection Pool)
    // Chỉ tạo 1 lần duy nhất cho toàn bộ ứng dụng (static)
    private static HikariDataSource dataSource;

    // Khối static: Chạy 1 lần duy nhất khi ứng dụng bắt đầu
    static {
        try {
            HikariConfig config = new HikariConfig();
            
            // 1. Cấu hình cơ bản
            config.setDriverClassName(DB_DRIVER);
            config.setJdbcUrl(DB_URL);
            config.setUsername(DB_USER);
            config.setPassword(DB_PASSWORD);
            
            // 2. Cấu hình tối ưu cho HikariCP (Tùy chỉnh)
            config.setMinimumIdle(5);           // Giữ ít nhất 5 kết nối rảnh
            config.setMaximumPoolSize(20);      // Tối đa 20 kết nối cùng lúc
            config.setConnectionTimeout(30000); // Chờ tối đa 30s để lấy kết nối
            config.setIdleTimeout(600000);      // 10 phút không dùng thì đóng bớt kết nối rảnh

            // 3. Tạo DataSource
            dataSource = new HikariDataSource(config);
            
        } catch (Exception e) {
            System.err.println("Lỗi khởi tạo HikariCP: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- HÀM NÀY GIỮ NGUYÊN TÊN VÀ KIỂU TRẢ VỀ ---
    // Các file khác gọi hàm này sẽ không biết sự thay đổi bên trong
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("HikariDataSource chưa được khởi tạo!");
        }
        return dataSource.getConnection(); // Lấy kết nối từ hồ chứa
    }

    // Test thử kết nối
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối SQL Server qua HikariCP thành công!");
                System.out.println("Tên DB: " + conn.getCatalog());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối:");
            e.printStackTrace();
        }
    }
}
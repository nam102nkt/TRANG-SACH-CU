package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

	private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	// --- THAY ĐỔI 1: CẬP NHẬT CHUỖI KẾT NỐI ---
	// Chúng ta dùng "localhost" (máy hiện tại) và chỉ định "instanceName"
	// mà bạn đã tạo là "SQLJAVAWEB".
	private static final String DB_URL = "jdbc:sqlserver://localhost;" 
			+ "instanceName=SQLJAVAWEB;" // Tên server mới của bạn
			+ "databaseName=OldBookStoreDB;" // Tên CSDL (Xem lưu ý bên dưới)
			+ "encrypt=true;"
			+ "trustServerCertificate=true;";

	// --- THAY ĐỔI 2: DÙNG TÀI KHOẢN "sa" ---
	// Đây là tài khoản admin bạn đã tạo khi cài đặt
	private static final String DB_USER = "sa";

	// --- THAY ĐỔI 3: DÙNG MẬT KHẨU "sa" ---
	// Hãy thay "Mật_khẩu_SA_của_bạn" bằng mật khẩu bạn đã đặt
	private static final String DB_PASSWORD = "123"; // <-- THAY MẬT KHẨU CỦA BẠN VÀO ĐÂY

	public static Connection getConnection() throws SQLException {
		try {
			// (Giữ nguyên) Nạp driver
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("Lỗi: Không tìm thấy JDBC Driver!");
			System.err.println("Bạn đã thêm file .jar của JDBC vào project chưa?");
			e.printStackTrace();
			throw new SQLException("Lỗi nạp Driver", e);
		}

		// (Giữ nguyên) Lấy kết nối
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	public static void main(String[] args) {
		try (Connection conn = getConnection()) {
			if (conn != null) {
				System.out.println("Kết nối SQL Server (SQLJAVAWEB) thành công!");
				System.out.println("Phiên bản: " + conn.getMetaData().getDatabaseProductVersion());
			} else {
				System.out.println("Kết nối SQL Server thất bại!");
			}
		} catch (SQLException e) {
			System.err.println("Lỗi khi kết nối CSDL:");
			e.printStackTrace();
		}
	}
}
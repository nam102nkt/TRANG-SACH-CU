package dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBContext {

    // --- Cáº¤U HÃŒNH THÃ”NG TIN Káº¾T Ná»�I (GIá»® NGUYÃŠN Cá»¦A Báº N) ---
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL =
    	    "jdbc:sqlserver://localhost;"
    	  + "instanceName=SQLJAVAWEB;"
    	  + "databaseName=OldBookStoreDB;"
    	  + "encrypt=true;"
    	  + "trustServerCertificate=true;"
    	  + "loginTimeout=30;";

    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "123"; 

    // Biáº¿n giá»¯ há»“ káº¿t ná»‘i (Connection Pool)
    // Chá»‰ táº¡o 1 láº§n duy nháº¥t cho toÃ n bá»™ á»©ng dá»¥ng (static)
    private static HikariDataSource dataSource;

    // Khá»‘i static: Cháº¡y 1 láº§n duy nháº¥t khi á»©ng dá»¥ng báº¯t Ä‘áº§u
    static {
        try {
            HikariConfig config = new HikariConfig();
            
            // 1. Cáº¥u hÃ¬nh cÆ¡ báº£n
            config.setDriverClassName(DB_DRIVER);
            config.setJdbcUrl(DB_URL);
            config.setUsername(DB_USER);
            config.setPassword(DB_PASSWORD);
            
            // 2. Cáº¥u hÃ¬nh tá»‘i Æ°u cho HikariCP (TÃ¹y chá»‰nh)
            config.setMinimumIdle(5);           // Giá»¯ Ã­t nháº¥t 5 káº¿t ná»‘i ráº£nh
            config.setMaximumPoolSize(20);      // Tá»‘i Ä‘a 20 káº¿t ná»‘i cÃ¹ng lÃºc
            config.setConnectionTimeout(30000); // Chá»� tá»‘i Ä‘a 30s Ä‘á»ƒ láº¥y káº¿t ná»‘i
            config.setIdleTimeout(600000);      // 10 phÃºt khÃ´ng dÃ¹ng thÃ¬ Ä‘Ã³ng bá»›t káº¿t ná»‘i ráº£nh

            // 3. Táº¡o DataSource
            dataSource = new HikariDataSource(config);
            
        } catch (Exception e) {
        	 throw new ExceptionInInitializerError("KhÃ´ng khá»Ÿi táº¡o Ä‘Æ°á»£c HikariCP: " + e.getMessage());
        }
    }

    // --- HÃ€M NÃ€Y GIá»® NGUYÃŠN TÃŠN VÃ€ KIá»‚U TRáº¢ Vá»€ ---
    // CÃ¡c file khÃ¡c gá»�i hÃ m nÃ y sáº½ khÃ´ng biáº¿t sá»± thay Ä‘á»•i bÃªn trong
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("HikariDataSource chÆ°a Ä‘Æ°á»£c khá»Ÿi táº¡o!");
        }
        return dataSource.getConnection(); // Láº¥y káº¿t ná»‘i tá»« há»“ chá»©a
    }

    // Test thá»­ káº¿t ná»‘i
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Káº¿t ná»‘i SQL Server qua HikariCP thÃ nh cÃ´ng!");
                System.out.println("TÃªn DB: " + conn.getCatalog());
            }
        } catch (SQLException e) {
            System.err.println("Lá»—i káº¿t ná»‘i:");
            e.printStackTrace();
        }
    }
}
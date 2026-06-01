import java.sql.*;

public class DB {

    public static Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url      = System.getenv("DB_URL");
            String user     = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (url == null || user == null || password == null) {
                throw new IllegalStateException(
                        "Missing env variables: DB_URL, DB_USER, DB_PASSWORD"
                );
            }

            Connection con = DriverManager.getConnection(url, user, password);
            return con;

        } catch (Exception e) {
            System.out.println("Connection Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            Connection con = getConnection();
            System.out.println("Connection completed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
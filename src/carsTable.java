import java.sql.*;

public class carsTable {
    static final String DB_URL = "jdbc:mysql://localhost/CARS";
    static final String USER = "root";
    static final String PASS = "";
    static String driverName = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stm = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS car_info (" +
                    "carID INT NOT NULL AUTO_INCREMENT," +
                    "serialNum VARCHAR(50) NOT NULL," +
                    "make VARCHAR(50) NOT NULL," +
                    "model VARCHAR(50) NOT NULL," +
                    "colour VARCHAR(20)," +
                    "year INT," +
                    "PRIMARY KEY (carID)" +
                    ")";
            stm.executeUpdate(sql);
            System.out.println("Table is successfully created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

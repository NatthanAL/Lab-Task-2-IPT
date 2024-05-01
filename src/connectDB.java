import java.sql.*;

public class connectDB {
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "";
    //static String  driverName = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {

        try {
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("the connection ok");
        } catch (Exception e) {
            System.out.println("cannot connect");
        }
    }

}

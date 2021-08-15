package Gateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GameGateFirebase {
    public static void main(String[] args) {
        String  url = "jdbc:mysql://localhost:3306/schema1";
        String username = "root";
        String password = "PlzWork";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            System.out.println("Error connecting!");
            e.printStackTrace();
        }

    }

}

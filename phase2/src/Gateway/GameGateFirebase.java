package Gateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GameGateFirebase {
    public static void main(String[] args) {
        String  url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/";
        String username = "admin";
        String password = "BossAcc!";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            System.out.println("Error connecting!");
            e.printStackTrace();
        }

    }

}

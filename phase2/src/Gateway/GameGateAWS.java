package Gateway;

import java.sql.*;
import java.util.*;

public class GameGateAWS {
    public static void main(String[] args) {

        List<Integer> ids = Arrays.asList(1 , 2, 3);
        HashMap<Integer, String> gameData = new HashMap<>();
        gameData.put(-4, "Bron");
        gameData.put(-3, "Bruh Bruthorson");
        gameData.put(-2, String.valueOf(true));
        gameData.put(-1, String.valueOf(5));
        for (int id: ids){
            gameData.put(id, String.valueOf(id));
        }

        String  url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/game_data";
        String username = "admin";
        String password = "BossAcc!";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Database!");
            System.out.println(connection.getSchema());
            Statement statement = connection.createStatement();
            ResultSet resSet = statement.executeQuery("select * from test1");
            System.out.println(resSet);

            for (Map.Entry<Integer, String> integerStringEntry : gameData.entrySet()) {
                Map.Entry pair = (Map.Entry) integerStringEntry;
                statement.executeUpdate("INSERT INTO test1 VALUES("+ pair.getKey() + ",'" + pair.getValue() + "');");
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println("Error connecting!");
            e.printStackTrace();
        }

    }

}

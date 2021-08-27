package Gateway;

import java.sql.*;
import java.util.*;

public class GameGateAWS {
    public static void main(String[] args) {
        ArrayList<HashMap<Integer, String>> mapList = new ArrayList<HashMap<Integer, String>>();

        List<Integer> ids = Arrays.asList(1 , 2, 3);

        for (int i: ids) {
            HashMap<Integer, String> gameData = new HashMap<>();
            gameData.put(-4, "Bron" + i);
            gameData.put(-3, "Bruh Bruthorson");
            gameData.put(-2, String.valueOf(true));
            gameData.put(-1, String.valueOf(5));
            for (int id : ids) {
                gameData.put(id, String.valueOf(id));
            }
            mapList.add(gameData);
        }

        GameGateAWS.save(mapList);

    }
    public static void save(List<HashMap<Integer, String>> myMaps){
        String  url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/game_data";
        String username = "admin";
        String password = "BossAcc!";

        String tableName;


        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Database!");
            System.out.println(connection.getMetaData().getTables(null, null, "test1", null).next());
            Statement statement = connection.createStatement();
            ResultSet resSet = statement.executeQuery("select * from test1");
            System.out.println(resSet);


            for (HashMap<Integer, String> hMap : myMaps) {
                tableName = hMap.get(-4);
                if (connection.getMetaData().getTables(null, null, tableName, null).next()) {
                    System.out.println("Table found.");
                    for (Map.Entry<Integer, String> integerStringEntry : hMap.entrySet()) {
                        statement.executeUpdate("INSERT INTO " + tableName + " VALUES(" + integerStringEntry.getKey() + ",'" + integerStringEntry.getValue() + "') " +
                                "ON DUPLICATE KEY UPDATE value='" + integerStringEntry.getValue() + "';");
                    }
                } else {
                    System.out.println("Creating new table.");

                    statement.execute("CREATE TABLE `" + tableName + "` (\n" +
                            "  `id` INT NOT NULL,\n" +
                            "  `value` MEDIUMTEXT NULL,\n" +
                            "  PRIMARY KEY (`id`));");

                    for (Map.Entry<Integer, String> integerStringEntry : hMap.entrySet()) {
                        statement.executeUpdate("INSERT INTO " + tableName + " VALUES(" + integerStringEntry.getKey() + ",'" + integerStringEntry.getValue() + "') " +
                                "ON DUPLICATE KEY UPDATE value='" + integerStringEntry.getValue() + "';");
                    }

                }
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println("Error connecting!");
            e.printStackTrace();
        }

    }
}

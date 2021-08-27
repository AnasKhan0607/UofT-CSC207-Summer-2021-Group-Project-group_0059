package Gateway;

import java.sql.*;
import java.util.*;

public class GameGateAWS {
    private final String url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/game_data";
    private final String username = "admin";
    private final String password = "BossAcc!";

    public static void main(String[] args) {
        List<HashMap<Integer, String>> mapList = new ArrayList<>();

        List<HashMap<Integer, String>> outList = new ArrayList<>();

        List<Integer> ids = Arrays.asList(1 , 2, 3);

        for (int i: ids) {
            HashMap<Integer, String> gameData = new HashMap<>();
            gameData.put(-4, "test" + i);
            gameData.put(-3, "author test");
            gameData.put(-2, String.valueOf(true));
            gameData.put(-1, String.valueOf(5));
            for (int id : ids) {
                gameData.put(id, String.valueOf(id));
            }
            mapList.add(gameData);
        }

        GameGateAWS ggaws = new GameGateAWS();

        ggaws.save(mapList);
        outList = ggaws.load();
        
    }

    public GameGateAWS() {
    }

    public void save(List<HashMap<Integer, String>> myMaps){

        String tableName;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            for (HashMap<Integer, String> hMap : myMaps) {
                tableName = hMap.get(-4);
                if (connection.getMetaData().getTables(null, null, tableName, null).next()) {
                    for (Map.Entry<Integer, String> integerStringEntry : hMap.entrySet()) {
                        statement.executeUpdate("INSERT INTO " + tableName + " VALUES(" + integerStringEntry.getKey() + ",'" + integerStringEntry.getValue() + "') " +
                                "ON DUPLICATE KEY UPDATE value='" + integerStringEntry.getValue() + "';");
                    }
                } else {
                    System.out.println("Creating new table.");

                    statement.execute("CREATE TABLE `" + tableName + "` (\n" +
                            "  `key` INT NOT NULL,\n" +
                            "  `value` MEDIUMTEXT NULL,\n" +
                            "  PRIMARY KEY (`key`));");

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

    public List<HashMap<Integer, String>> load() {

        List<HashMap<Integer, String>> dbMaps = new ArrayList<>();
        HashMap<Integer, String > currMap;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setSchema("game_data");
            Statement statement = connection.createStatement();
            ResultSet resSet;

            ResultSet myTables = connection.getMetaData().getTables("game_data", null, null, new String[]{"TABLE"});

            while (myTables.next()) {
                currMap = new HashMap<>();
                resSet = statement.executeQuery("select * from " + myTables.getString(3));

                while(resSet.next()) {
                    currMap.put(resSet.getInt("key"), resSet.getString("value"));
                }
                dbMaps.add(currMap);
            }

        } catch (SQLException e) {
            System.out.println("Error connecting!");
            e.printStackTrace();
        }

        return dbMaps;
    }

}

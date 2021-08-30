package Gateway;

import Interface.GameTemplateLoadSave;

import java.sql.*;
import java.util.*;

/**
 *
 * A Gateway class used to implement <I>GameTemplateLoadSave</I> for loading and saving games.
 *
 */
public class GameGate implements GameTemplateLoadSave {
    private final String url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/game_data";
    private final String username = "admin";
    private final String password = "BossAcc!";

    /**
     * The save method takes a /List</Hashmap>> which represents the games
     * and saves it to a sql database.
     * @param myMaps The list of Hashmaps to be saved.
     */
    public void save(List<HashMap<Integer, String>> myMaps){

        String tableName;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            Connection connDel = DriverManager.getConnection(url, username, password);
            ResultSet delTabs = connDel.getMetaData().getTables("game_data", null, null, new String[]{"TABLE"});


            while (delTabs.next()) {
                statement.executeUpdate("DROP TABLE `" + delTabs.getString(3) + "`;");
            }

            for (HashMap<Integer, String> hMap : myMaps) {
                tableName = hMap.get(-4);
                if (connection.getMetaData().getTables("game_data", null, tableName, null).next()) {

                    statement.executeUpdate("DROP TABLE `" + tableName + "`;");

                    statement.execute("CREATE TABLE `" + tableName + "` (\n" +
                            "  `key` INT NOT NULL,\n" +
                            "  `value` MEDIUMTEXT NULL,\n" +
                            "  PRIMARY KEY (`key`));");

                    for (Map.Entry<Integer, String> integerStringEntry : hMap.entrySet()) {
                        statement.executeUpdate("INSERT INTO `" + tableName + "` VALUES(" + integerStringEntry.getKey() + ",'" + integerStringEntry.getValue() + "') " +
                                "ON DUPLICATE KEY UPDATE value='" + integerStringEntry.getValue() + "';");
                    }
                } else {

                    statement.execute("CREATE TABLE `" + tableName + "` (\n" +
                            "  `key` INT NOT NULL,\n" +
                            "  `value` MEDIUMTEXT NULL,\n" +
                            "  PRIMARY KEY (`key`));");

                    for (Map.Entry<Integer, String> integerStringEntry : hMap.entrySet()) {
                        statement.executeUpdate("INSERT INTO `" + tableName + "` VALUES(" + integerStringEntry.getKey() + ",'" + integerStringEntry.getValue() + "') " +
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

    /**
     * The load method reads the sql database and returns a /List</Hashmap>>
     * which represents the games.
     * @return A list of Hashmaps that represents the saved games in the file.
     */
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
                resSet = statement.executeQuery("select * from `" + myTables.getString(3) + "`");

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

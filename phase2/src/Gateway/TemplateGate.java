package Gateway;

import Interface.LoadSave;

import java.sql.*;
import java.util.*;

public class TemplateGate implements LoadSave {

    /*
     * Similar format as GameGate
     * Each hashmap in the list of hashmaps should have this specific format:
     *  _______________________________________
     * |Key             | Value                |
     * |_______________________________________|
     * |0 (Name)        | "SampleTemplateOne"  |
     * |1 (description) | "SampleOfTemplate"   |
     * |2 (Numchoice)   | "3"                  |
     * |3 (Scheme)      | "Black Times New R." |
     * |_______________________________________|
     *
     * */


    private final String url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/template_data";
    private final String username = "admin";
    private final String password = "BossAcc!";

    public TemplateGate() {
    }

    public void save(List<HashMap<Integer, String>> myMaps){

        String tableName;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            for (HashMap<Integer, String> hMap : myMaps) {
                tableName = hMap.get(0);
                if (connection.getMetaData().getTables("template_data", null, tableName, null).next()) {
                    for (Map.Entry<Integer, String> integerStringEntry : hMap.entrySet()) {
                        statement.executeUpdate("INSERT INTO `" + tableName + "` VALUES(" + integerStringEntry.getKey() + ",'" + integerStringEntry.getValue() + "') " +
                                "ON DUPLICATE KEY UPDATE value='" + integerStringEntry.getValue() + "';");
                    }
                } else {
                    System.out.println("Creating new table.");

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

    public List<HashMap<Integer, String>> load() {

        List<HashMap<Integer, String>> dbMaps = new ArrayList<>();
        HashMap<Integer, String > currMap;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setSchema("game_data");
            Statement statement = connection.createStatement();
            ResultSet resSet;

            ResultSet myTables = connection.getMetaData().getTables("template_data", null, null, new String[]{"TABLE"});

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

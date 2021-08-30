package Gateway;

import java.sql.*;
import java.util.*;

public class UserGateAWS {
    private final String url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/user_data";
    private final String username = "admin";
    private final String password = "BossAcc!";


    public static void main(String[] args) {

        List<HashMap> mapList = new ArrayList<>();

        List<HashMap<Integer, String>> outList;


        for (int i: Arrays.asList(1, 2, 3)){

//            gameData.put(-4, game.getGameName());
//            gameData.put(-3, game.getGameAuthor());
//            gameData.put(-2, String.valueOf(game.getGamePublic()));
//            gameData.put(-1, String.valueOf(game.getchoiceNumLimit()));
//            gameData.put(-5, game.getStyleSheetName());

            HashMap<Integer, String> gameData = new HashMap<>();
            gameData.put(-100, "game");
            gameData.put(-5, "blue");
            gameData.put(-4, "Game " + i);
            gameData.put(-3, "author");
            gameData.put(-2, "true");
            gameData.put(-1, "5");
            gameData.put(0, "#;Dialogue 1");
            mapList.add(gameData);

        }
        UserGate ugo = new UserGate();
        UserGateAWS ugAWS = new UserGateAWS();
        //ugAWS.save(ugo.load());

    }

    public UserGateAWS() {
    }

    public void save(List<HashMap> myMaps){

        String tableName;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            for (HashMap<Integer, List> hMap : myMaps) {
                tableName = "my_users";
                if (connection.getMetaData().getTables("user_data", null, tableName, null).next()) {
                    for (Map.Entry<Integer, List> integerStringEntry : hMap.entrySet()) {
                        statement.executeUpdate("INSERT INTO `" + tableName + "` VALUES(" + integerStringEntry.getKey()
                                + ",'" + integerStringEntry.getValue().get(0)
                                + ",'" + integerStringEntry.getValue().get(1)
                                + ",'" + java.sql.Date.valueOf((String) integerStringEntry.getValue().get(2))
                                + ",'" + java.sql.Date.valueOf((String) integerStringEntry.getValue().get(3))
                                + ",'" + java.sql.Date.valueOf((String) integerStringEntry.getValue().get(4)) + "') " +
                                "ON DUPLICATE KEY UPDATE password='"
                                + integerStringEntry.getValue().get(0) + "', status="
                                + integerStringEntry.getValue().get(1) + "', suspendEnd="
                                + java.sql.Date.valueOf((String) integerStringEntry.getValue().get(2)) + "', startDate="
                                + java.sql.Date.valueOf((String) integerStringEntry.getValue().get(3)) + "', endDate="
                                + java.sql.Date.valueOf((String) integerStringEntry.getValue().get(4)) + "';");
                    }
                } else {
                    System.out.println("Creating new table.");

                    statement.execute("CREATE TABLE `user_data`.`" + tableName + "` (\n" +
                            "  `username` VARCHAR(255) NOT NULL,\n" +
                            "  `password` TINYTEXT NULL,\n" +
                            "  `status` TINYINT NULL,\n" +
                            "  `suspendEnd` DATETIME NULL,\n" +
                            "  `startDate` DATETIME NULL,\n" +
                            "  `endDate` DATETIME NULL,\n" +
                            "  PRIMARY KEY (`username`));");

                    for (Map.Entry<Integer, List> integerStringEntry : hMap.entrySet()) {
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
            connection.setSchema("user_data");
            Statement statement = connection.createStatement();
            ResultSet resSet;

            ResultSet myTables = connection.getMetaData().getTables("user_data", null, null, new String[]{"TABLE"});

            while (myTables.next()) {
                currMap = new HashMap<>();
                resSet = statement.executeQuery("select * from `" + myTables.getString(3) + "`");

                while(resSet.next()) {
//                    currMap.put(resSet.getInt("username"),
////                            Arrays.asList(resSet.getString("password"),
////                                    resSet.getBoolean("status"),
////                                    resSet.getDate("suspendEnd").toLocalDate(),
////                                    resSet.getDate("startDate").toLocalDate(),
////                                    resSet.getDate("endDate").toLocalDate()));
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

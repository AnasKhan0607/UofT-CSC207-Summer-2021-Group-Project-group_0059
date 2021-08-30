package Gateway;

import Interface.GameTemplateLoadSave;

import java.sql.*;
import java.util.*;

public class GameGate implements GameTemplateLoadSave {
    private final String url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/game_data";
    private final String username = "admin";
    private final String password = "BossAcc!";


    public static void main(String[] args) {

        List<HashMap<Integer, String>> mapList = new ArrayList<>();

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
            gameData.put(0, " #;Dialogue 1");
            mapList.add(gameData);

        }
        GameGateOld ggo = new GameGateOld();
        GameGate gg = new GameGate();
        gg.save(mapList);

    }

    public GameGate() {
    }

    public void save(List<HashMap<Integer, String>> myMaps){

        String tableName;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

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

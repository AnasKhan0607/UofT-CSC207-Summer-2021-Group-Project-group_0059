package Gateway;

import java.sql.*;
import java.util.*;

public class DatabaseGate {
    public static void main(String[] args) {
        DatabaseGate db = DatabaseGate.getDatabaseGate();

        List<HashMap<Integer, String>> mapList = new ArrayList<>();

        List<HashMap<Integer, String>> outList;

        HashMap<Integer, String> gameData = new HashMap<>();
        gameData.put(-100, "game");
        gameData.put(-5, "Game 1");
        gameData.put(-4, "DanielLiu");
        gameData.put(-3, "false");
        gameData.put(-2, "4");
        gameData.put(-1, "Chill");
        gameData.put(0, "Dialogue 1");
        mapList.add(gameData);

        HashMap<Integer, String> template_map = new HashMap<>();
        template_map.put(0, "Chill Bro");
        template_map.put(1, "Chill Red Background, Chill Blue Text, Patricks Hand Font, Font Size 20");
        template_map.put(2, "2");
        template_map.put(3, "Chill");
        template_map.put(-100, "template");
        mapList.add(template_map);

        template_map = new HashMap<>();
        template_map.put(0, "Futuristic Template");
        template_map.put(1, "Dark Background, Blue Text, Roboto Font, Font Size 20");
        template_map.put(2, "4");
        template_map.put(3, "Future_Blue");
        template_map.put(-100, "template");
        mapList.add(template_map);

        template_map = new HashMap<>();
        template_map.put(0, "Plain White");
        template_map.put(1, "White Background, Black Text, Sans-Serif Font, Font Size 16");
        template_map.put(2, "3");
        template_map.put(3, "Plain");
        template_map.put(-100, "template");
        mapList.add(template_map);

        db.save(mapList);
        outList = db.load();

        System.out.println((long) outList.size());

        for (HashMap<Integer, String> aMap : outList) {
            for (Map.Entry<Integer, String> mapEntry : aMap.entrySet()) {
                System.out.println(mapEntry.getKey() + ", " + mapEntry.getValue());
            }
        }

    }

    private static DatabaseGate dbGate = new DatabaseGate();
    private List<HashMap<Integer, String>> Games = new ArrayList<>();
    private List<HashMap<Integer, String>> Templates = new ArrayList<>();
    private List<HashMap<Integer, String>> Users = new ArrayList<>();
    private List<HashMap<Integer, String>> Messages = new ArrayList<>();



    public static DatabaseGate getDatabaseGate(){
        return dbGate;
    }

    public void save(List<HashMap<Integer, String>> myMaps){
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
        String  url = "jdbc:mysql://dbphase2.cy2xtdsstzct.us-east-2.rds.amazonaws.com:3306/game_data?currentSchema=wtf";
        String username = "admin";
        String password = "BossAcc!";

        String tableName;

        List<HashMap<Integer, String>> dbMaps = new ArrayList<>();
        HashMap<Integer, String > currMap;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Database!");
            connection.setSchema("game_data");
            System.out.println(connection.getCatalog());
            System.out.println(connection.getMetaData().getTables(null, null, "test1", null).next());
            Statement statement = connection.createStatement();
            ResultSet resSet = statement.executeQuery("select * from test1");
            System.out.println(resSet);


            ResultSet myTables = connection.getMetaData().getTables("game_data", null, null, new String[]{"TABLE"});

            while (myTables.next()) {
                currMap = new HashMap<>();
                System.out.println(myTables.getString(3) + ", " + myTables.getString(1));
                resSet = statement.executeQuery("select * from " + myTables.getString(3));

                while(resSet.next()) {
                    currMap.put(resSet.getInt("key"), resSet.getString("value"));
                }

                if (currMap.get(-100).equals("game")){
                    this.Games.add(currMap);
                }
                else if (currMap.get(-100).equals("template")){
                    this.Templates.add(currMap);
                }
                else if (currMap.get(-100).equals("message")){
                    this.Messages.add(currMap);
                }
                else if (currMap.get(-100).equals("user")){
                    this.Games.add(currMap);
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

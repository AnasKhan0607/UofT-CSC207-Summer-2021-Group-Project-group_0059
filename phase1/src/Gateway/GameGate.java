package Gateway;

import Entity.Game;
import Interface.LoadSave;
import UseCase.GameUseCase;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameGate implements LoadSave {
    /**
     *
     * A Gateway class used to implement <I>LoadSave</I> for loading and saving games.
     * The save method takes a /List</Hashmap>> which represents the games
     * and saves it to a serialized txt file.
     * The load method reads the serialized txt file and returns a /List</Hashmap>>
     * which represents the games.
     *
     * @param args
     */

    public static void main(String[] args) {
//
//        HashMap<Integer, String> gameData = new HashMap<>();
//        gameData.put(-4, "bruh");
//        gameData.put(-3, "le bruh");
//        gameData.put(-2, "false");
//        gameData.put(-1, "4");
//        gameData.put(0, "0");
//        gameData.put(1, "1");
//        gameData.put(2, "2");
//        gameData.put(3, "3");
//        gameData.put(4, "4");
//        gameData.put(5, "5");
//        gameData.put(21, "21");
//        gameData.put(9, "9");
//
//        List<HashMap> gamesData = new ArrayList<>();
//        gamesData.add(gameData);
//        GameGate bruh = new GameGate();
//        bruh.save(gamesData);
//
//        List<HashMap> loadTest = bruh.load();
//
//        HashMap<Integer, String> firstMap = loadTest.get(0);
//
//        System.out.println(firstMap.get(2));
    }

    /*
     * Each hashmap in the list of hashmaps should have this specific format:
     *  _______________________________________
     * |Key             | Value                |
     * |_______________________________________|
     * |-4 (Name)       | "Bruhther's Broments"|
     * |-3 (Author)     | "Le Bruh"            |
     * |-2 (Public)     | "false"              |
     * |-1 (ChoiceLimit)| "4"                  |
     * |0               | "Dialogue 0"         |
     * |1               | "Dialogue 1"         |
     * |2               | "Dialogue 2"         |
     * |...             | ...                  |
     * |_______________________________________|
     * Remember that java hashmap is just dict in python
     * */

    String myPath = new File("").getAbsolutePath() + "\\phase1\\data\\SerialGames.txt";

    // remember to return an arraylist
    public List<HashMap> load() {

        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/

        List<HashMap> myMaps = new ArrayList<>();

        try {
            File gameFile = new File(myPath);
            gameFile.createNewFile();

            FileInputStream fileInput = new FileInputStream(myPath);

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            myMaps = (List<HashMap>) objectInput.readObject();

            objectInput.close();
            fileInput.close();
        }

        catch (IOException obj1) {
            System.out.println("Loading...\n" +
                    "No Saved Games.");
//            obj1.printStackTrace();

        }

        catch (ClassNotFoundException obj2) {
            System.out.println("Class not found!");
//            obj2.printStackTrace();

        }

        return myMaps;
    }

    // an arraylist will be passed here
    public void save(List<HashMap> myMap){
        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/
        try {
            FileOutputStream myFileOutStream
                    = new FileOutputStream(myPath);

            ObjectOutputStream myObjectOutStream
                    = new ObjectOutputStream(myFileOutStream);

            myObjectOutStream.writeObject(myMap);

            // closing FileOutputStream and
            // ObjectOutputStream
            myObjectOutStream.close();
            myFileOutStream.close();
        }
        catch (IOException e) {
            System.out.println(e);
//            e.printStackTrace();
        }
    }

}

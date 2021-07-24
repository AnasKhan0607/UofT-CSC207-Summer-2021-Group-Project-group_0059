package Gateway;

import Interface.SaveLoadGame;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GameGateway implements SaveLoadGame {

    String myPath = new File("").getAbsolutePath();

    /*
    * Each hashmap in the list of hashmaps should have this specific format:
    *  ___________________________________
    * |Key         | Value                |
    * |___________________________________|
    * |Name        | "Bruhther's Broments"|
    * |Author      | "Le Bruh"            |
    * |Public      | false                |
    * |ChoiceLimit | 4                    |
    * |0           | "Dialogue 0"         |
    * |1           | "Dialogue 1"         |
    * |2           | "Dialogue 2"         |
    * |...         | ...                  |
    * |___________________________________|
    * Remember that java hashmap is just dict in python
     * */

    // remember to return an arraylist
    public List<HashMap> load_games() {

        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/

        List<HashMap> gameMaps = null;

        try {
            FileInputStream fileInput = new FileInputStream(
                    myPath + "\\phase1\\data\\SerializedMaps.txt");

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            gameMaps = (List<HashMap>) objectInput.readObject();

            objectInput.close();
            fileInput.close();
        }

        catch (IOException obj1) {
            obj1.printStackTrace();

        }

        catch (ClassNotFoundException obj2) {
            System.out.println("Class not found");
            obj2.printStackTrace();

        }

        return gameMaps;
    }

    // an arraylist will be passed here
    public void save_games(List<HashMap> gameTable){
        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/
        try {
            FileOutputStream myFileOutStream
                    = new FileOutputStream(
                    myPath + "\\phase1\\data\\SerializedMaps.txt");

            ObjectOutputStream myObjectOutStream
                    = new ObjectOutputStream(myFileOutStream);

            myObjectOutStream.writeObject(gameTable);

            // closing FileOutputStream and
            // ObjectOutputStream
            myObjectOutStream.close();
            myFileOutStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}



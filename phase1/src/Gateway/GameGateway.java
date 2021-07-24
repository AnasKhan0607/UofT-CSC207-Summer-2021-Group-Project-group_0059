package Gateway;

import Interface.SaveLoadGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

        CsvToTable CTT = new CsvToTable();

        return null;
    }

    // an arraylist will be passed here
    public void save_games(List<HashMap> gameTable){

    }

}



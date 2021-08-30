package Gateway;
import Interface.GameTemplateLoadSave;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * A Gateway class used to implement <I>GameTemplateLoadSave</I> for loading and saving games.
 *
 */

public class GameGateOld implements GameTemplateLoadSave {

    /*
     * Each hashmap in the list of hashmaps should have this specific format:
     *  _______________________________________
     * |Key             | Value                |
     * |_______________________________________|
     * |-4 (Name)       | "Bruhther's Broments"|
     * |-3 (Author)     | "Le Bruh"            |
     * |-2 (Public)     | "false"              |
     * |-1 (ChoiceLimit)| "4"                  |
     * |-5 (Stylesheet) | "Chill"              |
     * |0               | "Dialogue 0"         |
     * |1               | "Dialogue 1"         |
     * |2               | "Dialogue 2"         |
     * |...             | ...                  |
     * |_______________________________________|
     *
     * */
    String myPath;

    /**
     * The gateway's constructor that gets a relative path for the current directory.
     */

    public GameGateOld(){
        this.myPath = findSaveGameFile(System.getProperty("user.dir"));
    }

    private String findSaveGameFile(String filePath){
        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        String foundPath = "";

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.getAbsolutePath().contains("data") && child.getAbsolutePath().contains("GameData.txt")){
                    return child.getAbsolutePath();
                }
                else{
                    String path = findSaveGameFile(child.getAbsolutePath());
                    if (!path.equals("")){
                        return path;
                    }
                }
            }
        }
        return foundPath;
    }

    /**
     * The load method reads the serialized txt file and returns a /List</Hashmap>>
     * which represents the games.
     * @return A list of Hashmaps that represents the saved games in the file.
     */

    public List<HashMap<Integer, String>> load() {

        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/

        List<HashMap<Integer, String>> myMaps = new ArrayList<>();

        try {
//            File gameFile = new File(myPath);
//            gameFile.createNewFile();

            FileInputStream fileInput = new FileInputStream(myPath);

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            myMaps = (List<HashMap<Integer, String>>) objectInput.readObject();

            objectInput.close();
            fileInput.close();
        }

        catch (IOException obj1) {
            System.out.println("Loading...\n" +
                    "No Saved Games.");

        }

        catch (ClassNotFoundException obj2) {
            System.out.println("Class not found!");

        }

        return myMaps;
    }

    /**
     * The save method takes a /List</Hashmap>> which represents the games
     * and saves it to a serialized txt file.
     * @param myMap The list of Hashmaps to be saved.
     */

    public void save(List<HashMap<Integer, String>> myMap){
        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/
        try {
            FileOutputStream myFileOutStream
                    = new FileOutputStream(myPath);

            ObjectOutputStream myObjectOutStream
                    = new ObjectOutputStream(myFileOutStream);

            myObjectOutStream.writeObject(myMap);

            myObjectOutStream.close();
            myFileOutStream.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}


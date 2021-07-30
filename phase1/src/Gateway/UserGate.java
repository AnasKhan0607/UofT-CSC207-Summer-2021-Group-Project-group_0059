package Gateway;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class UserGate {

    /**
     *
     * A Gateway class used to implement <I>LoadSave</I> for loading and saving all the users.
     * The save method takes a /List</Hashmap>> which represents the users
     * and saves it to a serialized txt file.
     * The load method reads the serialized txt file and returns a /List</Hashmap>>
     * which represents the users.
     *
     * @param args
     */

    public static void main(String[] args) {

        HashMap<Integer, String> gameData = new HashMap<>();
        gameData.put(-4, "bruh");
        gameData.put(-3, "le bruh");
        gameData.put(-2, "false");
        gameData.put(-1, "4");
        gameData.put(0, "0");
        gameData.put(1, "1");
        gameData.put(2, "2");
        gameData.put(3, "3");
        gameData.put(4, "4");
        gameData.put(5, "5");
        gameData.put(21, "21");
        gameData.put(9, "9");

        List<HashMap> gamesData = new ArrayList<>();
        gamesData.add(gameData);
        UserGate bruh = new UserGate();
        bruh.save(gamesData);

        List<HashMap> loadTest = bruh.load();

        HashMap<Integer, String> firstMap = loadTest.get(0);

        System.out.println(firstMap.get(2));
        System.out.println(new File("").getAbsolutePath());


    }


    // remember to return an arraylist
    String myPath = new File("").getAbsolutePath() + "\\data\\SerialUser.txt";

    // remember to return an arraylist
    public List<HashMap> load() {

        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/

        List<HashMap> myMaps = null;

        try {
            FileInputStream fileInput = new FileInputStream(myPath);

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            myMaps = (List<HashMap>) objectInput.readObject();

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
            e.printStackTrace();
        }
    }
}

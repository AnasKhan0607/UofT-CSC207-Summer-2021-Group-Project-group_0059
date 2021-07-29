package Gateway;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserGate {
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



    String myPath = new File("").getAbsolutePath() + "\\phase1\\data\\SerialUser.txt";

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

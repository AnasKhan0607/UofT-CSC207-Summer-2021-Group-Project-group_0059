package Gateway;

import Interface.LoadSave;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TemplateGate implements LoadSave {

    /**
     *
     * A Gateway class used to implement <I>LoadSave</I> for loading and saving users.
     * The save method takes a /List</Hashmap>> which represents the games
     * and saves it to a serialized txt file.
     * The load method reads the serialized txt file and returns a /List</Hashmap>>
     * which represents the games.
     *
     * @param args
     */

    public static void main(String[] args) {

        HashMap<Integer, String> TemplateData = new HashMap<>();
        TemplateData.put(0, "SampleTemplateOne");
        TemplateData.put(1, "SampleOfTemplateDescriptionOne");
        TemplateData.put(2, "3");

        List<HashMap> TemplatesData = new ArrayList<>();
        TemplatesData.add(TemplateData);
        TemplateGate bruh = new TemplateGate();
        bruh.save(TemplatesData);

        List<HashMap> loadTest = bruh.load();

        HashMap<Integer, String> firstMap = loadTest.get(0);

        System.out.println(firstMap.get(1));

    }

    /*
     * Similar format as GameGate
     * Each hashmap in the list of hashmaps should have this specific format:
     *  _______________________________________
     * |Key             | Value                |
     * |_______________________________________|
     * |0 (Name)        | "SampleTemplateOne"  |
     * |1 (description) | "SampleOfTemplate"   |
     * |2 (Numchoice)   | "3"                  |
     * |_______________________________________|
     * Remember that java hashmap is just dict in python
     * */

    // remember to return an arraylist
    String myPath = new File("").getAbsolutePath() + "//phase1//data//SerialTemplates.txt";

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
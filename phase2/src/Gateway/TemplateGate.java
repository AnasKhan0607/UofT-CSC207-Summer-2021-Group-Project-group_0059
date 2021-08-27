package Gateway;

import Interface.LoadSave;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * A Gateway class used to implement <I>LoadSave</I> for loading and saving templates.
 *
 */

public class TemplateGate implements LoadSave {

    /*
     * Similar format as GameGate
     * Each hashmap in the list of hashmaps should have this specific format:
     *  _______________________________________
     * |Key             | Value                |
     * |_______________________________________|
     * |0 (Name)        | "SampleTemplateOne"  |
     * |1 (description) | "SampleOfTemplate"   |
     * |2 (Numchoice)   | "3"                  |
     * |3 (Scheme)      | "Black Times New R." |
     * |_______________________________________|
     * Remember that java hashmap is just dict in python
     * */

    String myPath;

    /**
     * The gateway's constructor that gets a relative path for the current directory.
     */

    public TemplateGate(){
        this.myPath = findSaveTemplateFile(System.getProperty("user.dir"));
    }

    private String findSaveTemplateFile(String filePath){
        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        String foundPath = "";

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.getAbsolutePath().contains("data") && child.getAbsolutePath().contains("TemplateData.txt")){
                    return child.getAbsolutePath();
                }
                else{
                    String path = findSaveTemplateFile(child.getAbsolutePath());
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
     * which represents the templates.
     * @return A list of Hashmaps that represents the saved templates in the file.
     */

    public List<HashMap> load() {

        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/

        List<HashMap> myMaps = new ArrayList<>();

        try {
            File templateFile = new File(myPath);
            templateFile.createNewFile();

            FileInputStream fileInput = new FileInputStream(myPath);

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            myMaps = (List<HashMap>) objectInput.readObject();

            objectInput.close();
            fileInput.close();
        }

        catch (IOException obj1) {
            System.out.println("Loading...\n" +
                    "No Saved Templates.");

        }

        catch (ClassNotFoundException obj2) {
            System.out.println("Class not found");

        }

        return myMaps;
    }

    /**
     * The save method takes a /List</Hashmap>> which represents the templates
     * and saves it to a serialized txt file.
     * @param myMap The list of Hashmaps to be saved.
     */

    // an arraylist will be passed here
    public void save(List<HashMap> myMap){
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
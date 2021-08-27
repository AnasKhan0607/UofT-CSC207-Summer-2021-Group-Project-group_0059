package Gateway;

import Entity.Template;
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
    public static void main(String[] args) {
        List<HashMap> template_maps = new ArrayList<>();
        HashMap<Integer, String> template_map = new HashMap<>();
        template_map.put(0, "Futuristic Template");
        template_map.put(1, "Dark Background, Blue Text, Roboto Font, Font Size 20");
        template_map.put(2, "4");
        template_map.put(3, "Future_Blue");
        TemplateGate t = new TemplateGate();
        template_maps.add(template_map);

        template_map = new HashMap<>();
        template_map.put(0, "Plain White");
        template_map.put(1, "White Background, Black Text, Sans-Serif Font, Font Size 16");
        template_map.put(2, "3");
        template_map.put(3, "Plain");
        template_maps.add(template_map);

        template_map = new HashMap<>();
        template_map.put(0, "Chill Bro");
        template_map.put(1, "Chill Red Background, Chill Blue Text, Patricks Hand Font, Font Size 20");
        template_map.put(2, "2");
        template_map.put(3, "Chill");
        template_maps.add(template_map);

        t.save(template_maps);
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
//            obj2.printStackTrace();

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
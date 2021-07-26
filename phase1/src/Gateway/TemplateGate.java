package Gateway;

import Interface.TemplateSaveLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TemplateGate implements TemplateSaveLoader {

    String myPath = new File("").getAbsolutePath();

    /*
     * Similar format as GameGateway
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
    public List<HashMap> load_templates() {

        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/

        List<HashMap> templatesMaps = null;

        try {
            FileInputStream fileInput = new FileInputStream(
                    myPath + "\\phase1\\data\\SerializedMaps.txt");

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            templatesMaps = (List<HashMap>) objectInput.readObject();

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

        return templatesMaps;
    }

    // an arraylist will be passed here
    public void save_templates(List<HashMap> templatesTable){
        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/
        try {
            FileOutputStream myFileOutStream
                    = new FileOutputStream(
                    myPath + "\\phase1\\data\\SerializedMaps.txt");

            ObjectOutputStream myObjectOutStream
                    = new ObjectOutputStream(myFileOutStream);

            myObjectOutStream.writeObject(templatesTable);

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
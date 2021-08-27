package Gateway;

import Interface.LoadSave;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageGate implements LoadSave {
    String myPath;

    /**
     * The gateway's constructor that gets a relative path for the current directory.
     */

    public MessageGate(){
        this.myPath = findSaveMessageFile(System.getProperty("user.dir"));
    }

    private String findSaveMessageFile(String filePath){
        File dir = new File(filePath);
        File[] directoryListing = dir.listFiles();
        String foundPath = "";

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.getAbsolutePath().contains("data") && child.getAbsolutePath().contains("MessageData.txt")){
                    return child.getAbsolutePath();
                }
                else{
                    String path = findSaveMessageFile(child.getAbsolutePath());
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
     * which represents the users.
     * @return A list of Hashmaps that represents the saved users in the file.
     */

    public List<HashMap> load() {

        // Source: https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/

        List<HashMap> myMaps = new ArrayList<>();

        try {
//            File userFile = new File(myPath);
//            userFile.createNewFile();

            FileInputStream fileInput = new FileInputStream(myPath);

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            myMaps = (List<HashMap>) objectInput.readObject();

            objectInput.close();
            fileInput.close();
        }

        catch (IOException obj1) {
            myMaps.add(new HashMap<String, String>());
            System.out.println("Loading...\n" +
                    "No Saved Messages.");
//            obj1.printStackTrace();
        }

        catch (ClassNotFoundException obj2) {
            System.out.println("Class not found");
//            obj2.printStackTrace();
        }

        return myMaps;
    }

    /**
     * The save method takes a /List</Hashmap>> which represents the users
     * and saves it to a serialized txt file.
     * @param myMap The list of Hashmaps to be saved.
     */

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

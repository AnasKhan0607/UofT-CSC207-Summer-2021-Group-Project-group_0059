package Interface;

import java.util.HashMap;
import java.util.List;

/**
 * A Gateway class used to implement <I>LoadSave</I> for loading and saving games.
 */

public interface LoadSave {

    /**
     * The load method reads the serialized txt file and returns a /List</Hashmap>>
     * which represents the saved object.
     * @return A list of Hashmaps that represents the saved objects in the file.
     */

    List<HashMap> load();

    /**
     * The save method takes a /List</Hashmap>> which represents the object
     * and saves it to a serialized txt file.
     * @param myMap list of Hashmaps to be saved.
     */

    void save(List<HashMap> myMap);

}

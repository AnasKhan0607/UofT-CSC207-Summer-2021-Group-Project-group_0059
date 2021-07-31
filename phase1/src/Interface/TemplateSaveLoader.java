package Interface;

import java.util.HashMap;
import java.util.List;

/**
 * Interface for methods to load data from file and save data to file for Template.
 */
public interface TemplateSaveLoader {

    /**
     * The load method reads the serialized txt file and returns a /List</Hashmap>>
     * which represents the templates.
     * @return A list of Hashmaps that represents the saved templates in the file.
     */
    List<HashMap<Integer, String>> load_templates();

    /**
     * The save method takes a /List</Hashmap>> which represents the templates
     * and saves it to a serialized txt file.
     * @param TemplateTable The list of Hashmaps to be saved.
     */
    void save_templates(List<HashMap<Integer, String>> TemplateTable);
}

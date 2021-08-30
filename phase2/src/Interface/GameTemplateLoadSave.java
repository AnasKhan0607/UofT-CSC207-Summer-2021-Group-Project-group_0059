package Interface;

import java.util.HashMap;
import java.util.List;

/**
 * The interface for the Gateway classes for the Game and Template data
 */
public interface GameTemplateLoadSave {

    List<HashMap<Integer, String>> load();

    void save(List<HashMap<Integer, String>> table);

}

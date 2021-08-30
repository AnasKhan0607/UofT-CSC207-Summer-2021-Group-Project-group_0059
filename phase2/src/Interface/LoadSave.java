package Interface;

import java.util.HashMap;
import java.util.List;

/**
 * The interface for the Gateway classes for the User and Message data
 */
public interface LoadSave {

    List<HashMap<Integer, String>> load();

    void save(List<HashMap<Integer, String>> gameTable);

}

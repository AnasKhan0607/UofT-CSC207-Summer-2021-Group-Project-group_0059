package Interface;

import java.util.HashMap;
import java.util.List;

public interface UserMessageLoadSave {

    List<HashMap<String, List<Object>>> load();

    void save(List<HashMap<String, List<Object>>> table);
}

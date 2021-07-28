package Interface;

import java.util.HashMap;
import java.util.List;

public interface LoadSave {

    List<HashMap> load();

    void save(List<HashMap> gameTable);

}

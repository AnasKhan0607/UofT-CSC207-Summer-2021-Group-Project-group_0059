package Interface;

import java.util.HashMap;
import java.util.List;

public interface SaveLoadGame {
    List<HashMap> load_games();
    void save_games(List<HashMap> gameTable);
}

package Interface;

import java.util.HashMap;
import java.util.List;

public interface TemplateSaveLoader {
    List<HashMap<Integer, String>> load_templates();
    void save_templates(List<HashMap<Integer, String>> TemplateTable);
}

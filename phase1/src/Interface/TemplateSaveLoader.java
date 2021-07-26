package Interface;

import java.util.HashMap;
import java.util.List;

public interface TemplateSaveLoader {
    List<HashMap> load_templates();
    void save_templates(List<HashMap> TemplateTable);
}

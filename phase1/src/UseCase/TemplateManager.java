package UseCase;
import Entity.Template;

import java.util.ArrayList;
import java.util.HashMap;

public class TemplateManager {
    private ArrayList<Template> templates;

    public ArrayList<HashMap<String, Template>> All_templates() {
        ArrayList<HashMap<String, Template>> all_templates = new ArrayList<>();
        for (Template template : templates) {
            HashMap<String, Template> hashMap = new HashMap<>();
            hashMap.put(template.getTemplatename(), template);
            all_templates.add(hashMap);
        }
        return all_templates;
    }

    public Template Find_template(String name){
        int counter;
        for (counter = 0; counter < templates.size(); counter++) {
            if (templates.get(counter).getTemplatename().equals(name)) {
                return templates.get(counter);
            }
        }
        return null;
    }
}

package UseCase;
import Entity.Template;
import Gateway.TemplateGate;
import Interface.TemplateSaveLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TemplateManager {
    private ArrayList<Template> templates;

    public TemplateManager(){
        this.templates = new ArrayList<>();
        TemplateGate t = new TemplateGate();

        List<HashMap> template_maps = t.load();
        for (HashMap<Integer, String> template_map : template_maps){
            Template a = new Template(template_map.get(0));
            a.setTemplatename(template_map.get(0));
            a.setDescription(template_map.get(1));
            a.setNumchoice(Integer.parseInt(template_map.get(2)));
            this.templates.add(a);
        }
    }

    public ArrayList<Template> getTemplates() {
        return this.templates;
    }

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

package UseCase;
import Entity.Template;
import Gateway.TemplateGate;

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

    public void Save_changes(){
        List<HashMap> template_maps = new ArrayList<>();
        TemplateGate t = new TemplateGate();
        for (Template template : this.templates){
            HashMap<Integer, String> template_map = new HashMap<>();
            template_map.put(0, template.getTemplatename());
            template_map.put(1, template.getDescription());
            template_map.put(2, Integer.toString(template.getNumchoice()));
            template_maps.add(template_map);
        }
        t.save(template_maps);
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

    public ArrayList<String> Template_names() {
        ArrayList<String> all_templates = new ArrayList<>();
        for (Template template : templates) {
            all_templates.add(template.getTemplatename());
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
    public void setNewName(String name, String new_){
        Find_template(name).setTemplatename(new_);
    }
    public void setNewDescription(String name, String new_){
        Find_template(name).setDescription(new_);
    }
    public void setNewChoicesNum(String name, Integer new_){
        Find_template(name).setNumchoice(new_);
    }
    public Integer getNumChoices(String name){
        return Find_template(name).getNumchoice();
    }
    public String getDescription(String name){
        return Find_template(name).getDescription();
    }
}

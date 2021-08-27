package UseCase;
import Entity.Template;
import Gateway.TemplateGate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The use case class for the Template.
 * @author Anas, Tian
 */
public class TemplateManager {
    private ArrayList<Template> templates;

    /**
     * initializes template gateway to create a list of Templates.
     */
    public TemplateManager(){
        this.templates = new ArrayList<>();
        TemplateGate t = new TemplateGate();

        List<HashMap> template_maps = t.load();
        for (HashMap<Integer, String> template_map : template_maps){
            Template a = new Template(template_map.get(0));
            a.setTemplatename(template_map.get(0));
            a.setDescription(template_map.get(1));
            a.setNumchoice(Integer.parseInt(template_map.get(2)));
            a.setScheme(template_map.get(3));
            this.templates.add(a);
        }
    }

    /**
     * Saves the updated list of templates into the file.
     */
    public void Save_changes(){
        List<HashMap> template_maps = new ArrayList<>();
        TemplateGate t = new TemplateGate();
        for (Template template : this.templates){
            HashMap<Integer, String> template_map = new HashMap<>();
            template_map.put(0, template.getTemplatename());
            template_map.put(1, template.getDescription());
            template_map.put(2, Integer.toString(template.getNumchoice()));
            template_map.put(3, template.getScheme());
            template_maps.add(template_map);
        }
        t.save(template_maps);
    }

    /**
     * geter for list of templates
     * @return private list of templates
     */
    public ArrayList<Template> getTemplates() {
        return this.templates;
    }

    /**
     * Creates an array of hashmaps with names and corresponding Templates.
     * @return Array of Hashmaps containing name and corresponding Template.
     */
    public ArrayList<HashMap<String, Template>> All_templates() {
        ArrayList<HashMap<String, Template>> all_templates = new ArrayList<>();
        for (Template template : templates) {
            HashMap<String, Template> hashMap = new HashMap<>();
            hashMap.put(template.getTemplatename(), template);
            all_templates.add(hashMap);
        }
        return all_templates;
    }

    /**
     * creates array of all template names.
     * @return Returns Array of all template names.
     */
    public ArrayList<String> Template_names() {
        ArrayList<String> all_templates = new ArrayList<>();
        for (Template template : templates) {
            all_templates.add(template.getTemplatename());
        }
        return all_templates;
    }

    /**
     * Finds Template according to name.
     * @param name of Template
     * @return Template or Null
     */
    public Template Find_template(String name){
        int counter;
        for (counter = 0; counter < templates.size(); counter++) {
            if (templates.get(counter).getTemplatename().equals(name)) {
                return templates.get(counter);
            }
        }
        return null;
    }

    /**
     * Changes name of template
     * @param name, new_ Strings of old name and new name of Template
     */
    public void setNewName(String name, String new_){
        Find_template(name).setTemplatename(new_);
    }
    /**
     * Changes Description of template
     * @param name, new_ Strings of name and new Description of template
     */
    public void setNewDescription(String name, String new_){
        Find_template(name).setDescription(new_);
    }
    /**
     * Changes Scheme of template
     * @param name, new_ Strings of scheme and new scheme of template
     */
    public void setNewScheme(String name, String new_){
        Find_template(name).setScheme(new_);
    }
    /**
     * Changes number of choices of template
     * @param name, new_ String of name and Integer of new number of choices of template
     */
    public void setNewChoicesNum(String name, Integer new_){
        Find_template(name).setNumchoice(new_);
    }
    /**
     * Returns Templates number of choices.
     * @param name of template
     * @return A Integer for num of choices
     */
    public Integer getNumChoices(String name){
        return Find_template(name).getNumchoice();
    }
    /**
     * Returns Description of Template
     * @param name of Template
     * @return A string containing the description
     */
    public String getDescription(String name){
        return Find_template(name).getDescription();
    }
    /**
     * Returns scheme of Template
     * @param name of Template
     * @return A string containing the scheme
     */
    public String getScheme(String name){
        return Find_template(name).getScheme();
    }
}

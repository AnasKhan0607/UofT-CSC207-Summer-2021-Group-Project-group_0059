package Presenter;

import Entity.Template;
import UseCase.TemplateManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The presenter class for displaying templates in a user-friendly manner.
 */

public class TemplatePresenter {

    /**
     * Displays a list of all the available templates in the template UseCase class.
     * @param manager The template use case class </TemplateManager>
     */

    public static void display_templates(TemplateManager manager){
        System.out.println("List Of Available Templates:");
        for (String template_name : manager.Template_names()) {
            System.out.println("Template Name: " + template_name);
            System.out.println("Template Description: " + manager.getDescription(template_name));
            System.out.println("Template # of choices: " + manager.getNumChoices(template_name));
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        }
    }

    /**
     * Displays the desired template's information: name, description, and maximum number of choices.
     * @param manager The template use case class </TemplateManager>
     */

    public static void display_template(TemplateManager manager, String name){
        System.out.println("Template Name: " + name);
        System.out.println("Template Description: " + manager.getDescription(name));
        System.out.println("Template # of choices: " + manager.getNumChoices(name));
    }

    /**
     * Confirms that the user has selected a template and they will be directed to it.
     */

    public static void selected_template(Template template) {
        System.out.println("You have selected " + template.getTemplatename() + " template");
        System.out.println("Redirecting...");
    }


}

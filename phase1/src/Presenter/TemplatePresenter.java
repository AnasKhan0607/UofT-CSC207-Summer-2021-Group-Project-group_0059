package Presenter;

import Entity.Template;
import UseCase.TemplateManager;

import java.util.ArrayList;
import java.util.HashMap;

public class TemplatePresenter {
    public static void display_templates(TemplateManager manager){
        System.out.println("List Of Available Templates:");
        int counter;
        for (String template_name : manager.Template_names()) {
            System.out.println("Template Name: " + template_name);
            System.out.println("Template Description: " + manager.getDescription(template_name));
            System.out.println("Template # of choices: " + manager.getNumChoices(template_name));
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        }
    }
    public static void display_template(TemplateManager manager, String name){
        System.out.println("Template Name: " + name);
        System.out.println("Template Description: " + manager.getDescription(name));
        System.out.println("Template # of choices: " + manager.getNumChoices(name));
    }
    public static void chose_template_for_game(TemplateManager manager) {
        System.out.println("Which template would you like to chose to make a game with?");
        display_templates(manager);
    }
    public static void selected_template(Template template) {
        System.out.println("You have selected " + template.getTemplatename() + " template");
        System.out.println("Redirecting...");
    }


}

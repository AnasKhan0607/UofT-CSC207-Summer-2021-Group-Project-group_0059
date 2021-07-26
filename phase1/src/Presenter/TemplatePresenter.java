package Presenter;

import Entity.Template;
import UseCase.TemplateManager;

import java.util.ArrayList;

public class TemplatePresenter {
    public static void display_templates(ArrayList<Template> templates){
        System.out.println("List Of Available Templates:");
        int counter;
        for (counter = 0; counter < templates.size(); counter++) {
            System.out.println("Template Name: " + templates.get(counter).getTemplatename());
            System.out.println("Template Description: " + templates.get(counter).getDescription());
            System.out.println("Template # of choices: " + templates.get(counter).getNumchoice());
        }
    }
    public static void display_template(Template template){
        System.out.println("Template Name: " + template.getTemplatename());
        System.out.println("Template Description: " + template.getDescription());
        System.out.println("Template # of choices: " + template.getNumchoice());
    }
    public static void chose_template_for_game(ArrayList<Template> templates) {
        System.out.println("Which template would you like to chose to make a game with?");
        display_templates(templates);
    }
    public static void selected_template(Template template) {
        System.out.println("You have selected " + template.getTemplatename() + " template");
        System.out.println("Redirecting...");
    }


}

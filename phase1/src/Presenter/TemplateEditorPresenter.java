package Presenter;

import Entity.Template;
import Presenter.TemplatePresenter;
import UseCase.TemplateManager;

import java.util.ArrayList;

public class TemplateEditorPresenter {

    public static void chose_template_to_edit(TemplateManager manager) {
        System.out.println("Which template would you like to choose? Enter the Name or -1 to Cancel.");
        TemplatePresenter.display_templates(manager);
    }

    public static void edit_template() {
        System.out.println("What would you like to change in the template.");
        System.out.println("Type -1 to go back.");
        System.out.println("Type 0 to change Name.");
        System.out.println("Type 1 to change Description.");
        System.out.println("Type 2 to change Number of Choices.");
    }

    public static void change_name() {
        System.out.println("What would you like to change the name of the Template to.(Please do not use -1)");
    }

    public static void change_description() {
        System.out.println("What would you like to change the description of the Template to.");
    }

    public static void change_choices() {
        System.out.println("What would you like to change the number of choices of the Template to.");
    }

    public static void try_agin() {
        System.out.println("No such Template. Please enter a Template name provided.");
    }
    public static void try_agin_option() {
        System.out.println("No such option. Please enter an option provided.");
    }
    public static void try_agin_name() {
        System.out.println("Please try another name.");
    }



}

package Presenter;

import Entity.Template;
import Presenter.TemplatePresenter;

import java.util.ArrayList;

public class TemplateEditorPresenter {

    public static void chose_template_to_edit(ArrayList<Template> templates) {
        System.out.println("Which template would you like to chose to edit? Enter the Name.");
        TemplatePresenter.display_templates(templates);
    }

    public static void edit_template() {
        System.out.println("What would you like to change in the template.");
    }

    public static void try_agin() {
        System.out.println("No such Template. Please enter a Template name provided.");
    }

}

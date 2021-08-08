package Presenter;

import UseCase.TemplateManager;

/**
 * The presenter class for editing templates. Displays the corresponding messages for different actions.
 */

public class TemplateEditorPresenter {

    /**
     * Allows the user to choose a template to edit.
     * @param manager The <TemplateManager> use case class.
     */

    public static void chose_template_to_edit(TemplateManager manager) {
        System.out.println("Which template would you like to choose? Enter the Name or -1 to Cancel.");
        TemplatePresenter.display_templates(manager);
    }

    /**
     * Displays the options for editing a template.
     */

    public static void edit_template() {
        System.out.println("What would you like to change in the template.");
        System.out.println("Type -1 to go back.");
        System.out.println("Type 0 to change Name.");
        System.out.println("Type 1 to change Description.");
        System.out.println("Type 2 to change Number of Choices.");
    }

    /**
     * Message corresponding to changing the name of a template.
     */

    public static void change_name() {
        System.out.println("What would you like to change the name of the Template to.(Please do not use -1)");
    }

    /**
     * Message corresponding to changing the description of a template
     */

    public static void change_description() {
        System.out.println("What would you like to change the description of the Template to.");
    }

    /**
     * Message corresponding to changing the choices of a template
     */

    public static void change_choices() {
        System.out.println("What would you like to change the number of choices of the Template to.");
    }

    /**
     * Message corresponding to the template not being found
     */

    public static void try_agin() {
        System.out.println("No such Template. Please enter a Template name provided.");
    }

    /**
     * Message corresponding to the selected option not being found
     */

    public static void try_agin_option() {
        System.out.println("No such option. Please enter an option provided.");
    }

    /**
     * Message corresponding to the name not being found
     */

    public static void try_agin_name() {
        System.out.println("Please try another name.");
    }



}

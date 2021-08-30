package Controller;


import Interface.TemplateData;
import Presenter.GamePresenter;
import UseCase.TemplateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * the controller class that interacts with Userinputs from Admin User wanting to edit a Template or chose Template.
 */
public class TemplateEditorController implements TemplateData {

    private TemplateManager templates;

    /**
     * the constructor for this controller class
     * Initializes a new TemplateManger object and assigns to private class variable templates.
     */
    public TemplateEditorController() {
        this.templates = new TemplateManager();
    }

    /**
     * the constructor for this controller class
     *
     * @return returns chosen template's number of choices or -1 when template isnt chosen.
     */
    public ArrayList<Object> chooseTemplate() {
        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> choices = new ArrayList<>();
        choices.add("View Template List");
        choices.add("Choose Template");
        choices.add("EXIT");


        for (; ; ) {
            int choice = gamePresenter.displayChoices(this, choices, "What do you want to do?");
            if (choice == 0) {
                String templatelist = new String();
                for (String template_name : templates.Template_names()) {
                    templatelist = templatelist + "Template Name: " + template_name + "\n" + "Description: " + templates.getDescription(template_name)
                            + "\n" + "Number of Choices: " + templates.getNumChoices(template_name) + "\n" + "Scheme: " + templates.getScheme(template_name) + "\n" + "\n" + "\n";
                }
                gamePresenter.displayTextScene(this, "BACK", "List Of Available Templates:" + "\n" + "\n" + templatelist);
            } else if (choice == 1) {
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add("Please enter the name of the template you want to chose:");
                List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Choose Template.");
                String templatename = userinputs.get(0);
                if (!this.templates.getTemplates().contains(this.templates.Find_template(templatename))) {
                    gamePresenter.displayTextScene(this, "BACK", "Sorry, but we do not have such template, please check the template name and try again.");
                }
                else {
                    ArrayList<Object> game_instructions = new ArrayList<>();
                    game_instructions.add(templates.getNumChoices(templatename));
                    game_instructions.add(templates.getScheme(templatename));
                    return game_instructions;
                }

            }
            else {
                break;
            }
        }
        return new ArrayList<>();
    }


    /**
     * the main method that is run to accept user inputs and take them through the whole editing process.
     */
    public void run() {
        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> choices = new ArrayList<>();
        choices.add("View Template List");
        choices.add("Choose Template");
        choices.add("EXIT");
        for (; ; ) {
            int choice = gamePresenter.displayChoices(this, choices, "Hello, what do you want to do?");
            if (choice == 0) {
                String templatelist = new String();
                for (String template_name : templates.Template_names()) {
                    templatelist = templatelist + "Template Name: " + template_name + "\n" + "Description: " + templates.getDescription(template_name)
                            + "\n" + "Number of Choices: " + templates.getNumChoices(template_name) + "\n" + "Scheme: " + templates.getScheme(template_name) + "\n" + "\n" + "\n";
                }
                gamePresenter.displayTextScene(this, "BACK", "List Of Available Templates:" + "\n" + "\n" + templatelist);
            } else if (choice == 1) {
                ArrayList<String> inputs = new ArrayList<>();
                inputs.add("Please enter the name of the template you want to edit:");
                List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Choose Template.");
                String templatename = userinputs.get(0);
                if (!this.templates.getTemplates().contains(this.templates.Find_template(templatename))) {
                    gamePresenter.displayTextScene(this, "BACK", "Sorry, but we do not have such template, please check the template name and try again.");
                } else {
                    ArrayList<String> editchoices = new ArrayList<>();
                    editchoices.add("Change the name of the template");
                    editchoices.add("Change the description of the template");
                    editchoices.add("Change the number of choice of the template");
                    editchoices.add("Change the scheme of the template");
                    editchoices.add("EXIT");
                    for (; ; ) {
                        int edit_choice = gamePresenter.displayChoices(this, editchoices, "What would you like to change in the template?");
                        if (edit_choice == 4) {
                            break;
                        }
                        if (edit_choice == 0) {
                            inputs = new ArrayList<>();
                            inputs.add("Please enter the new name:");
                            List<String> newname = gamePresenter.displayInputs(this, inputs, "Please enter the new name.");
                            this.templates.setNewName(templatename, newname.get(0));
                            gamePresenter.displayTextScene(this, "BACK", "The name is successfully changed.");
                            break;
                        }
                        if (edit_choice == 1) {
                            List<String> newdescription = gamePresenter.displayInputs(this, inputs, "Please enter the new description.");
                            this.templates.setNewDescription(templatename, newdescription.get(0));
                            gamePresenter.displayTextScene(this, "BACK", "The Description is successfully changed.");
                            break;
                        }
                        if (edit_choice == 2) {
                            List<String> newnumchoices = gamePresenter.displayInputs(this, inputs, "Please enter the new number of choices.");
                            try {
                                Integer newchoices = Integer.parseInt(newnumchoices.get(0));
                                this.templates.setNewChoicesNum(templatename, newchoices);
                                gamePresenter.displayTextScene(this, "BACK", "The number of choice is successfully changed.");
                                break;
                            } catch (NumberFormatException ne) {
                                gamePresenter.displayTextScene(this, "BACK", "Sorry, please enter a valid input.");
                                break;
                            }
                        }
                        if (edit_choice == 3) {
                            List<String> newscheme = gamePresenter.displayInputs(this, inputs, "Please enter the new scheme.");
                            this.templates.setNewScheme(templatename, newscheme.get(0));
                            gamePresenter.displayTextScene(this, "BACK", "The Description is successfully changed.");
                            break;
                        }
                    }
                }
            } else {
                this.templates.Save_changes();
                break;
            }
        }
        this.templates.Save_changes();

    }
}
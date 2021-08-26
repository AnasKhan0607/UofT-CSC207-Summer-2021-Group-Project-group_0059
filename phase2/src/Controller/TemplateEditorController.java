package Controller;


import Entity.RegularUser;
import Entity.TempUser;
import Entity.User;
import Interface.TemplateData;
import Presenter.GamePresenter;
import Presenter.TemplateEditorPresenter;
import Presenter.TemplatePresenter;
import Presenter.UserLoginPresenter;
import UseCase.MessageManager;
import UseCase.TemplateManager;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * the controller class that interacts with Userinputs from Admin User wanting to edit a Template or chose Template.
 */
public class TemplateEditorController implements TemplateData {
    public static void main(String[] args) {
        TemplateEditorController editorController = new TemplateEditorController();
        editorController.run();
    }

    private TemplateManager templates;
    private Scanner myObj = new Scanner(System.in);

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
    public int chooseTemplate() {
        for (; ; ) {
            TemplateEditorPresenter.chose_template_to_edit(this.templates);
            String choice;
            choice = String.valueOf(myObj.nextLine());
            while (!this.templates.getTemplates().contains(this.templates.Find_template(choice)) && Integer.parseInt(choice) != -1) {
                TemplateEditorPresenter.try_agin();
                choice = String.valueOf(myObj.nextLine());

            }
            if (choice.equals("-1")) {
                break;
            }
            return this.templates.getNumChoices(choice);
        }
        return -1;
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
            int choice = gamePresenter.displayChoices(this, choices, "Hello, what you want to do?");
            if (choice == 0) {
                String templatelist = new String();
                for (String template_name : templates.Template_names()) {
                    templatelist = templatelist + "Template Name: " + template_name + "\n";
                }
                gamePresenter.displayTextScene(this, "BACK", "List Of Available Templates:" + "\n" + templatelist);
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
                    editchoices.add("EXIT");
                    for (; ; ) {
                        int edit_choice = gamePresenter.displayChoices(this, editchoices, "What would you like to change in the template?");
                        if (edit_choice == 3) {
                            break;
                        }
                        if (edit_choice == 0) {
                            List<String> newname = gamePresenter.displayInputs(this, inputs, "Please enter the new name.");
                            this.templates.setNewName(templatename, newname.get(0));
                            templatename = newname.get(0);
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
                    }
                }
            } else {
                this.templates.Save_changes();
                gamePresenter.displayTextScene(this, "CONTINUE", "Thank you for using our program");
                gamePresenter.terminateGUI();
                break;
            }
        }
        this.templates.Save_changes();

    }
}
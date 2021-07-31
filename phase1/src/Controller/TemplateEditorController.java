package Controller;

import Interface.TemplateData;
import Presenter.TemplateEditorPresenter;
import Presenter.TemplatePresenter;
import UseCase.TemplateManager;

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
    public TemplateEditorController(){
        this.templates = new TemplateManager();
    }

    /**
     * the constructor for this controller class
     * @return returns chosen template's number of choices or -1 when template isnt chosen.
     */
    public int chooseTemplate(){
        for (;;) {
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
    public void run(){
        for (;;){
            TemplateEditorPresenter.chose_template_to_edit(this.templates);
            String choice;
            choice = String.valueOf(myObj.nextLine());
            while (!this.templates.getTemplates().contains(this.templates.Find_template(choice))&&!choice.equals("-1")){
                TemplateEditorPresenter.try_agin();
                choice = String.valueOf(myObj.nextLine());

            }
            if (choice.equals("-1")){
                break;
            }
            for (;;) {
                TemplatePresenter.selected_template(this.templates.Find_template(choice));
                TemplatePresenter.display_template(this.templates, choice);
                TemplateEditorPresenter.edit_template();
                String edit_choice = String.valueOf(myObj.nextLine());
                while (!edit_choice.equals("-1") && !edit_choice.equals("0") && !edit_choice.equals("1") && !edit_choice.equals("2")){
                    TemplateEditorPresenter.try_agin_option();
                    edit_choice = String.valueOf(myObj.nextLine());
                }
                if (edit_choice.equals("-1")){
                    break;
                }
                if (edit_choice.equals("0")){
                    TemplateEditorPresenter.change_name();
                    String name = String.valueOf(myObj.nextLine());
                    if (name.equals("-1")){
                        TemplateEditorPresenter.try_agin_name();
                    }
                    else {
                        this.templates.setNewName(choice, name);
                        choice = name;

                    }
                }
                if (edit_choice.equals("1")){
                    TemplateEditorPresenter.change_description();
                    String description = String.valueOf(myObj.nextLine());
                    this.templates.setNewDescription(choice, description);
                }
                if (edit_choice.equals("2")){
                    TemplateEditorPresenter.change_choices();
                    boolean changable = false;
                    String read = String.valueOf(myObj.nextLine());
                    while (!changable){
                        try{
                            Integer testint = Integer.parseInt(read);
                            break;
                        }
                        catch (NumberFormatException ne){
                            TemplateEditorPresenter.try_agin_option();
                        }
                        read = String.valueOf(myObj.nextLine());
                    }
                    Integer choices = Integer.parseInt(read);
                    this.templates.setNewChoicesNum(choice, choices);
                }
            }

        }
        this.templates.Save_changes();

    }
}

package Controller;

import Entity.AdminUser;
import Entity.Template;
import Interface.TemplateData;
import Presenter.AdminUserNavigatorPresenter;
import Presenter.TemplateEditorPresenter;
import Presenter.TemplatePresenter;
import UseCase.TemplateManager;

import java.util.Scanner;

public class TemplateEditorController implements TemplateData {
    public static void main(String[] args) {
        TemplateEditorController editorController = new TemplateEditorController();
        editorController.run();
    }
    private TemplateManager templates;
    private Scanner myObj = new Scanner(System.in);

    public TemplateEditorController(){
        this.templates = new TemplateManager();
    }

    public int chooseTemplate(){
        for (;;) {
            TemplateEditorPresenter.chose_template_to_edit(this.templates.getTemplates());
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

    public void run(){
        for (;;){
            TemplateEditorPresenter.chose_template_to_edit(this.templates.getTemplates());
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
                TemplatePresenter.display_template(this.templates.Find_template(choice));
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
                    this.templates.setNewName(choice, name);
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
                            Integer test = Integer.parseInt(read);
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

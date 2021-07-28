package Controller;

import Entity.AdminUser;
import Entity.Template;
import Presenter.AdminUserNavigatorPresenter;
import Presenter.TemplateEditorPresenter;
import Presenter.TemplatePresenter;
import UseCase.TemplateManager;

import java.util.Scanner;

public class TemplateEditorController {
    public static void main(String[] args) {
        TemplateEditorController templatePresenter = new TemplateEditorController(new TemplateManager());
        templatePresenter.run();
    }
    private TemplateManager templates;

    public TemplateEditorController(){
        this.templates = new TemplateManager();
    }

    public void run(){
        Scanner myObj = new Scanner(System.in);
        for (;;){
            TemplateEditorPresenter.chose_template_to_edit(this.templates.getTemplates());
            String choice;
            choice = String.valueOf(myObj.nextLine());
            while (!this.templates.getTemplates().contains(this.templates.Find_template(choice))&&Integer.parseInt(choice)!=-1){
                TemplateEditorPresenter.try_agin();
                choice = String.valueOf(myObj.nextLine());
            }
            if (Integer.parseInt(choice)==-1){
                break;
            }
            Template template = this.templates.Find_template(choice);
            for (;;) {
                TemplatePresenter.selected_template(this.templates.Find_template(choice));
                TemplatePresenter.display_template(this.templates.Find_template(choice));
                TemplateEditorPresenter.edit_template();
                Integer edit_choice = Integer.valueOf(myObj.nextLine());
                while (edit_choice != -1 && edit_choice != 0 && edit_choice!=1 && edit_choice!=2){
                    TemplateEditorPresenter.try_agin_option();
                    edit_choice = Integer.valueOf(myObj.nextLine());
                }
                if (edit_choice==-1){
                    break;
                }
                if (edit_choice==0){
                    TemplateEditorPresenter.change_name();
                    String name = String.valueOf(myObj.nextLine());
                    template.setTemplatename(name);
                }
                if (edit_choice==1){
                    TemplateEditorPresenter.change_description();
                    String description = String.valueOf(myObj.nextLine());
                    template.setDescription(description);
                }
                if (edit_choice==2){
                    TemplateEditorPresenter.change_name();
                    Integer choices = Integer.valueOf(myObj.nextLine());
                    template.setNumchoice(choices);
                }
            }

        }
        this.templates.Save_changes();



    }
}

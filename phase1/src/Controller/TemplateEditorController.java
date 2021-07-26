package Controller;

import Entity.AdminUser;
import Entity.Template;
import Presenter.AdminUserNavigatorPresenter;
import Presenter.TemplateEditorPresenter;
import Presenter.TemplatePresenter;
import UseCase.TemplateManager;

import java.util.Scanner;

public class TemplateEditorController {
    private TemplateManager templates;

    public TemplateEditorController(TemplateManager a){
        templates = a;
    }

    public void run(){
        Scanner myObj = new Scanner(System.in);
        TemplateEditorPresenter.chose_template_to_edit(templates.getTemplates());
        String choice;
        choice = String.valueOf(myObj.nextLine());
        while (!templates.getTemplates().contains(templates.Find_template(choice))){
            TemplateEditorPresenter.try_agin();
            choice = String.valueOf(myObj.nextLine());
        }
        TemplatePresenter.selected_template(templates.Find_template(choice));
        TemplatePresenter.display_template(templates.Find_template(choice));
        TemplateEditorPresenter.edit_template();


    }
}

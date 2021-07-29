package Controller;

import Interface.UserData;
import Presenter.AdminUserNavigatorPresenter;

import java.util.Scanner;

public class AdminUserNavigatorController implements UserData {
    private String username;

    public AdminUserNavigatorController(String un){
        username = un;
    }

    public String currentUser(){
        return username;
    }

    public void run(){
        Scanner myObj = new Scanner(System.in);
        AdminUserNavigatorPresenter.display(username);
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* GamePlay*/

        } else if (choice == 2) {
            /* TemplateEditor*/
            TemplateEditorController tec = new TemplateEditorController();
            tec.run();
        } else if (choice == 3) {
            /* GameCreator*/
        } else if (choice == 4) {
            /* GameEditor*/
        } else if (choice == 5) {
            /*logout*/

        }
    }
}

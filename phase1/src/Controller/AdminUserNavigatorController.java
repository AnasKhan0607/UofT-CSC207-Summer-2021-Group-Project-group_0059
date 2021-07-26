package Controller;

import Entity.AdminUser;
import Presenter.AdminUserNavigatorPresenter;
import Presenter.UserLoginPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminUserNavigatorController implements UserNavigatorController{
    private AdminUser currentUser;

    public AdminUserNavigatorController(AdminUser a){
        currentUser = a;
    }



    public void run(){
        Scanner myObj = new Scanner(System.in);
        AdminUserNavigatorPresenter.display(currentUser.getUsername());
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* GamePlay*/

        } else if (choice == 2) {
            /* TemplateEditor*/
        } else if (choice == 3) {
            /* GameCreator*/
        } else if (choice == 4) {
            /* GameEditor*/
        } else if (choice == 5) {
            /*logout*/

        }
    }
}

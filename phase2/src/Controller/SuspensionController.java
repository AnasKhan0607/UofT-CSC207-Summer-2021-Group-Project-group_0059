package Controller;

import Presenter.SuspensionPresenter;
import UseCase.UserManager;

import java.util.Scanner;

public class SuspensionController {
    private String username;

    public SuspensionController(){

    }

    public void suspend(){
        Scanner myObj = new Scanner(System.in);
        SuspensionPresenter.display1();
        String choice = myObj.nextLine();
        username = choice;
        UserManager tempum = new UserManager();
        boolean rst = tempum.suspendUser(username);
        if (rst){
            SuspensionPresenter.successMessage(username);
        } else {SuspensionPresenter.errorMessage();}
    }

    public void unsuspend(){
        Scanner myObj = new Scanner(System.in);
        SuspensionPresenter.display2();
        String choice = myObj.nextLine();
        username = choice;
        UserManager tempum = new UserManager();
        boolean rst = tempum.unsuspendUser(username);
        if (rst){SuspensionPresenter.successMessage2(username);} else {SuspensionPresenter.errorMessage();}
    }

    public String getUsername() {
        return username;
    }


}

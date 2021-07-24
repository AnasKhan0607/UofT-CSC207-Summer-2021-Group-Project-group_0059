package Controller;

import Presenter.UserLoginPresenter;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public class UserLoginController {

    private String userName;
    private String password;
    private UserManager testUM = new UserManager();

    public UserLoginController(){
        this.userName = null;
        this.password = null;
    }

    public void NormalUserinput(){
        Scanner myObj = new Scanner(System.in);
        UserLoginPresenter.display();
        this.userName = myObj.nextLine();
        if (this.userName == "Signup"){
            /* Jump to signup */
        } else if (this.userName == "Guest"){
            GuestUserInput();
        } else {
            this.password = myObj.nextLine();
            if (testUM.SearchUser(this.userName) == null){
                UserLoginPresenter.errorMessage();
            } else {
                String temppassword = testUM.SearchUser(this.userName).getPassword();
                if (!temppassword.equals(this.password)){
                    UserLoginPresenter.errorMessage();
                } else {
                    UserLoginPresenter.successMessage();
                }

            }
        }

    }

    public void GuestUserInput(){
        Scanner myObj = new Scanner(System.in);
        ArrayList<String> info = new ArrayList<>();
        /*UserLoginPresenter.displayUsername();*/
        UserLoginPresenter.display2();
        this.userName = myObj.nextLine();
        info.add(this.userName);
        testUM.addUser(info);
        UserLoginPresenter.successMessage();
    }


}



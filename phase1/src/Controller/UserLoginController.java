package Controller;

import Entity.AdminUser;
import Entity.RegularUser;
import Entity.User;
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
        if (this.userName.equals("Signup")){
            /* Jump to signup */
            UserSignupController signup1 = new UserSignupController(this.testUM);
            signup1.UserInput();
            // Going through the login process again
            UserLoginPresenter.display3(); // using display 3 to remove dialogue of giving the user the option to signup or login as guest
            this.userName = myObj.nextLine();
            RegularLogin();
        } else if (this.userName.equals("Guest")){
            GuestUserInput();
        } else {
            RegularLogin();
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

    public void redirect(User user){
        String username = user.getUsername();
        if (username.charAt(0) == 'A'){
            AdminUserNavigatorController.run();
        } else if(username.charAt(0) == 'R'){
            RegularUserNavigatorController.run();
        } else {
            GuestUserNavigatorController.run();
        }

    }

    // helper method

    private void RegularLogin() {
        Scanner myObj = new Scanner(System.in);
        this.password = myObj.nextLine();
        if (testUM.SearchUser(this.userName) == null) {
            UserLoginPresenter.errorMessage();
        } else {
            User tempUser = testUM.SearchUser(this.userName);
            String temppassword = tempUser.getPassword();
            if (!temppassword.equals(this.password)) {
                UserLoginPresenter.errorMessage();
            } else {
                UserLoginPresenter.successMessage();
                redirect(tempUser);
            }
        }
    }
}



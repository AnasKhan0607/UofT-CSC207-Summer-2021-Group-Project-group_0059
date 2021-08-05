package Controller;

import Entity.AdminUser;
import Entity.GuestUser;
import Entity.RegularUser;
import Entity.User;
import Presenter.UserLoginPresenter;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller for the user login process.
 * @author Ahmad I., Ruilin P.
 */
public class UserLoginController {

    private String userName;
    private String password;
    private UserManager testUM = new UserManager();

    /**
     * Creates a new UserLoginController object that will set the userName and password attributes to what the
     * user inputs.
     */
    public UserLoginController(){
        this.userName = null;
        this.password = null;
    }

    /**
     * Uses user input as well as the UserLoginPresenter to allow the user to login to their account, or login as guest.
     */
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

    /**
     * Unique prompts, inputs, and actions taken if the user selects logging in as a guest.
     */
    public void GuestUserInput(){

        UserLoginPresenter.successMessage("Guest");
        redirect(testUM.CreateGuestUser());
    }

    /**
     * Uses the UserNavigator classes to redirect the user to a new UI once they have logged in.
     * @param user a User object that shows whether it is an Admin, Regular, or Guest user to redirect to a new UI
     */
    public void redirect(User user){
        String username = user.getUsername();
        if (username.startsWith("Admin_")){
            AdminUserNavigatorController aunc = new AdminUserNavigatorController(username);
            aunc.run();
        } else if(username.startsWith("Guest")){
            GuestUserNavigatorController gunc = new GuestUserNavigatorController();
            gunc.run();

        } else {
            RegularUserNavigatorController runc = new RegularUserNavigatorController(username);
            runc.run();
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
                if (this.userName.startsWith("Admin_")) {
                    UserLoginPresenter.successMessage("Admin");
                } else {
                    UserLoginPresenter.successMessage("Regular");
                }
                redirect(tempUser);
            }
        }
    }
}



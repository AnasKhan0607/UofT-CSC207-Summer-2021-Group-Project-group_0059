package Controller;

import Entity.AdminUser;
import Entity.GuestUser;
import Entity.RegularUser;
import Entity.TempUser;
import Entity.User;
import Presenter.GamePresenter;
import Presenter.UserLoginPresenter;
import UseCase.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

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
        /**
        Scanner myObj = new Scanner(System.in);
        while (true){UserLoginPresenter.display();
            this.userName = myObj.nextLine();
            if (this.userName.equals("Signup")){

                UserSignupController signup1 = new UserSignupController(this.testUM);
                signup1.UserInput();
                // Going through the login process again
                UserLoginPresenter.display3(); // using display 3 to remove dialogue of giving the user the option to signup or login as guest
                this.userName = myObj.nextLine();
                RegularLogin();
            } else if (this.userName.equals("Guest")){
                GuestUserInput();
            } else if(this.userName.equals("EXIT")){
                UserLoginPresenter.exitMessage();
                break;
            }else {
                RegularLogin();
            }}
**/

        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Login");
        choices.add("Signup");
        choices.add("Login as Guest");
        choices.add("EXIT");
        while (true){
            int choice = gamePresenter.displayChoices(this, choices, "Please login first:");
            //Scanner myObj = new Scanner(System.in);
            if (choice == 0){
                /**
                UserLoginPresenter.display();
                this.userName = myObj.nextLine();
                RegularLogin();
                **/

                ArrayList<String> inputs = new ArrayList<>();
                inputs.add("Username:");
                inputs.add("Password:");
                List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Login");
                this.userName = userinputs.get(0);
                this.password = userinputs.get(1);
                RegularLogin();
            } else if (choice == 1){
                UserSignupController signup1 = new UserSignupController(this.testUM);
                signup1.UserInput();
                // Going through the login process again
                /**
                UserLoginPresenter.display3(); // using display 3 to remove dialogue of giving the user the option to signup or login as guest
                this.userName = myObj.nextLine();
                RegularLogin();
                 **/

            } else if (choice == 2){
                GuestUserInput();
            } else {
                UserLoginPresenter.exitMessage();
                break;
            }
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
        //Scanner myObj = new Scanner(System.in);
        //this.password = myObj.nextLine();
        if (testUM.SearchUser(this.userName) == null) {
            UserLoginPresenter.errorMessage();
        } else {
            User tempUser = testUM.SearchUser(this.userName);
            String temppassword = tempUser.getPassword();
            if (!temppassword.equals(this.password)) {
                UserLoginPresenter.errorMessage();
            } else if (tempUser.getflag()){
                UserLoginPresenter.suspensionMessage(tempUser.getUsername());
            } else {
                if (this.userName.startsWith("Admin_")) {
                    UserLoginPresenter.successMessage("Admin");
                } else if (this.userName.startsWith("Temp_")) {
                    LocalDate endDate = ((TempUser)tempUser).getEndDate();
                    LocalDate today = LocalDate.now();
                    if (today.isAfter(endDate)) {
                        UserLoginPresenter.expiredAccountMessage();
                    }
                    else {
                        UserLoginPresenter.successMessage("Temporary. After " + endDate +
                                " your account will be unavailable.");
                    }
                }
                else {
                    UserLoginPresenter.successMessage("Regular");
                }
                redirect(tempUser);
            }
        }
    }
}



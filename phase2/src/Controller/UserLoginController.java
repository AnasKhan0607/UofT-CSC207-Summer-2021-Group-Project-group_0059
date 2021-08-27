package Controller;


import Entity.TempUser;

import Presenter.GamePresenter;

import UseCase.UserManager;

import java.util.ArrayList;

import java.util.List;

import java.time.LocalDate;

/**
 * Controller for the user login process.
 * @author Ahmad I., Ruilin P.
 */
public class UserLoginController {

    private String userName;
    private String password;
    private UserManager testUM;

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
        GamePresenter gamePresenter = new GamePresenter();
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Login");
        choices.add("Signup");
        choices.add("Login as Guest");
        choices.add("Forgot Password");
        choices.add("EXIT");
        while (true){
            testUM = new UserManager();
            int choice = gamePresenter.displayChoices(this, choices, "Please login first:");

            if (choice == 0){ login(gamePresenter); }
            else if (choice == 1){
                UserSignupController signup1 = new UserSignupController(this.testUM);
                signup1.UserInput();
            }
            else if (choice == 2){ GuestUserInput(); }
            else if(choice == 3){ forgotPassword(gamePresenter); }
            else{
                gamePresenter.displayTextScene(this, "CONTINUE", "Thank you for using our program");
                gamePresenter.terminateGUI();
                break;
            }
        }
    }

    private void login(GamePresenter gamePresenter) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("Username:");
        inputs.add("Password:");
        List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Login");
        this.userName = userinputs.get(0);
        this.password = userinputs.get(1);
        RegularLogin();
    }

    private void forgotPassword(GamePresenter gamePresenter) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("Your Username:");
        List<String> userinputs = gamePresenter.displayInputs(this, inputs, "Forget password");
        String userName = userinputs.get(0);
        if (!testUM.resetTempPassword(userName)){
            gamePresenter.displayTextScene(this, "BACK", "There is no such username in " +
                    "record, please check your spelling and try again!");
        } else {
            gamePresenter.displayTextScene(this, "CONTINUE", "reset successful, " +
                    "please check the text file(named with your username) with your temporal password. And don't forget to reset your password after " +
                    "you login!");
        }



    }


    /**
     * Unique prompts, inputs, and actions taken if the user selects logging in as a guest.
     */
    public void GuestUserInput(){


        redirect("Guest");
    }

    /**
     * Uses the UserNavigator classes to redirect the user to a new UI once they have logged in.
     * @param username a String object that shows whether it is an Admin, Regular, or Guest user to redirect to a new UI
     */
    public void redirect(String username){

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

        GamePresenter gamePresenter = new GamePresenter();
        if (testUM.SearchUser(this.userName) == null) {
            gamePresenter.displayTextScene(this, "BACK", "Sorry, but either the username or the password is incorrect");
            return;
        }


        if (!testUM.validatePassword(userName, password)) {
            gamePresenter.displayTextScene(this, "BACK", "Sorry, but either the username or the password is incorrect");
        }
        else if (testUM.validateFlag(userName)){
            gamePresenter.displayTextScene(this, "BACK", "(" + userName + ") is currently suspended until "+ testUM.SearchUser(userName).getsuspensionEndTime()+" . Please contact Ruilin or Ahmad for support.");
        }
        else {
            login(gamePresenter, userName);
        }
    }

    private void login(GamePresenter gamePresenter, String tempUser) {
        if (this.userName.startsWith("Admin_")) {
            gamePresenter.displayTextScene(this, "CONTINUE", "Logged in as Admin" );
        }
        else if (this.userName.startsWith("Temp_")) {
            LocalDate endDate = ((TempUser)testUM.SearchUser(userName)).getEndDate();
            LocalDate today = LocalDate.now();
            if (today.isAfter(endDate)) {
                gamePresenter.displayTextScene(this, "BACK", "Sorry, your temporary account has expired." );
            }
            else {
                gamePresenter.displayTextScene(this, "BACK", "Temporary. After " + endDate + " your account will be unavailable.");
            }
        }
        else {
            gamePresenter.displayTextScene(this, "CONTINUE", "Logged in as Regular" );
        }
        redirect(userName);
    }
}



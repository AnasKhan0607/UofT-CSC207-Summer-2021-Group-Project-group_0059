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
        /*UserLoginPresenter.displayUsername();*/
        this.userName = myObj.nextLine();
        this.password = myObj.nextLine();
        if (testUM.SearchUser(this.userName) == null){
            System.out.println("Username does not exist");
        } else {
            String temppassword = testUM.SearchUser(this.userName).getPassword();
            if (!temppassword.equals(this.password)){
                System.out.println("Username does not exist");
            } else {
                System.out.println("Login successful!");
                System.out.println("Redirecting...");
            }

        }
    }

    public void GuestUserInput(){
        Scanner myObj = new Scanner(System.in);
        ArrayList<String> info = new ArrayList<>();
        /*UserLoginPresenter.displayUsername();*/
        System.out.println("input username. press enter to continue");
        this.userName = myObj.nextLine();
        info.add(this.userName);
        testUM.addUser(info);
        System.out.println("Logged in as Guest");
        System.out.println("Redirecting...");
    }


}



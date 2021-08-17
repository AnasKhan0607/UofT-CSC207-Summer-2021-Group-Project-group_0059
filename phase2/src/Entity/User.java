package Entity;

import java.time.LocalDate;
import java.util.Date;

/**
 * The abstract class, extended by AdminUser, RegularUser, and GuestUser
 */
public abstract class User {


    private String username;
    private boolean suspensionFlag;
    private LocalDate suspensionEndTime;

    /**
     * create a user with the given username
     * @param username This user's username
     */
    public User(String username){
        this.username = username;
    }
    /**
     * Returns the user's username.
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Returns the user's password.
     * @return A string containing the user's password
     */
    public abstract String getPassword();

    /**
     * allowing the user to change his/her username
     * @param username The user's username
     */
    public void setUsername (String username) {
        this.username = username;
    }




    public void raiseFlag(){this.suspensionFlag = true;}

    public void lowerFlag(){this.suspensionFlag = false;}

    public boolean getflag(){return this.suspensionFlag;}

    public void setsuspensionEndTime(LocalDate suspensionEndTime){
        this.suspensionEndTime = suspensionEndTime;
    }
    public LocalDate getsuspensionEndTime(){
        return suspensionEndTime;
    }
}


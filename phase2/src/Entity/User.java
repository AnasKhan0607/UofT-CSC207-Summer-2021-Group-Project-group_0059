package Entity;

import java.time.LocalDate;


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


    /**
     * allow the program to set the User's suspension status to positive(become suspended)
     */
    public void raiseFlag(){this.suspensionFlag = true;}
    /**
     * allow the program to set the User's suspension status to negative(become not suspended)
     */
    public void lowerFlag(){this.suspensionFlag = false;}

    /**
     *
     * @return the user's suspension status, true for being suspended and false for not suspended
     */
    public boolean getflag(){return this.suspensionFlag;}

    /**
     * allow the system to set the date when the User's suspension comes to an end, null if User's not suspended
     * @param suspensionEndTime the date when the User's suspension comes to an end
     */
    public void setsuspensionEndTime(LocalDate suspensionEndTime){
        this.suspensionEndTime = suspensionEndTime;
    }

    /**
     *
     * @return the date when the User's suspension comes to an end
     */
    public LocalDate getsuspensionEndTime(){
        return suspensionEndTime;
    }
}


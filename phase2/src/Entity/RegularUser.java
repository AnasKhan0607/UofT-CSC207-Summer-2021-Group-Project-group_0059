package Entity;

/**
 * Represents a regular user.
 * @author Ahmad I., Ruilin P.
 */
public class RegularUser extends User {
    private String password;


    /**
     * Creates a regular user with a username and password.
     * @param username The user's username.
     * @param password The user's password.
     */
    public RegularUser(String username, String password) {
        super(username);
        this.password = password;
    }

    /**
     * Returns the user's password.
     * @return A string containing the user's password
     */
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }


}

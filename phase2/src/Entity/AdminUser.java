package Entity;

/**
 * Represents an admin user.
 * @author Ahmad I., Ruilin P.
 */
public class AdminUser extends User {
    private String password;

    /**
     * Creates an admin user with a username and password.
     * @param username The user's username.
     * @param password The user's password.
     */
    public AdminUser(String username, String password) {
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

    /**
     * Allow the system to reset User's password
     * @param password the new password
     */
    public void setPassword(String password){
        this.password = password;
    }
}

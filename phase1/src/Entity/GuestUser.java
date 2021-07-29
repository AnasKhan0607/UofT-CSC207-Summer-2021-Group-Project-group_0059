package Entity;

/**
 * Represents a guest user.
 * @author Ahmad I., Ruilin P.
 */
public class GuestUser extends User {
    /**
     * Creates a guest user with a username.
     * @param username The user's username.
     */
    public GuestUser(String username) {
        super(username);
    }

    /**
     * Returns the user's username.
     * @return The user's username
     */
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * Returns a message showing the user has no password.
     * @return A string showing the user has no password
     */
    public String getPassword(){
        return "No password";
    }
}

package Entity;

import java.time.LocalDate;

/**
 * Represents a regular user.
 * @author Ahmad I., Ruilin P.
 */
public class TempUser extends User {
    private String password;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Creates a regular user with a username and password.
     * @param username The user's username.
     * @param password The user's password.
     */
    public TempUser (String username, String password) {
        super(username);
        this.password = password;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusDays(30);
    }

    /**
     * Returns the user's password.
     * @return A string containing the user's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns date of this user's account creation.
     * @return A LocalDate object containing when the user's account was created.
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Returns date of this user's account expiration.
     * @return A LocalDate object containing when the user's account will expire.
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }


}

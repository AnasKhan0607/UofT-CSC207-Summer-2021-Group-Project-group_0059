package Entity;

public class RegularUser extends User {
    private String password;

    public RegularUser(String username, String password) {
        super(username);
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

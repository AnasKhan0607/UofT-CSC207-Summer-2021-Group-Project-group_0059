package Entity;

public class GuestUser extends User {
    public GuestUser(String username) {
        super(username);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    public String getPassword(){
        return "No password";
    }
}

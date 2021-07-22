public abstract class User {
    public static void main(String[] args) {

    }

    private String username;

    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract String getPassword();

    public void setUsername (String username) {
        this.username = username;
    }
}


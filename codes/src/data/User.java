package data;

public class User {
    private final UserRole role;
    private final String username;
    private String password;

    public User(String username, String password, UserRole role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername(){
      return this.username;
    }

    public String getPassword(){
      return this.password;
    }

    public UserRole getUserRole(){
      return this.role;
    }
}

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private int userId;

    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User ID: " + userId +
               ", Username: " + username;
    }
}
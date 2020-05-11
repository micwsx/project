package feature.circulardependency;

public class Account {
    private User user;

    public User getUser() {
        return user;
    }
}

package feature.circulardependency;

public class User {
    private Account account;

    public Account getAccount() {
        return account;
    }
}

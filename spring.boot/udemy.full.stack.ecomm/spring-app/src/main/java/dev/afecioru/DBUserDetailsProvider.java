package dev.afecioru;

public class DBUserDetailsProvider implements UserDetailsProvider {
    @Override
    public String getUserDetails() {
        return "Getting the user details from the DB.";
    }
}

package dev.afecioru;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserManager {
    private final UserDetailsProvider userDetailsProvider;

    public void printUserDetails() {
        System.out.println(userDetailsProvider.getUserDetails());
    }
}

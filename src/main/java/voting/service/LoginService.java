package voting.service;

import voting.model.User;

public class LoginService {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LoginService.currentUser = currentUser;
    }






}

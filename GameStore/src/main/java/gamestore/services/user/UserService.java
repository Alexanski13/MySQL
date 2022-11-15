package gamestore.services.user;

import gamestore.domain.entities.User;

public interface UserService {
    String registerUser(String[] args);
    String loginUser(String[] args);

    String logoutUser();

    User getLoggedInUser();
}

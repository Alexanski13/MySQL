package DefaultPackage.services;

import DefaultPackage.models.User;

public interface UserService {
    void register(String username, int age);

    User findByUsername(String username);
}

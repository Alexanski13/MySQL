package workshop.services;

import org.springframework.stereotype.Service;
import workshop.models.users.User;
import workshop.models.users.LoginDTO;
import workshop.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(LoginDTO loginDTO) {
        return this.userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
    }
}

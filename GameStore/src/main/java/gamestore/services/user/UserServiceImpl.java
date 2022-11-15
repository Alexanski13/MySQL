package gamestore.services.user;

import gamestore.domain.dtos.user.UserLoginDto;
import gamestore.domain.dtos.user.UserRegisterDto;
import gamestore.domain.entities.User;
import gamestore.domain.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static gamestore.constants.Validations.USERNAME_OR_PASSWORD_NOT_VALID_MESSAGE;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private User loggedInUser;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String[] args) {
        final String email = args[1];
        final String password = args[2];
        final String confirmPassword = args[3];
        final String fullName = args[4];
        UserRegisterDto userRegisterDto;

        try {
            userRegisterDto = new UserRegisterDto(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }


        final User user = this.modelMapper.map(userRegisterDto, User.class);

        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        boolean isUserFound = this.userRepository.findByEmail(userRegisterDto.getEmail()).isPresent();

        if (isUserFound) {
            return "Email already exists";
        }

        this.userRepository.save(user);

        return userRegisterDto.successfulRegisterFormat();
    }

    @Override
    public String loginUser(String[] args) {
        final String email = args[1];
        final String password = args[2];

        final UserLoginDto userLoginDto = new UserLoginDto(email, password);

        Optional<User> user = this.userRepository.findByEmail(userLoginDto.getEmail());

        if (user.isPresent() && this.loggedInUser == null && user.get().getPassword().equals(userLoginDto.getPassword())) {
            this.loggedInUser = this.userRepository.findByEmail(userLoginDto.getEmail()).get();
            return "Successfully logged in " + this.loggedInUser.getFullName();
        }

        return USERNAME_OR_PASSWORD_NOT_VALID_MESSAGE;
    }

    @Override
    public String logoutUser() {
        if (this.loggedInUser == null) {
            return "Cannot log out. No user was logged in.";
        }

        String output = "User " + this.loggedInUser.getFullName() + " successfully logged out";

        this.loggedInUser = null;
        return output;
    }

    @Override
    public User getLoggedInUser() {
        return null;
    }

}

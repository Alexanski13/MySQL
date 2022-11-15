package gamestore;

import gamestore.services.game.GameService;
import gamestore.services.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static gamestore.constants.Commands.*;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final GameService gameService;

    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        final String[] input = scanner.nextLine().split("\\|");
        final String command = input[0];

        final String output = switch (command) {
            case REGISTER_USER -> userService.registerUser(input);
            case LOGIN_USER -> userService.loginUser(input);
            case LOGOUT_USER -> userService.logoutUser();
            case ADD_GAME -> gameService.addGame(input);
            default -> "Command not found";
        };

        System.out.println(output);
    }
}

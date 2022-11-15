package gamestore.services.game;

import gamestore.domain.dtos.game.GameDto;
import gamestore.domain.entities.Game;
import gamestore.domain.repositories.GameRepository;
import gamestore.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final UserService userService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public String addGame(String[] args) {
        if (this.userService.getLoggedInUser() != null && this.userService.getLoggedInUser().getIsAdmin()) {

            final String title = args[1];
            final BigDecimal price = BigDecimal.valueOf(Long.parseLong(args[2]));
            final float size = Float.parseFloat(args[3]);
            final String trailer = args[4];
            final String imageUrl = args[5];
            final String description = args[6];
            final LocalDate releaseDate = LocalDate.now();

            final GameDto gameDto = new GameDto(title, trailer, imageUrl, size, price, description, releaseDate);

            Game gameToSave = this.modelMapper.map(gameDto, Game.class);

            this.gameRepository.save(gameToSave);
            return "Added " + title;
        }

        return "Impossible command";
    }

    @Override
    public String editGame(String[] args) {
        return null;
    }

    @Override
    public String deleteGame(Long id) {
        return null;
    }
}

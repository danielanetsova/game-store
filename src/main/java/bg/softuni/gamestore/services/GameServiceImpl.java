package bg.softuni.gamestore.services;

import bg.softuni.gamestore.dtos.AllGamesDTO;
import bg.softuni.gamestore.dtos.DetailGameDTO;
import bg.softuni.gamestore.dtos.GameDTO;
import bg.softuni.gamestore.entities.Game;
import bg.softuni.gamestore.exceptions.NoSuchEntityException;
import bg.softuni.gamestore.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper mapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public void addGame(GameDTO gameDTO) {
        Game game = mapper.map(gameDTO, Game.class);
        gameRepository.saveAndFlush(game);
    }

    @Override
    public Game deleteGame(int id) {
        Optional<Game> byId = gameRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NoSuchEntityException("There is no such game");
        }
        gameRepository.deleteById(id);
        return byId.get();
    }

    @Override
    public void editGame(GameDTO gameDTO, int id) {
        Game game = mapper.map(gameDTO, Game.class);
        game.setId(id);
        gameRepository.saveAndFlush(game);
    }

    @Override
    public GameDTO getGameDTOById(int id) {
        Optional<Game> byId = gameRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NoSuchEntityException("There is no such game");
        }

        Game game = byId.get();
        return mapper.map(game, GameDTO.class);
    }

    @Override
    public Set<AllGamesDTO> getAllGames() {
        return gameRepository
                .findAll()
                .stream()
                .map(game -> mapper.map(game, AllGamesDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public DetailGameDTO getGamesDetailsByName(String title) {
        Optional<Game> byTitle = gameRepository.findByTitle(title);

        if (byTitle.isEmpty()) {
            throw new NoSuchEntityException("There is no such game");
        }

        return mapper.map(byTitle.get(), DetailGameDTO.class);
    }

    @Override
    public Game getGameByTitle(String title) {
        Optional<Game> byTitle = gameRepository.findByTitle(title);

        if (byTitle.isEmpty()) {
            throw new NoSuchEntityException("There is no such game");
        }

        return byTitle.get();
    }


}

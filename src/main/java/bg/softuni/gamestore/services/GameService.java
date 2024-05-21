package bg.softuni.gamestore.services;

import bg.softuni.gamestore.dtos.AllGamesDTO;
import bg.softuni.gamestore.dtos.DetailGameDTO;
import bg.softuni.gamestore.dtos.GameDTO;
import bg.softuni.gamestore.entities.Game;

import java.util.Optional;
import java.util.Set;

public interface GameService {
    void addGame(GameDTO gameDTO);
    Game deleteGame(int id);
    void editGame(GameDTO game, int id);
    GameDTO getGameDTOById(int id);

    Set<AllGamesDTO> getAllGames();

    DetailGameDTO getGamesDetailsByName(String title);
    Game getGameByTitle(String title);
}

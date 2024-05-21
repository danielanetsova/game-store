package bg.softuni.gamestore.repositories;

import bg.softuni.gamestore.dtos.DetailGameDTO;
import bg.softuni.gamestore.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findByTitle(String title);
}

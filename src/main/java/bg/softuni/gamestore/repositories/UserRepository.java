package bg.softuni.gamestore.repositories;

import bg.softuni.gamestore.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isLogged = true WHERE u.email = :email AND u.password = :password")
    void setUserLogStatusTrue(String email, String password);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isLogged = false WHERE u.id = :id")
    void setUserLogStatusFalse(int id);

    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findFirstByEmail(String email);

    Optional<User> findByIsLogged(boolean b);
}

package bg.softuni.gamestore.services;

import bg.softuni.gamestore.dtos.BasicUserDTO;
import bg.softuni.gamestore.dtos.RegisterUserDTO;
import bg.softuni.gamestore.entities.User;

import java.util.Optional;

public interface UserService {

    void registerUser(RegisterUserDTO userDTO);
    boolean findUserByEmail(String email);
    BasicUserDTO loginUser(String email, String password);
    String logoutUser();
    boolean isCurrentUserAdmin();
    Optional<User> returnLoggedUser();
    void updateUser(User user);
}

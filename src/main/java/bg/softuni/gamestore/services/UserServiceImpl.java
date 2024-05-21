package bg.softuni.gamestore.services;

import bg.softuni.gamestore.dtos.BasicUserDTO;
import bg.softuni.gamestore.dtos.RegisterUserDTO;
import bg.softuni.gamestore.entities.User;
import bg.softuni.gamestore.exceptions.LoggedUserException;
import bg.softuni.gamestore.exceptions.ValidationException;
import bg.softuni.gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public void registerUser(RegisterUserDTO userDTO) {
        Optional<User> optionalUser = returnLoggedUser();

        if (optionalUser.isPresent()) {
            throw new LoggedUserException("An user has already been logged");
        }

        User user = mapper.map(userDTO, User.class);

        if (userRepository.count() == 0) {
            user.setIsAdmin(true);
        }

        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean findUserByEmail(String email) {
        Optional<User> firstByEmail = userRepository.findFirstByEmail(email);
        return firstByEmail.isPresent();
    }

    @Override
    public BasicUserDTO loginUser(String email, String password) {
        Optional<User> optionalUser = returnLoggedUser();

        if (optionalUser.isPresent()) {
            throw new LoggedUserException("An user has already been logged");
        }

        Optional<User> byEmailAndPassword = userRepository.findByEmailAndPassword(email, password);

        if (byEmailAndPassword.isEmpty()) {
            throw new ValidationException("Incorrect username / password");
        }

        userRepository.setUserLogStatusTrue(email, password);

        return this.mapper.map(byEmailAndPassword.get(), BasicUserDTO.class);
    }

    @Override
    public String logoutUser() {
        Optional<User> optionalUser = returnLoggedUser();
        if (optionalUser.isEmpty()) {
            throw new LoggedUserException("Cannot log out. No user was logged in.");
        }
        User user = optionalUser.get();
        userRepository.setUserLogStatusFalse(user.getId());
        return String.format("User %s successfully logged out\n", user.getFullName());
    }

    @Override
    public Optional<User> returnLoggedUser() {
        return this.userRepository.findByIsLogged(true);
    }

    @Override
    public boolean isCurrentUserAdmin() {
        Optional<User> optionalUser = returnLoggedUser();
        if (optionalUser.isEmpty()) {
            throw new LoggedUserException("No user is logged");
        }
        return optionalUser.get().getIsAdmin();
    }

    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }
}

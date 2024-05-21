package bg.softuni.gamestore.services;

import bg.softuni.gamestore.dtos.*;
import bg.softuni.gamestore.entities.Game;
import bg.softuni.gamestore.entities.Order;
import bg.softuni.gamestore.entities.User;
import bg.softuni.gamestore.exceptions.LoggedUserException;
import bg.softuni.gamestore.exceptions.ValidationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExecutorServiceImpl implements ExecutorService {
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    @Autowired
    public ExecutorServiceImpl(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public String execute(String input) {
        String command = input.split("\\|")[0];

        String output = switch (command) {
            case "RegisterUser" -> registerUser(input);
            case "LoginUser" -> loginUser(input);
            case "Logout" -> userService.logoutUser();
            case "AddGame" -> addGame(input);
            case "EditGame" -> editGame(input);
            case "DeleteGame" -> deleteGame(input);
            case "AllGames" -> showAllGames();
            case "DetailGame" -> showGameDetails(input.split("\\|")[1]);
            case "OwnedGames" -> showCurrentlyLoggedUserGames();
            case "AddItem" -> addGameToShoppingCart(input);
            case "RemoveItem" -> removeGameFromShoppingCart(input);
            case "BuyItem" -> buyGames();
            default -> "Command doesn't exist";
        };

        return output;
    }

    private User getLoggedUser() {
        Optional<User> optionalUser = userService.returnLoggedUser();

        if (optionalUser.isEmpty()) {
            throw new LoggedUserException("No user is logged.");
        }

        return optionalUser.get();
    }

    private String buyGames() {
        User user = getLoggedUser();
        user.buyAddedToCartGames();
        userService.updateUser(user);
        return user.getBoughtGamesTitles();
    }

    private String removeGameFromShoppingCart(String input) {
        String gameTitle = input.split("\\|")[1];
        User user = getLoggedUser();
        Game game = gameService.getGameByTitle(gameTitle);
        Order order = user.removeGameFromCart(game);
        orderService.removeProductFromOrder(order);
        return  gameTitle + " removed from cart.";
    }

    @Transactional
    private String addGameToShoppingCart(String input) {
        String gameTitle = input.split("\\|")[1];
        User user = getLoggedUser();
        Game game = gameService.getGameByTitle(gameTitle);
        Order order = user.purchase(game);
        orderService.saveOrder(order);
        return gameTitle + " added to cart";
    }

    private String showCurrentlyLoggedUserGames() {
        User user = getLoggedUser();

        return user
                .getGames()
                .stream()
                .map(Game::getTitle)
                .collect(Collectors.joining("\n"));
    }

    private String showGameDetails(String title) {
        return gameService
                .getGamesDetailsByName(title).toString();
    }

    private String showAllGames() {
        Set<AllGamesDTO> allGamesDTOSet = gameService.getAllGames();
        return allGamesDTOSet
                .stream()
                .map(AllGamesDTO::toString)
                .collect(Collectors.joining("\n"));
    }

    private String editGame(String input) {
        if (!userService.isCurrentUserAdmin()) {
            throw new LoggedUserException("Logged user is not admin");
        }
        String[] args = input.split("\\|");
        int id = Integer.parseInt(args[1]);

        GameDTO gameDTO = gameService.getGameDTOById(id);

        for (int i = 2; i < args.length; i++) {
            if (args[i].split("=")[0].equals("price")) {
                BigDecimal newPrice = new BigDecimal(args[i].split("=")[1]);
                gameDTO.setPrice(newPrice);
            } else if (args[i].split("=")[0].equals("title")) {
                String newTitle = args[i].split("=")[1];
                gameDTO.setTitle(newTitle);
            } else if (args[i].split("=")[0].equals("size")) {
                float newSize = Float.parseFloat(args[i].split("=")[1]);
                gameDTO.setSize(newSize);
            } else if (args[i].split("=")[0].equals("trailer")) {
                String newTrailer = args[i].split("=")[1];
                gameDTO.setTrailer(newTrailer);
            } else if (args[i].split("=")[0].equals("thumbnailURL")) {
                String newThumbnailURL = args[i].split("=")[1];
                gameDTO.setImageThumbnail(newThumbnailURL);
            } else if (args[i].split("=")[0].equals("description")) {
                String newDescription = args[i].split("=")[1];
                gameDTO.setDescription(newDescription);
            } else if (args[i].split("=")[0].equals("releaseDate")) {
                LocalDate newReleaseDate = LocalDate.parse(args[i].split("=")[1],
                        DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                gameDTO.setReleaseDate(newReleaseDate);
            }
        }
        gameDTO.validate();
        gameService.editGame(gameDTO, id);
        return "Edited " + gameDTO.getTitle();
    }

    private String deleteGame(String input) {
        if (!userService.isCurrentUserAdmin()) {
            throw new LoggedUserException("Logged user is not admin");
        }

        int id = Integer.parseInt(input.split("\\|")[1]);
        Game game = gameService.deleteGame(id);

        return "Deleted " + game.getTitle();
    }

    private String addGame(String input) {
        if (!userService.isCurrentUserAdmin()) {
            throw new LoggedUserException("Logged user is not admin");
        }

        String[] args = input.split("\\|");

        String title = args[1];
        BigDecimal price = new BigDecimal(args[2]);
        float size = Float.parseFloat(args[3]);
        String trailer = args[4];
        String thumbnailURL = args[5];
        String description = args[6];
        LocalDate releaseDate = LocalDate.parse(args[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        GameDTO gameDTO = new GameDTO(title, price, size, trailer, thumbnailURL, description, releaseDate);
        gameDTO.validate();

        gameService.addGame(gameDTO);
        return "Added " + title;
    }

    private String loginUser(String input) {
        String email = input.split("\\|")[1];
        String password = input.split("\\|")[2];
        BasicUserDTO basicUserDTO = userService.loginUser(email, password);
        return "Successfully logged in " + basicUserDTO.getFullName();
    }

    private String registerUser(String input) {
        String[] args = input.split("\\|");
        String email = args[1];
        String password = args[2];
        String confirmPassword = args[3];
        String fullName = args[4];

        if (userService.findUserByEmail(email)) {
         throw new ValidationException("This email has already been taken");
        }

        RegisterUserDTO userDTO = new RegisterUserDTO(email, password, confirmPassword, fullName);
        userDTO.validate();
        userService.registerUser(userDTO);

        return fullName + " was registered";
    }
}

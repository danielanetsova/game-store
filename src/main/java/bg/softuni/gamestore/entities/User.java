package bg.softuni.gamestore.entities;

import bg.softuni.gamestore.customAnnotations.Password;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Required field Email!")
    @Email(message = "Please enter a valid email address!")
    @Column(unique = true)
    private String email;

    //може да си направим custom анотация за password, но може и просто да си добавим @Pattern с regex
    //@Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}")
    @Password
    @NotNull(message = "Required field Password!")
    private String password;

    @NotNull(message = "Required field Full Name!")
    @Column(name = "full_name")
    private String fullName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_games",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
    private Set<Game> games;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_logged")
    private Boolean isLogged;

    @OneToMany(targetEntity = Order.class, mappedBy = "buyer", fetch = FetchType.EAGER)
    private Set<Order> orders;

    public User() {
        this.games = new HashSet<>();
        this.orders = new HashSet<>();
        this.isAdmin = false;
        this.isLogged = false;
    }

    public User(String email, String password, String fullName) {
        this();
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setIsAdmin(Boolean b) {
        this.isAdmin = b;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void purchaseGame(Game game) {
        this.games.add(game);
    }

    public Order purchase(Game game) {
        boolean gameIsBought = this.games.contains(game);

        if (gameIsBought) {
            throw new RuntimeException("A user can buy a game only once");
        }

        Order order = new Order(this);

        for (Order o : this.orders) {
            if (o.getProducts().contains(game)) {
                throw new RuntimeException("That game has already been added to the cart");
            }
        }

        order.addProduct(game);
        this.orders.add(order);
        return order;
    }

    public Order removeGameFromCart(Game game) {
        for (Order order : orders) {
            if (order.getProducts().contains(game)) {
                order.removeProduct(game);
                return order;
            }
        }

        throw  new RuntimeException("No such game in the cart.");
    }

    public void buyAddedToCartGames() {
        for (Order order : this.orders) {
            this.games.addAll(order.getProducts());
        }

        this.orders.clear();
    }

    public String getBoughtGamesTitles() {
        StringBuilder stringBuilder = new StringBuilder("Successfully bought games:" + System.lineSeparator());

        this.games.stream().map(Game::getTitle).forEach(g ->  {
                    stringBuilder.append(" -");
                    stringBuilder.append(g);
                    stringBuilder.append(System.lineSeparator());
                });

        return stringBuilder.toString();
    }
}

package bg.softuni.gamestore.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User buyer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_products",
    joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<Game> products;

    public Order() {
        this.products = new HashSet<>();
    }

    public Order(User buyer) {
        this();
        this.buyer = buyer;
    }

    public void addProduct(Game game) {
        if (this.products.contains(game)) {
            throw new RuntimeException("This game has already been ordered");
        }
        this.products.add(game);
    }

    public Set<Game> getProducts() {
        return products;
    }

    public void removeProduct(Game game) {
        this.products.remove(game);
    }
}

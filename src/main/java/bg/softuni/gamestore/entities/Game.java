package bg.softuni.gamestore.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Pattern(regexp = "[A-Z].{2,99}", message = "Title has to begin with an uppercase letter and must " +
            "have length between 3 and 100 symbols (inclusively).")
    private String title;

    @Column(length = 11)
    @Size(min = 11, max = 11, message = "Trailer should be exactly 11 symbols")
    private String trailer;

    @URL
    @NotNull
    @Column(name = "image_thumbnail")
    private String imageThumbnail;

    @NotNull
    @Positive
    private Float size;

    @NotNull
    @Positive
    private BigDecimal price;

    @Size(min = 20, message = "Description should be at least 20 symbols")
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Game() {}

    public Game(String title, String trailer, String imageThumbnail, Float size,
                BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.imageThumbnail = imageThumbnail;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public Float getSize() {
        return size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) 
            && Objects.equals(title, game.title) 
            && Objects.equals(trailer, game.trailer) 
            && Objects.equals(imageThumbnail, game.imageThumbnail) 
            && Objects.equals(size, game.size) && Objects.equals(price, game.price) 
            && Objects.equals(description, game.description) 
            && Objects.equals(releaseDate, game.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, trailer, imageThumbnail, size, price, description, releaseDate);
    }
}

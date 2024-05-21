package bg.softuni.gamestore.dtos;

import bg.softuni.gamestore.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GameDTO {
    private String title;

    private BigDecimal price;
    private Float size;

    private String trailer;
    private String imageThumbnail;
    private String description;
    private LocalDate releaseDate;

    public GameDTO(String title,
                   BigDecimal price,
                   Float size,
                   String trailer,
                   String imageThumbnail,
                   String description,
                   LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.imageThumbnail = imageThumbnail;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public GameDTO() {}

    public void validate() {
        if (!this.title.matches("[A-Z].{2,99}")) {
            throw new ValidationException("Title has to begin with an uppercase letter and must " +
                    "have length between 3 and 100 symbols (inclusively).");
        }

        if (this.price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Price must be positive number");
        }

        if (this.size < 0) {
            throw new ValidationException("Size must be positive number");
        }

        if (this.trailer.length() != 11) {
            throw new ValidationException("Trailer must be exactly 11 characters long");
        }

        if (!this.imageThumbnail.startsWith("http://") && !this.imageThumbnail.startsWith("https://")) {
            throw new ValidationException("URL is required.");
        }

        if (description.length() < 20) {
            throw new ValidationException("Description must be at least 20 characters long");
        }
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Float getSize() {
        return size;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSize(Float size) {
        this.size = size;
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
}

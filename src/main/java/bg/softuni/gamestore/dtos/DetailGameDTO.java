package bg.softuni.gamestore.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DetailGameDTO extends AllGamesDTO {
    private String description;
    private LocalDate releaseDate;

    public DetailGameDTO() {}

    public DetailGameDTO(String title, BigDecimal price, String description, LocalDate releaseDate) {
        super(title, price);
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return String.format("Title: %s\n" +
                "Price: %.2f\n" +
                "Description: %s\n" +
                "Release date: 24-05-2016\n",
                super.getTitle(),
                super.getPrice(),
                this.description,
                this.releaseDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}

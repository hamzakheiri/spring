package fr._42.spring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "films")
@Getter
@Setter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "age_restrictions", nullable = false)
    private int ageRestrictions;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "poster_url", nullable = true)
    private String posterUrl = "";

    public Film() {}

    public Film(Long id, String title, int year, int ageRestrictions, String description, String posterUrl) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.ageRestrictions = ageRestrictions;
        this.description = description;
        this.posterUrl = posterUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return year == film.year && ageRestrictions == film.ageRestrictions && Objects.equals(id, film.id) && Objects.equals(title, film.title) && Objects.equals(description, film.description) && Objects.equals(posterUrl, film.posterUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, ageRestrictions, description, posterUrl);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", ageRestrictions=" + ageRestrictions +
                ", description='" + description + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}

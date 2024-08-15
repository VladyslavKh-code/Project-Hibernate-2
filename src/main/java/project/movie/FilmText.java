package project.movie;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "film_text", schema = "movie")
public class FilmText {
    @Id
    @Column(name = "film_id")
    private Short filmId;

    @OneToOne
    @JoinColumn(name = "film_id")
    private Film filmTextFilm;

    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Film getFilmTextFilm() {
        return filmTextFilm;
    }

    public void setFilmTextFilm(Film filmTextFilm) {
        this.filmTextFilm = filmTextFilm;
    }

    public Short getFilmId() {
        return filmId;
    }

    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text")
    @Type(type = "text")
    private String description;

}


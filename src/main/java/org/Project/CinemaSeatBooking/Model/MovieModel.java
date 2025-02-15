package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.GenreService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Data
public class MovieModel {

    private String movieId;
    private String title;
    private String description;
    private String imageUrl;
    private List<GenreModel> genreList;
    private Integer movieTime;  // Minutes (Ex: 60 = 1 hour)
    private Double movieCost;   // Revenue share percentage
    private LocalDate releaseDate;
    private Boolean isActive;

    public MovieModel() {}

    // DTO
    public MovieModel(ResultSet resultSet) throws SQLException {
        this.movieId = resultSet.getString("movie_id");
        this.title = resultSet.getString("movie_title");
        this.description = resultSet.getString("movie_description");
        this.imageUrl = resultSet.getString("image_url");

        this.genreList = new GenreService().getMany(
                "SELECT g.* FROM genre g JOIN movie_genre mg ON g.genre_id = mg.genre_id WHERE mg.movie_id = '" + this.movieId + "'"
        );

        this.movieTime = resultSet.getInt("movie_time");
        this.movieCost = resultSet.getDouble("movie_cost_percentage");

        this.releaseDate = resultSet.getObject("release_date", LocalDate.class);

        this.isActive = resultSet.getBoolean("is_active");
    }
}

package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.GenreService;
import org.Project.CinemaSeatBooking.Service.MovieService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MovieGenreModel {

    private String movieId;
    private Integer genreId;

    public MovieGenreModel() {}

    public MovieGenreModel (ResultSet resultSet) throws SQLException {
        this.movieId = resultSet.getString("movie_id");
        this.genreId = resultSet.getInt("genre_id");
    }

}
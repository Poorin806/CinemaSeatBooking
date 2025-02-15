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

    public MovieGenreModel DTO(ResultSet resultSet) throws SQLException {
        MovieGenreModel result = new MovieGenreModel();
        result.setMovieId(resultSet.getString("movie_id"));
        result.setGenreId(resultSet.getInt("genre_id"));
        return result;

    }

}
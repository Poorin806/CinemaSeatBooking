package org.Project.CinemaSeatBooking.Model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class GenreModel {

    private Integer genreId;
    private String name;

    public GenreModel() {}

    // DTO - Constructor
    public GenreModel(ResultSet resultSet) throws SQLException {
        this.genreId = resultSet.getInt("genre_id");
        this.name = resultSet.getString("genre_name");
    }

}

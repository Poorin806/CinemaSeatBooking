package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieService implements MySQLQueryInterface<MovieModel> {

    private static final GenreService genreService = new GenreService();

    @Override
    public List<MovieModel> getAll() throws SQLException {

        String sql = "SELECT * FROM movie";
        List<MovieModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new MovieModel(resultSet));
        return result;
    }

    @Override
    public List<MovieModel> getMany(String sql) throws SQLException {
        List<MovieModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);

        while (resultSet.next()) result.add(new MovieModel(resultSet));

        return result;
    }

    @Override
    public MovieModel getOne(String sql) throws SQLException {

        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.wasNull()) return null;
        return new MovieModel(resultSet);

    }
}

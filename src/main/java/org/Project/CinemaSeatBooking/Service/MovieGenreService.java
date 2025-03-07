package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.MovieGenreModel;
import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieGenreService implements MySQLQueryInterface<MovieGenreModel> {
    @Override
    public List<MovieGenreModel> getAll() throws SQLException {
        String sql = "SELECT * FROM movie_genre";

        List<MovieGenreModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);

        while (resultSet.next()) {
            result.add(new MovieGenreModel(resultSet));
        }

        return result;
    }

    @Override
    public List<MovieGenreModel> getMany(String sql) throws SQLException {
        List<MovieGenreModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);

        while (resultSet.next()) {
            result.add(new MovieGenreModel(resultSet));
        }

        return result;
    }

    @Override
    public MovieGenreModel getOne(String sql) throws SQLException {
        MovieGenreModel result = new MovieGenreModel();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.next())
            return new MovieGenreModel(resultSet);

        return null;
    }
}

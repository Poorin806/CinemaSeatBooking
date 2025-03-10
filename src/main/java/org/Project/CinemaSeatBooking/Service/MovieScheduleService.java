package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieScheduleService implements MySQLQueryInterface<MovieScheduleModel> {
    @Override
    public List<MovieScheduleModel> getAll() throws SQLException {

        String sql = "SELECT * FROM movie_schedule";
        List<MovieScheduleModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new MovieScheduleModel(resultSet));
        return result;

    }

    @Override
    public List<MovieScheduleModel> getMany(String sql) throws SQLException {
        List<MovieScheduleModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new MovieScheduleModel(resultSet));
        return result;
    }

    @Override
    public MovieScheduleModel getOne(String sql) throws SQLException {
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.next())
            return new MovieScheduleModel(resultSet);

        return null;
    }
}

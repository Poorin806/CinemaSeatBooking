package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.GenreModel;
import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreService implements MySQLQueryInterface<GenreModel> {

    @Override
    public List<GenreModel> getAll() throws SQLException {

        String sql = "SELECT * FROM genre";

        List<GenreModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new GenreModel(resultSet));

        return result;
    }

    @Override
    public List<GenreModel> getMany(String sql) throws SQLException {

        List<GenreModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new GenreModel(resultSet));
        return result;

    }

    @Override
    public GenreModel getOne(String sql) throws SQLException {

        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.next())
            return new GenreModel(resultSet);

        return null;

    }

}

package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Model.SeatModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatService implements MySQLQueryInterface<SeatModel> {
    @Override
    public List<SeatModel> getAll() throws SQLException {
        String sql = "SELECT * FROM seat";
        List<SeatModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new SeatModel(resultSet));
        return result;
    }

    @Override
    public List<SeatModel> getMany(String sql) throws SQLException {
        List<SeatModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new SeatModel(resultSet));
        return result;
    }

    @Override
    public SeatModel getOne(String sql) throws SQLException {
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.wasNull()) return null;
        return new SeatModel(resultSet);
    }
}

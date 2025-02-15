package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.SeatTypeModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatTypeService implements MySQLQueryInterface<SeatTypeModel> {
    @Override
    public List<SeatTypeModel> getAll() throws SQLException {
        String sql = "SELECT * FROM seat_type";

        List<SeatTypeModel> result = new ArrayList<>();

        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new SeatTypeModel(resultSet));

        return result;
    }

    @Override
    public List<SeatTypeModel> getMany(String sql) throws SQLException {
        List<SeatTypeModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new SeatTypeModel(resultSet));

        return result;
    }

    @Override
    public SeatTypeModel getOne(String sql) throws SQLException {
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.wasNull()) return null;
        return new SeatTypeModel(resultSet);
    }
}

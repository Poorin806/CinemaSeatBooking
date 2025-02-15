package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.LogStatusModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogStatusService implements MySQLQueryInterface<LogStatusModel> {
    @Override
    public List<LogStatusModel> getAll() throws SQLException {
        String sql = "SELECT * FROM log_status";
        List<LogStatusModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new LogStatusModel(resultSet));
        return result;
    }

    @Override
    public List<LogStatusModel> getMany(String sql) throws SQLException {
        List<LogStatusModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new LogStatusModel(resultSet));
        return result;
    }

    @Override
    public LogStatusModel getOne(String sql) throws SQLException {
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.wasNull()) return null;
        return new LogStatusModel(resultSet);
    }
}

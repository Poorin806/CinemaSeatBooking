package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements MySQLQueryInterface<TicketModel> {
    @Override
    public List<TicketModel> getAll() throws SQLException {
        String sql = "SELECT * FROM ticket";
        List<TicketModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new TicketModel(resultSet));
        return result;
    }

    @Override
    public List<TicketModel> getMany(String sql) throws SQLException {
        List<TicketModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new TicketModel(resultSet));
        return result;
    }

    @Override
    public TicketModel getOne(String sql) throws SQLException {
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.wasNull()) return null;
        return new TicketModel(resultSet);
    }
}

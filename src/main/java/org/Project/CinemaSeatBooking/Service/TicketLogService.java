package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.TicketLogModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketLogService implements MySQLQueryInterface<TicketLogModel> {
    @Override
    public List<TicketLogModel> getAll() throws SQLException {
        String sql = "SELECT * FROM ticket_log";
        List<TicketLogModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new TicketLogModel(resultSet));
        return result;
    }

    @Override
    public List<TicketLogModel> getMany(String sql) throws SQLException {
        List<TicketLogModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new TicketLogModel(resultSet));
        return result;
    }

    @Override
    public TicketLogModel getOne(String sql) throws SQLException {
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.wasNull()) return null;
        return new TicketLogModel(resultSet);
    }

}

package org.Project.CinemaSeatBooking.Service;

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
        if (resultSet.next())
            return new TicketModel(resultSet);

        return null;
    }

    public Boolean cancelTicket(String ticketId) throws SQLException {
        String sql = "UPDATE ticket t SET t.is_active = false WHERE t.ticket_id = '" + ticketId + "'";
        return MySQLConnection.query(sql) >= 1;
    }
}
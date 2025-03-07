package org.Project.CinemaSeatBooking.Service;

import org.Project.CinemaSeatBooking.Model.GenreModel;
import org.Project.CinemaSeatBooking.Model.RoomModel;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomService implements MySQLQueryInterface<RoomModel> {
    @Override
    public List<RoomModel> getAll() throws SQLException {
        String sql = "SELECT * FROM room";

        List<RoomModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new RoomModel(resultSet));

        return result;
    }

    @Override
    public List<RoomModel> getMany(String sql) throws SQLException {
        List<RoomModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new RoomModel(resultSet));

        return result;
    }

    @Override
    public RoomModel getOne(String sql) throws SQLException {
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.next())
            return new RoomModel(resultSet);

        return null;
    }
}

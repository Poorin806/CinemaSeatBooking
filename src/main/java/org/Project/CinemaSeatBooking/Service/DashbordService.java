package org.Project.CinemaSeatBooking.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.Project.CinemaSeatBooking.Model.DashbordModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

public class DashbordService implements MySQLQueryInterface<DashbordModel>{
    @Override
    public List<DashbordModel> getAll() throws SQLException {

        // String sql = "SELECT * FROM genre";

        // List<DashbordModel> result = new ArrayList<>();
        // ResultSet resultSet = MySQLConnection.fetchData(sql);
        // while (resultSet.next()) result.add(new DashbordModel(resultSet));

        return null;
    }

    @Override
    public List<DashbordModel> getMany(String sql) throws SQLException {

        List<DashbordModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new DashbordModel(resultSet));
        return result;

    }

    @Override
    public DashbordModel getOne(String sql) throws SQLException {

        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.wasNull()) return null;
        return new DashbordModel(resultSet);

    }
}

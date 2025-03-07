package org.Project.CinemaSeatBooking.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.Project.CinemaSeatBooking.Model.DashboardVIPModel;
import org.Project.CinemaSeatBooking.Model.DashboardVIPModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

public class DashboardVIPService implements MySQLQueryInterface<DashboardVIPModel>{
    @Override
    public List<DashboardVIPModel> getAll() throws SQLException {

        // String sql = "SELECT * FROM genre";

        // List<DashboardVIPModel> result = new ArrayList<>();
        // ResultSet resultSet = MySQLConnection.fetchData(sql);
        // while (resultSet.next()) result.add(new DashboardVIPModel(resultSet));

        return null;
    }

    @Override
    public List<DashboardVIPModel> getMany(String sql) throws SQLException {

        List<DashboardVIPModel> result = new ArrayList<>();
        ResultSet resultSet = MySQLConnection.fetchData(sql);
        while (resultSet.next()) result.add(new DashboardVIPModel(resultSet));
        return result;

    }

    @Override
    public DashboardVIPModel getOne(String sql) throws SQLException {

        ResultSet resultSet = MySQLConnection.fetchData(sql);
        if (resultSet.wasNull()) return null;
        return new DashboardVIPModel(resultSet);

    }
}

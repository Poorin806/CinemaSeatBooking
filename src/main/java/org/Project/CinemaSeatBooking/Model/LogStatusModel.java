package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Data
public class LogStatusModel {

    private String logStatusId;

    private String name;

    public LogStatusModel() {}

    public LogStatusModel(ResultSet resultSet) throws SQLException {

        this.logStatusId = resultSet.getString("log_status_id");
        this.name = resultSet.getString("log_status_name");

    }

}

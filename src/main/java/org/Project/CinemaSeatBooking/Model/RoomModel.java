package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.bson.Document;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class RoomModel {

    private String roomId;

    private String name;

    public RoomModel() {}

    public RoomModel(ResultSet resultSet) throws SQLException {

        this.roomId = resultSet.getString("room_id");
        this.name = resultSet.getString("room_name");

    }

}

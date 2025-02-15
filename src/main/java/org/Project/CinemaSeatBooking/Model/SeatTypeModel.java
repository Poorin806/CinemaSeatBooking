package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.bson.Document;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class SeatTypeModel {

    private Integer seatTypeId;

    private String name;

    private Double price;

    public SeatTypeModel() {}

    public SeatTypeModel(ResultSet resultSet) throws SQLException {

        this.seatTypeId = resultSet.getInt("seat_type_id");
        this.name = resultSet.getString("seat_type_name");
        this.price = resultSet.getDouble("seat_price");

    }

}

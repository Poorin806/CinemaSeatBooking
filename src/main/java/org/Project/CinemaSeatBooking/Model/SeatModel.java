package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.RoomService;
import org.Project.CinemaSeatBooking.Service.SeatTypeService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
public class SeatModel {

    private Integer seatId;  // Unique identifier for Mongo DB

    private RoomModel roomModel;

    private String row; // Seat row (Ex: A, B, C, etc)

    private Integer number; // Number of rows (Ex: A1, B1, C2, etc)

    private SeatTypeModel seatTypeModel;

    // Lazy loading
    private static List<RoomModel> roomModelList = null;
    private static List<SeatTypeModel> seatTypeModelList = null;

    public SeatModel() throws SQLException {}

    public SeatModel(ResultSet resultSet) throws SQLException {

        this.seatId = resultSet.getInt("seat_id");

        String roomId = resultSet.getString("room_id");
        if (roomModelList == null) roomModelList = new RoomService().getAll();
        for (RoomModel tmp : roomModelList) {
            if (tmp.getRoomId().equals(roomId)) this.roomModel = tmp;
        }

        this.row = resultSet.getString("seat_row");
        this.number = resultSet.getInt("seat_number");

        int seatTypeId = resultSet.getInt("seat_type_id");
        if (seatTypeModelList == null) seatTypeModelList = new SeatTypeService().getAll();
        for (SeatTypeModel tmp : seatTypeModelList) {
            if (tmp.getSeatTypeId() == seatTypeId) this.seatTypeModel = tmp;
        }

    }

}

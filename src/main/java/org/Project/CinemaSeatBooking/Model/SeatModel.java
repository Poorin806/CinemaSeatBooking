package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.RoomService;
import org.Project.CinemaSeatBooking.Service.SeatTypeService;
import org.bson.Document;

@Data
public class SeatModel {

    private String SeatId;  // Unique identifier for Mongo DB

    private RoomModel roomModel;

    private String Row; // Seat row (Ex: A, B, C, etc)

    private Integer Number; // Number of rows (Ex: A1, B1, C2, etc)

    private SeatTypeModel seatTypeModel;

    public SeatModel DTO(Document doc) {
        SeatModel seatModel = new SeatModel();

        seatModel.setSeatId(doc.getObjectId("_id").toString());

        // Sub DTO Room Model
        RoomService roomService = new RoomService();
        RoomModel roomModelTmp = roomService.FindOneById(doc.getString("RoomId"));
        seatModel.setRoomModel(roomModelTmp);

        seatModel.setRow(doc.getString("Row"));
        seatModel.setNumber(doc.getInteger("Number"));

        // Sub DTO SeatType Model
        SeatTypeService seatTypeService = new SeatTypeService();
        SeatTypeModel seatTypeModelTmp = seatTypeService.FindOneById(doc.getString("SeatTypeId"));
        seatModel.setSeatTypeModel(seatTypeModelTmp);

        return seatModel;
    }

}

package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.bson.Document;

@Data
public class RoomModel {

    private String RoomId;

    private String Name;

    public RoomModel DTO(Document doc) {
        RoomModel roomModel = new RoomModel();
        roomModel.setRoomId(doc.getObjectId("_id").toString());
        roomModel.setName(doc.getString("Name"));
        return roomModel;
    }

}

package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.bson.Document;

@Data
public class SeatTypeModel {

    private String SeatTypeId;

    private String Name;

    private Double Price;

    public SeatTypeModel DTO(Document doc) {

        SeatTypeModel seatTypeModel = new SeatTypeModel();
        seatTypeModel.setSeatTypeId(doc.getObjectId("_id").toString());
        seatTypeModel.setName(doc.getString("Name"));
        seatTypeModel.setPrice(doc.getDouble("Price"));

        return seatTypeModel;
    }

}

package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
@Data
public class LogStatusModel {

    private String LogStatusId;

    private String Name;

    public LogStatusModel DTO(Document doc) {

        LogStatusModel logStatusModel = new LogStatusModel();

        logStatusModel.setLogStatusId(
                doc.getObjectId("_id").toString()
        );
        logStatusModel.setName(doc.getString("Name"));

        return logStatusModel;

    }

}

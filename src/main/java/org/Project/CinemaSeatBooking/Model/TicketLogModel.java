package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.LogStatusService;
import org.Project.CinemaSeatBooking.Service.SeatService;
import org.Project.CinemaSeatBooking.Service.TicketService;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class TicketLogModel {

    private String _id; // Unique identifier for Mongo DB

    private TicketModel ticketModel;

    private LocalDateTime Timestamp; // Format: yyyy-MM-dd HH:mm:ss

    private LogStatusModel logStatusModel;

    private SeatModel CurrentSeat;

    private SeatModel NewSeat;

    private String Note;

    public TicketLogModel DTO(Document doc) {

        TicketLogModel ticketLogModel = new TicketLogModel();

        ticketLogModel.set_id(
                doc.getObjectId("_id").toString()
        );

        // Sub DTO Ticket Model
        TicketService ticketService = new TicketService();
        ticketLogModel.setTicketModel(ticketService.FindOneById(
                doc.get("TicketId").toString()
        ));

        if (doc.getDate("Timestamp") != null) {
            ticketLogModel.setTimestamp(
                    doc.getDate("Timestamp").toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
            );
        }

        // Sub DTO Log Status
        LogStatusService logStatusService = new LogStatusService();
        ticketLogModel.setLogStatusModel(
                logStatusService.FindOneById(
                        doc.getString("LogStatusId")
                )
        );

        // Sub DTO Seat Model
        SeatService seatService = new SeatService();
        ticketLogModel.setCurrentSeat(
                doc.getString("CurrentSeat") == null ? null : seatService.FindOneById(
                        doc.getString("CurrentSeat")
                )
        );

        ticketLogModel.setNewSeat(
                doc.getString("NewSeat") == null ? null : seatService.FindOneById(
                        doc.getString("NewSeat")
                )
        );

        ticketLogModel.setNote(
                doc.getString("Note")
        );

        return ticketLogModel;

    }

}

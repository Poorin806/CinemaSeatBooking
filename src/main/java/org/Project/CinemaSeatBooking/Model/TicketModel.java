package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.MovieScheduleService;
import org.Project.CinemaSeatBooking.Service.SeatService;
import org.bson.Document;

@Data
public class TicketModel {

    private String TicketId;

    private MovieScheduleModel movieScheduleModel;

    private Double TotalPrice;  // Calculated price after adding seats + movies price

    private SeatModel seatModel;

    private String Customer;    // Optional

    private Boolean IsActive;   // Checking if the ticket is active or not (true = active, false = canceled tickets)

    public TicketModel DTO(Document doc) {

        TicketModel ticketModel = new TicketModel();

        ticketModel.setTicketId(doc.getObjectId("_id").toString());

        // Sub DTO Movie Schedule Model
        MovieScheduleService movieScheduleService = new MovieScheduleService();
        MovieScheduleModel movieScheduleModelTmp = movieScheduleService.FindOneById(
                doc.getString("ScheduleId")
        );
        ticketModel.setMovieScheduleModel(movieScheduleModelTmp);

        ticketModel.setTotalPrice(doc.getDouble("TotalPrice"));
        ticketModel.setCustomer(
                doc.getString("Customer") != null ? doc.getString("Customer") : null
        );

        ticketModel.setIsActive(doc.getBoolean("IsActive"));

        // Sub DTO Seat Model
        SeatService seatService = new SeatService();
        SeatModel seatModelTmp = seatService.FindOneById(
                doc.getString("SeatId")
        );
        ticketModel.setSeatModel(seatModelTmp);

        return ticketModel;

    }

}

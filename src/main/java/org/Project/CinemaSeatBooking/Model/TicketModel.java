package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.MovieScheduleService;
import org.Project.CinemaSeatBooking.Service.SeatService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
public class TicketModel {

    private String ticketId;

    private MovieScheduleModel movieScheduleModel;

    private Double totalPrice;  // Calculated price after adding seats + movies price

    private SeatModel seatModel;

    private String customer;    // Optional

    private Boolean isActive;   // Checking if the ticket is active or not (true = active, false = canceled tickets)

    private static List<MovieScheduleModel> movieScheduleModelList = null;
    private static List<SeatModel> seatModelList = null;

    public TicketModel() {}

    public TicketModel(ResultSet resultSet) throws SQLException {

        this.ticketId = resultSet.getString("ticket_id");

        String movieScheduleId = resultSet.getString("movie_schedule_id");
        if (movieScheduleModelList == null) movieScheduleModelList = new MovieScheduleService().getAll();
        for (MovieScheduleModel tmp : movieScheduleModelList)
            if (tmp.getScheduleId().equals(movieScheduleId)) this.movieScheduleModel = tmp;

        this.totalPrice = resultSet.getDouble("total_price");

        int seatId = resultSet.getInt("seat_id");
        if (seatModelList == null) seatModelList = new SeatService().getAll();
        for (SeatModel tmp : seatModelList)
            if (tmp.getSeatId() == seatId) this.seatModel = tmp;

        this.customer = resultSet.getString("customer_name");
        this.isActive = resultSet.getBoolean("is_active");

    }

}

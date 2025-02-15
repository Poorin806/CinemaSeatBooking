package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.LogStatusService;
import org.Project.CinemaSeatBooking.Service.SeatService;
import org.Project.CinemaSeatBooking.Service.TicketService;
import org.bson.Document;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
public class TicketLogModel {

    private String ticketLogId; // Unique identifier for Mongo DB

    private TicketModel ticketModel;

    private LocalDateTime timeStamp; // Format: yyyy-MM-dd HH:mm:ss

    private LogStatusModel logStatusModel;

    private SeatModel currentSeat;

    private SeatModel newSeat;

    private String note;

    private static List<TicketModel> ticketModelList = null;
    private static List<LogStatusModel> logStatusModelList = null;
    private static List<SeatModel> seatModelList = null;

    public TicketLogModel() {}

    public TicketLogModel(ResultSet resultSet) throws SQLException {

        this.ticketLogId = resultSet.getString("ticket_log_id");

        String ticketId = resultSet.getString("ticket_id");
        if (ticketModelList == null) ticketModelList = new TicketService().getAll();
        for (TicketModel tmp : ticketModelList)
            if (tmp.getTicketId().equals(ticketId)) this.ticketModel = tmp;

        this.timeStamp = resultSet.getObject("time_stamp", LocalDateTime.class);

        String logStatusId = resultSet.getString("log_status_id");
        if (logStatusModelList == null) logStatusModelList = new LogStatusService().getAll();
        for (LogStatusModel tmp : logStatusModelList)
            if (tmp.getLogStatusId().equals(logStatusId)) this.logStatusModel = tmp;

        int currentSeat = resultSet.getInt("current_seat");
        int newSeat = resultSet.getInt("newSeat");
        if (seatModelList == null) seatModelList = new SeatService().getAll();
        for (SeatModel tmp : seatModelList) {
            if (tmp.getSeatId() == currentSeat) this.currentSeat = tmp;
            if (tmp.getSeatId() == newSeat) this.newSeat = tmp;
        }

        this.note = resultSet.getString("note");
    }

}

package org.Project.CinemaSeatBooking.Utils;

import lombok.Getter;
import lombok.Setter;
import org.Project.CinemaSeatBooking.Model.TicketModel;

import java.util.ArrayList;
import java.util.List;

public class UserTicket {

    @Getter
    @Setter
    private static List<TicketModel> userTicketList = new ArrayList<>();

    public static int getTicketCount() {
        return userTicketList.size();
    }

    public static void addTicketData(TicketModel ticketModel) {
        userTicketList.add(ticketModel);
    }

    public static void findAndUpdate(TicketModel ticketModel) {

        for (int i = 0; i < userTicketList.size(); i++) {

            TicketModel existingTicket = userTicketList.get(i);

            if (existingTicket.getTicketId().equals(ticketModel.getTicketId())) {
                userTicketList.set(i, ticketModel);
                break;
            }

        }

    }


}

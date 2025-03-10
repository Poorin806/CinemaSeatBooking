package org.Project.CinemaSeatBooking.Model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class DashboardVIPModel {

    private String vipTitle;
    private int vipTicketsSold;
    private int vipRegularTicketsSold;
    public DashboardVIPModel() {}

    // DTO - Constructor
    public DashboardVIPModel(ResultSet resultSet) throws SQLException {
        this.vipTitle = resultSet.getString("vip_title");
        this.vipTicketsSold = resultSet.getInt("vip_tickets_sold");
        this.vipRegularTicketsSold = resultSet.getInt("regular_tickets_sold");
    }

}

package org.Project.CinemaSeatBooking.Model;
import lombok.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class DashbordModel {

    private String MovieTitle;
    private String TicketSold;
    private double TotalSales;
    private double Percen;

    public DashbordModel() {}

    // DTO - Constructor
    public DashbordModel(ResultSet resultSet) throws SQLException {
        this.MovieTitle = resultSet.getString("movie_title");
        this.TicketSold = resultSet.getString("total_tickets_sold");
        this.TotalSales = resultSet.getDouble("total_sales");
        this.Percen = resultSet.getDouble("percentage_tickets_sold");
    }
}

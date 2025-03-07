package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

@Data
public class DashbordModel {

    private String movieTitle;
    private String image;
    private int ticketSold;
    private double totalSales;
    private double percentageSold;

    private String monthYear;
    private int totalTicketsSold;
    private double totalTicketSales;

    public DashbordModel() {}

    public DashbordModel(ResultSet resultSet) throws SQLException {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);

                if (columnName.equals("movie_title")) {
                    this.movieTitle = resultSet.getString("movie_title");
                }
                if (columnName.equals("image_url")) {
                    this.image = resultSet.getString("image_url");
                }
                if (columnName.equals("total_tickets_sold")) {
                    this.ticketSold = resultSet.getInt("total_tickets_sold");
                }
                if (columnName.equals("total_sales")) {
                    this.totalSales = resultSet.getDouble("total_sales");
                }
                if (columnName.equals("percentage_tickets_sold")) {
                    this.percentageSold = resultSet.getDouble("percentage_tickets_sold");
                }
                if (columnName.equals("Year")) {
                    this.monthYear = resultSet.getString("Year");
                }
                if (columnName.equals("total_tickets_solds")) {
                    this.totalTicketsSold = resultSet.getInt("total_tickets_solds");
                }
                if (columnName.equals("total_saless")) {
                    this.totalTicketSales = resultSet.getDouble("total_saless");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error mapping DashbordModel: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package org.Project.CinemaSeatBooking.GUI;

import javax.swing.*;
import org.Project.CinemaSeatBooking.Model.DashbordModel;
import org.Project.CinemaSeatBooking.Service.DashbordService;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DashboardGUI {

    private static final JPanel homeContent = new JPanel(new BorderLayout());
    
    public static JPanel get() throws SQLException {

        homeContent.setPreferredSize(new Dimension(824, 768));
        homeContent.setBackground(new Color(73, 73, 73));
        homeContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        homeContent.add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBackground(new Color(73, 73, 73));

        JPanel topMovieList = new JPanel(new BorderLayout());
        topMovieList.setBackground(new Color(73, 73, 73));
        topMovieList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel topMovieListTitle = new JLabel("Top 4 Movies", SwingConstants.LEFT);
        topMovieListTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topMovieListTitle.setForeground(Color.WHITE);
        topMovieList.add(topMovieListTitle, BorderLayout.NORTH);

        JPanel topMovieItems = new JPanel(new GridLayout(1, 4, 10, 10));
        topMovieItems.setBackground(new Color(73, 73, 73));

        JPanel imcomeBorder = new JPanel(new BorderLayout());
        imcomeBorder.setBackground(new Color(73, 73, 73));
        imcomeBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel incomeTitle = new JLabel("Total Income", SwingConstants.LEFT);
        incomeTitle.setFont(new Font("Arial", Font.BOLD, 24));
        incomeTitle.setForeground(Color.WHITE);
        imcomeBorder.add(incomeTitle, BorderLayout.NORTH);

        JPanel incomeItem = new JPanel(new GridLayout(1, 2, 10, 10));
        incomeItem.setBackground(new Color(73, 73, 73));

        List<DashbordModel> dashboardData = new DashbordService().getMany("SELECT " +
                "    m.movie_title, " +
                "    COUNT(t.ticket_id) AS total_tickets_sold, " +
                "    SUM(t.total_price) AS total_sales, " +
                "    ROUND((COUNT(t.ticket_id) / total_tickets.total_tickets * 100), 2) AS percentage_tickets_sold " +
                "FROM " +
                "    ticket t " +
                "JOIN " +
                "    movie_schedule ms ON t.movie_schedule_id = ms.movie_schedule_id " +
                "JOIN " +
                "    movie m ON ms.movie_id = m.movie_id " +
                "JOIN " +
                "    (SELECT COUNT(ticket_id) AS total_tickets, SUM(total_price) AS total_sales " +
                "     FROM ticket " +
                "     WHERE is_active = TRUE) total_tickets " +
                "ON 1=1 " +
                "WHERE " +
                "    t.is_active = TRUE " +
                "GROUP BY " +
                "    m.movie_title " +
                "LIMIT 25;");
        int count = 0;
        for (DashbordModel model : dashboardData) {
            JPanel card = new JPanel(new GridLayout(4, 1, 5, 5));
            card.setPreferredSize(new Dimension(180, 200));
            card.setBackground(new Color(97, 97, 97));
            card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

            JLabel movieName = new JLabel(model.getMovieTitle(), SwingConstants.CENTER);
            movieName.setFont(new Font("Arial", Font.BOLD, 16));
            movieName.setForeground(Color.WHITE);
            card.add(movieName);

            JLabel ticketsSold = new JLabel("Tickets Sold: " + model.getTicketSold(), SwingConstants.CENTER);
            ticketsSold.setForeground(Color.WHITE);
            ticketsSold.setFont(new Font("Arial", Font.PLAIN, 14));
            card.add(ticketsSold);

            JLabel totalSales = new JLabel("Total Sales: $" + model.getTotalSales(), SwingConstants.CENTER);
            totalSales.setForeground(Color.WHITE);
            totalSales.setFont(new Font("Arial", Font.PLAIN, 14));
            card.add(totalSales);

            JLabel percentageSold = new JLabel("Percentage Sold: " + model.getPercen() + "%", SwingConstants.CENTER);
            percentageSold.setForeground(Color.WHITE);
            percentageSold.setFont(new Font("Arial", Font.PLAIN, 14));
            card.add(percentageSold);

            topMovieItems.add(card);
            // incomeItem.add(card);
            count++;
        }
        DashbordModel model = new DashbordModel();
        for () {
            JPanel cardIncome = new JPanel(new GridLayout(4, 1, 5, 5));
            cardIncome.setPreferredSize(new Dimension(180, 200));
            cardIncome.setBackground(new Color(97, 97, 97));
            cardIncome.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            JLabel movieName = new JLabel(model.getMovieTitle(), SwingConstants.CENTER);
            movieName.setFont(new Font("Arial", Font.BOLD, 16));
            movieName.setForeground(Color.WHITE);
            cardIncome.add(movieName);

            JLabel ticketsSold = new JLabel("Tickets Sold: " + model.getTicketSold(), SwingConstants.CENTER);
            ticketsSold.setForeground(Color.WHITE);
            ticketsSold.setFont(new Font("Arial", Font.PLAIN, 14));
            cardIncome.add(ticketsSold);

            JLabel totalSales = new JLabel("Total Sales: $" + model.getTotalSales(), SwingConstants.CENTER);
            totalSales.setForeground(Color.WHITE);
            totalSales.setFont(new Font("Arial", Font.PLAIN, 14));
            cardIncome.add(totalSales);

            JLabel percentageSold = new JLabel("Percentage Sold: " + model.getPercen() + "%", SwingConstants.CENTER);
            percentageSold.setForeground(Color.WHITE);
            percentageSold.setFont(new Font("Arial", Font.PLAIN, 14));
            cardIncome.add(percentageSold);

            // topMovieItems.add(card);
            incomeItem.add(cardIncome);
            count++;
        }

        topMovieList.add(topMovieItems, BorderLayout.CENTER);
        imcomeBorder.add(incomeItem, BorderLayout.CENTER);

        mainPanel.add(topMovieList);
        mainPanel.add(imcomeBorder);

        homeContent.add(mainPanel, BorderLayout.CENTER);
        homeContent.setVisible(false);

        return homeContent;
    }

    public static void show() {
        homeContent.setVisible(true);
    }

    public static void close() {
        homeContent.setVisible(false);
    }
}
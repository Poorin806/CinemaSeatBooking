package org.Project.CinemaSeatBooking.GUI;

import javax.swing.*;
import org.Project.CinemaSeatBooking.Model.DashbordModel;
import org.Project.CinemaSeatBooking.Service.DashbordService;
import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Model.DashboardVIPModel;
import org.Project.CinemaSeatBooking.Service.DashboardVIPService;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DashboardGUI {

    private static final JPanel homeContent = new JPanel(new BorderLayout());

    // EZ
    public static JPanel get() throws SQLException {
        homeContent.setVisible(true);
        return homeContent;
    }

    public static void show() {
        homeContent.setVisible(true);
    }

    public static void close() {
        homeContent.setVisible(false);
    }

    private static List<DashbordModel> getTopMovies() {
        List<DashbordModel> dashboardData = new ArrayList<>();
        try {
            dashboardData = new DashbordService().getMany("SELECT " +
                    "    m.movie_title, " +
                    "    m.image_url, " +
                    "    COUNT(t.ticket_id) AS total_tickets_sold, " +
                    "    SUM(CASE WHEN st.seat_type_name = 'VIP' THEN 300 ELSE t.total_price END) AS total_sales, " +
                    "    ROUND((COUNT(t.ticket_id) / total_tickets.total_tickets * 100), 2) AS percentage_tickets_sold, "
                    +
                    "    SUM(CASE WHEN st.seat_type_name = 'VIP' THEN 1 ELSE 0 END) AS total_vip_tickets_sold, " +
                    "    SUM(CASE WHEN st.seat_type_name = 'VIP' THEN 300 ELSE 0 END) AS total_vip_sales " +
                    "FROM ticket t " +
                    "JOIN movie_schedule ms ON t.movie_schedule_id = ms.movie_schedule_id " +
                    "JOIN movie m ON ms.movie_id = m.movie_id " +
                    "JOIN seat s ON t.seat_id = s.seat_id " +
                    "JOIN seat_type st ON s.seat_type_id = st.seat_type_id " +
                    "JOIN (SELECT COUNT(ticket_id) AS total_tickets FROM ticket WHERE is_active = TRUE) total_tickets ON 1=1 "
                    +
                    "WHERE t.is_active = TRUE " +
                    "GROUP BY m.movie_title, m.image_url " +
                    "ORDER BY total_sales DESC " +
                    "LIMIT 4;");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching top movies: " + e.getMessage());
        }
        return dashboardData;
    }

    private static List<DashbordModel> getMonthlyIncome() {
        List<DashbordModel> dashboardData = new ArrayList<>();
        try {
            dashboardData = new DashbordService().getMany("SELECT " +
                    "    DATE_FORMAT(ms.show_time, '%Y') AS Year, " +
                    "    COUNT(t.ticket_id) AS total_tickets_solds, " +
                    "    SUM(t.total_price) AS total_saless " +
                    "FROM ticket t " +
                    "JOIN movie_schedule ms ON t.movie_schedule_id = ms.movie_schedule_id " +
                    "WHERE t.is_active = TRUE " +
                    "GROUP BY Year " +
                    "ORDER BY Year;");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage());
        }
        return dashboardData;
    }

    private static List<DashboardVIPModel> getTopMoviesVIP() {
        List<DashboardVIPModel> dashboardData = new ArrayList<>();
        try {
            dashboardData = new DashboardVIPService().getMany("SELECT " +
                    "    m.movie_title AS vip_title, " +
                    "    SUM(CASE WHEN st.seat_type_name = 'VIP' THEN 1 ELSE 0 END) AS vip_tickets_sold, " +
                    "    SUM(CASE WHEN st.seat_type_name != 'VIP' THEN 1 ELSE 0 END) AS regular_tickets_sold " +
                    "FROM ticket t " +
                    "JOIN seat s ON t.seat_id = s.seat_id " +
                    "JOIN seat_type st ON s.seat_type_id = st.seat_type_id " +
                    "JOIN movie_schedule ms ON t.movie_schedule_id = ms.movie_schedule_id " +
                    "JOIN movie m ON ms.movie_id = m.movie_id " +
                    "WHERE t.is_active = TRUE " +
                    "GROUP BY m.movie_title " +
                    "ORDER BY vip_tickets_sold DESC " +
                    "LIMIT 1;");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching top VIP movie data: " + e.getMessage());
        }
        return dashboardData;
    }

    public static void resetScreen() throws SQLException {

        homeContent.removeAll();
        homeContent.setPreferredSize(new Dimension(824, 768));
        homeContent.setBackground(new Color(73, 73, 73));
        homeContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        homeContent.add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBackground(new Color(73, 73, 73));

        // topmovie
        JPanel topMovieList = new JPanel(new BorderLayout());
        topMovieList.setBackground(new Color(73, 73, 73));
        topMovieList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel topMovieListTitle = new JLabel("Top 4 Movies", SwingConstants.LEFT);
        topMovieListTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topMovieListTitle.setForeground(Color.WHITE);
        topMovieListTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        topMovieList.add(topMovieListTitle, BorderLayout.NORTH);

        JPanel topMovieItems = new JPanel(new GridLayout(1, 4, 10, 20));
        topMovieItems.setBackground(new Color(73, 73, 73));

        // income
        JPanel imcomeList = new JPanel(new BorderLayout());
        imcomeList.setBackground(new Color(73, 73, 73));
        imcomeList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel incomeTitle = new JLabel("Total Income", SwingConstants.LEFT);
        incomeTitle.setFont(new Font("Arial", Font.BOLD, 24));
        incomeTitle.setForeground(Color.WHITE);
        imcomeList.add(incomeTitle, BorderLayout.NORTH);

        JPanel incomeItem = new JPanel(new GridLayout(1, 3, 10, 10));
        incomeItem.setBackground(new Color(73, 73, 73));
        incomeItem.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        List<DashbordModel> dashboardData = getTopMovies();

        for (DashbordModel model : dashboardData) {
            JPanel card = new JPanel(new BorderLayout());
            card.setPreferredSize(new Dimension(200, 550));
            card.setBackground(new Color(97, 97, 97));
            card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            JPanel imagePanel = new JPanel(new BorderLayout());
            imagePanel.setPreferredSize(new Dimension(200, 150));
            imagePanel.setBackground(new Color(0, 0, 0));

            BackgroundImageJPanel movieImg = new BackgroundImageJPanel();
            movieImg.setBackground(new Color(0, 0, 0, 0));
            movieImg.setPreferredSize(new Dimension(200, 300));
            movieImg.setImage(model.getImage(), false);
            imagePanel.add(movieImg, BorderLayout.CENTER);

            JPanel movieDetails = new JPanel(new GridLayout(4, 1, 10, 10));
            movieDetails.setPreferredSize(new Dimension(200, 250));
            movieDetails.setBackground(new Color(97, 97, 97));
            movieDetails.setBorder(BorderFactory.createEmptyBorder(11, 11, 12, 11));

            JLabel movieName = new JLabel(model.getMovieTitle(), SwingConstants.CENTER);
            movieName.setFont(new Font("Arial", Font.BOLD, 18));
            movieName.setForeground(Color.CYAN);
            movieDetails.add(movieName);

            JLabel ticketsSold = new JLabel("Tickets Sold: " + model.getTicketSold(), SwingConstants.CENTER);
            ticketsSold.setForeground(Color.WHITE);
            ticketsSold.setFont(new Font("Arial", Font.PLAIN, 12));
            movieDetails.add(ticketsSold);

            JLabel totalSales = new JLabel("Total Sales :" + model.getTotalSales(), SwingConstants.CENTER);
            totalSales.setForeground(Color.WHITE);
            totalSales.setFont(new Font("Arial", Font.PLAIN, 12));
            movieDetails.add(totalSales);

            JLabel percentageSold = new JLabel("Percentage Sold: " + model.getPercentageSold() + "%",
                    SwingConstants.CENTER);
            percentageSold.setForeground(Color.WHITE);
            percentageSold.setFont(new Font("Arial", Font.PLAIN, 12));
            movieDetails.add(percentageSold);

            card.add(imagePanel, BorderLayout.NORTH);
            card.add(movieDetails, BorderLayout.CENTER);

            topMovieItems.add(card);
        }
        List<DashbordModel> monthly = getMonthlyIncome();
        if (!monthly.isEmpty()) {
            DashbordModel model = monthly.get(0);

            JPanel card1 = new JPanel(new BorderLayout());
            card1.setPreferredSize(new Dimension(250, 250));
            card1.setBackground(new Color(97, 97, 97));
            card1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            JPanel incomeMonthlyDetails = new JPanel(new GridLayout(4, 1, 10, 10));
            incomeMonthlyDetails.setBackground(new Color(97, 97, 97));

            JLabel title = new JLabel("Total Years Income", SwingConstants.CENTER);
            title.setForeground(Color.CYAN);
            title.setFont(new Font("Arial", Font.BOLD, 20));
            incomeMonthlyDetails.add(title);

            JLabel monthYear = new JLabel("Years : " + model.getMonthYear(), SwingConstants.CENTER);
            monthYear.setForeground(Color.WHITE);
            monthYear.setFont(new Font("Arial", Font.BOLD, 14));
            incomeMonthlyDetails.add(monthYear);

            JLabel ticketsSold = new JLabel("Tickets Sold: " + model.getTotalTicketsSold(), SwingConstants.CENTER);
            ticketsSold.setForeground(Color.WHITE);
            ticketsSold.setFont(new Font("Arial", Font.PLAIN, 12));
            incomeMonthlyDetails.add(ticketsSold);

            JLabel totalSales = new JLabel("Total Sales: " + model.getTotalTicketSales(), SwingConstants.CENTER);
            totalSales.setForeground(Color.WHITE);
            totalSales.setFont(new Font("Arial", Font.PLAIN, 12));
            incomeMonthlyDetails.add(totalSales);

            card1.add(incomeMonthlyDetails, BorderLayout.CENTER);
            incomeItem.add(card1);
        }

        // income2
        List<DashboardVIPModel> topVipMovie = getTopMoviesVIP();
        if (!topVipMovie.isEmpty()) {
            DashboardVIPModel model = topVipMovie.get(0);
            JPanel vipSeatList = new JPanel(new BorderLayout());
            vipSeatList.setPreferredSize(new Dimension(250, 250));
            vipSeatList.setBackground(new Color(80, 80, 80));
            vipSeatList.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            JPanel vipSeatDetails = new JPanel(new GridLayout(4, 1, 10, 10));
            vipSeatDetails.setBackground(new Color(97, 97, 97));

            JLabel viptitle = new JLabel("Best Movie Vip Seat", SwingConstants.CENTER);
            viptitle.setForeground(Color.CYAN);
            viptitle.setFont(new Font("Arial", Font.BOLD, 20));
            vipSeatDetails.add(viptitle);

            JLabel vipSeatTitle = new JLabel("Movie : " + model.getVipTitle(), SwingConstants.CENTER);
            vipSeatTitle.setForeground(Color.WHITE);
            vipSeatTitle.setFont(new Font("Arial", Font.BOLD, 14));
            vipSeatDetails.add(vipSeatTitle);

            JLabel vipSeatSold = new JLabel("VIP Seat Sold : " + model.getVipTicketsSold(), SwingConstants.CENTER);
            vipSeatSold.setForeground(Color.WHITE);
            vipSeatSold.setFont(new Font("Arial", Font.PLAIN, 14));
            vipSeatDetails.add(vipSeatSold);

            JLabel vipRegularSeatSold = new JLabel("Regular Seat Sold : " + model.getVipRegularTicketsSold(),
                    SwingConstants.CENTER);
            vipRegularSeatSold.setForeground(Color.WHITE);
            vipRegularSeatSold.setFont(new Font("Arial", Font.PLAIN, 14));
            vipSeatDetails.add(vipRegularSeatSold);

            vipSeatList.add(vipSeatDetails, BorderLayout.CENTER);
            incomeItem.add(vipSeatList);
        }

        topMovieList.add(topMovieItems, BorderLayout.CENTER);
        imcomeList.add(incomeItem, BorderLayout.CENTER);

        mainPanel.add(topMovieList);
        mainPanel.add(imcomeList);

        homeContent.add(mainPanel, BorderLayout.CENTER);

        homeContent.revalidate();
        homeContent.repaint();
    }


}

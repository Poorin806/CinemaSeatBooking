package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Model.*;
import org.Project.CinemaSeatBooking.Service.SeatService;
import org.Project.CinemaSeatBooking.Service.TicketService;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;
import org.Project.CinemaSeatBooking.Utils.UserTicket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChangingSeatGUI {

    private static final JPanel content = new JPanel();
    private static JLabel movieTitle = new JLabel();
    private static JPanel seatPanel;

    private static List<String> seatId = new ArrayList<>();
    private static List<JToggleButton> seatButtons = new ArrayList<>();
    private static List<String> seatBooking = new ArrayList<>();
    private static TicketModel currentTicket = new TicketModel();

    private static MovieScheduleModel movieScheduleData = new MovieScheduleModel();
    private static List<SeatModel> seatModelArrayList = new ArrayList<>();

    public static JPanel get() {
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); // ใช้ BoxLayout แนวตั้ง
        content.setPreferredSize(new Dimension(824, 768));
        content.setBackground(new Color(73, 73, 73));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // กำหนด Padding ให้กับ content

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(824, 50));
        headerPanel.setMaximumSize(new Dimension(824, 50));

        movieTitle.setFont(new Font("Arial", Font.BOLD, 28));
        movieTitle.setHorizontalAlignment(SwingConstants.CENTER);
        movieTitle.setForeground(Color.WHITE);

        headerPanel.add(movieTitle, BorderLayout.CENTER);

        // Banner Panel
        JPanel bannerPanel = new JPanel();
        bannerPanel.setPreferredSize(new Dimension(824, 224));
        bannerPanel.setMaximumSize(new Dimension(824, 224));
        bannerPanel.setBackground(Color.LIGHT_GRAY);

        // Seat Panel
        seatPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5)); // ใช้ GridLayout
        seatPanel.setPreferredSize(new Dimension(824, 500));
        seatPanel.setMaximumSize(new Dimension(824, 500));
        seatPanel.setOpaque(false);

        seatId.clear();

        for (int i = 1; i <= 76; i++) seatId.add(Integer.toString(i));

        int rows = 0;
        int index = 0;

        JPanel cartPanel = new JPanel();
        cartPanel.setOpaque(false);
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.X_AXIS));
        cartPanel.setPreferredSize(new Dimension(824, 50)); // ขนาดของ cartPanel
        cartPanel.setMaximumSize(new Dimension(824, 50));
        JButton cartBtn = new JButton("Change Seat");
        cartBtn.setPreferredSize(new Dimension(250, 50));
        cartBtn.setMaximumSize(new Dimension(250, 50));
        cartBtn.setBackground(new Color(217, 217, 217));
        cartBtn.setFocusPainted(false);
        cartBtn.setBorderPainted(false);
        cartPanel.add(Box.createHorizontalGlue());
        cartPanel.add(cartBtn);
        cartPanel.add(Box.createHorizontalGlue());

        cartBtn.addActionListener(e -> {
            try {
                cartSeatBooking();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        content.add(headerPanel);
        content.add(Box.createVerticalStrut(20));
        content.add(bannerPanel);
        content.add(Box.createVerticalStrut(20));
        content.add(seatPanel);
        content.add(Box.createVerticalStrut(20));
        content.add(cartPanel);

        return content;
    }

    public static void clearSeatSelections() {
        for (JToggleButton button : seatButtons) {
            button.setSelected(false);
        }
    }

    public static void setMovieData(TicketModel ticketModel, MovieModel movieModel, MovieScheduleModel movieScheduleModel) throws SQLException {
        movieTitle.setText(movieModel.getTitle());
        movieScheduleData = movieScheduleModel;
        currentTicket = ticketModel;

        String sql = "SELECT * FROM seat WHERE seat.room_id = '" + movieScheduleModel.getRoomModel().getRoomId() + "'";
        seatModelArrayList = new SeatService().getMany(sql);

        seatPanel.removeAll();
        seatButtons.clear();
        setSeatData();
        content.revalidate();
        content.repaint();

        seatBooking.clear();
    }

    private static void cartSeatBooking() throws SQLException {

        if (seatBooking.isEmpty()) {
            JOptionPane.showMessageDialog(content, "Please select a seat before changing", "Cinema Seat Booking", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (seatBooking.size() > 1) {
            JOptionPane.showMessageDialog(content, "Cannot change more than 1 seat", "Cinema Seat Booking", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<SeatModel> confirmSeatBookingData = seatModelArrayList.stream()
                .filter(data -> seatBooking.contains(String.valueOf(data.getSeatId())))
                .toList();

        String msgBody = "[Confirm your seat changing]";
        double totalPrice = 0.00d;
        for (SeatModel tmp : confirmSeatBookingData) {
            msgBody += "\n- " + tmp.getRow() + tmp.getNumber() + " (" + tmp.getSeatTypeModel().getName() + " - " + tmp.getSeatTypeModel().getPrice() + ")";
            totalPrice += tmp.getSeatTypeModel().getPrice();
        }
        if (totalPrice <= currentTicket.getTotalPrice()) {
            msgBody += "\n\n ** Total: - **";
        } else {
            double priceDifference = currentTicket.getTotalPrice() - totalPrice;
            msgBody += String.format("\n\n ** Total: %d... (The seat you chose has a different price) **", priceDifference);
        }

        int choice = JOptionPane.showConfirmDialog( // 0 = OK, 2 = Cancel
                content,
                msgBody,
                "Cinema Seat Booking",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (choice == 0) {

            for (String s : seatBooking) {

                System.out.println("Seat ID: " + s);

                Optional<SeatModel> seatData = seatModelArrayList.stream().filter(data -> data.getSeatId() == Integer.parseInt(s)).findFirst();
                if (seatData.isPresent()) {

                    String logPk = MySQLConnection.genreratePK();
                    int currentSeat = currentTicket.getSeatModel().getSeatId();
                    int newSeat = seatData.get().getSeatId();
                    String ticketId = currentTicket.getTicketId();

                    // Insert to log
                    String logSql = "INSERT INTO ticket_log VALUES ('" + logPk + "', '" + ticketId + "', '" + LocalDateTime.now() + "', 1, " + currentSeat + ", " + newSeat + ", null)";
                    if (MySQLConnection.query(logSql) > 0) {
                        String ticketSql = "UPDATE ticket t SET t.movie_schedule_id = '" + movieScheduleData.getScheduleId() + "', t.seat_id = " + newSeat + ", t.total_price = " + seatData.get().getSeatTypeModel().getPrice() + " WHERE t.ticket_id = '" + ticketId + "'";
                        if (MySQLConnection.query(ticketSql) > 0) {

                            // Update user ticket cart
                            String updatedTicketSql = "SELECT * FROM ticket t WHERE t.ticket_id = '" + ticketId + "'";
                            TicketModel updatedTicketData = new TicketService().getOne(updatedTicketSql);
                            UserTicket.findAndUpdate(updatedTicketData);

                            System.out.println("Change seat successfully");
                        } else {
                            System.err.println("Failed to change seat");
                        }
                    } else {
                        System.err.println("Failed to add log");
                    }

                } else {
                    System.out.println("Seat ID " + s + " not found");
                }

            }

            JOptionPane.showMessageDialog(content, "Thank you for purchasing, We hope you will enjoy!", "Cinema Seat Booking", JOptionPane.INFORMATION_MESSAGE);
            HomeGUI.changeToHome();

        }

    }

    private static void setSeatData() throws SQLException {

        String sql = "SELECT * FROM ticket t WHERE t.movie_schedule_id = '" + movieScheduleData.getScheduleId() + "' AND t.is_active = true";
        List<TicketModel> ticketModelList = new TicketService().getMany(sql);

        List<Integer> bookedSeat = ticketModelList.stream()
                .map(ticket -> ticket.getSeatModel().getSeatId())
                .toList();

        String[] headerSeatText = {"A", "B", "C", "D", "E", "F"};
        int currentHeader = 0;
        int index = 0;

        for (SeatModel seat : seatModelArrayList) {
            if (index % 14 == 0) {
                JToggleButton headerSeat = new JToggleButton();
                headerSeat.setText(headerSeatText[currentHeader]);
                headerSeat.setOpaque(true);
                headerSeat.setEnabled(false);
                headerSeat.setBackground(new Color(54, 84, 59));
                headerSeat.setForeground(Color.WHITE);
                headerSeat.setFont(new Font("Arial", Font.BOLD, 12));
                headerSeat.setPreferredSize(new Dimension(45, 45));
                headerSeat.setMaximumSize(new Dimension(45, 45));
                headerSeat.setFocusPainted(false);
                headerSeat.setBorderPainted(false);

                seatPanel.add(headerSeat);
                currentHeader++;
            }

            JToggleButton seatBtn = new JToggleButton();
            seatBtn.setText(seat.getNumber().toString());
            seatBtn.setFont(new Font("Arial", Font.PLAIN, 8));
            seatBtn.setOpaque(true);

            if (ticketModelList.isEmpty()) {
                seatBtn.setBackground(new Color(219, 219, 219));
                seatBtn.setEnabled(true);
                seatBtn.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("Seat Booking: " + seat.getRow() + seat.getNumber());
                        seatBooking.add(seat.getSeatId().toString());
                    } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                        System.out.println("Cancel Booking: " + seat.getRow() + seat.getNumber());
                        seatBooking.remove(seat.getSeatId().toString());
                    }
                });
            } else {

                if (bookedSeat.contains(seat.getSeatId())) {
                    seatBtn.setBackground(new Color(96, 96, 96));
                    seatBtn.setForeground(Color.white);
                    seatBtn.setEnabled(false);
                } else {
                    seatBtn.setBackground(new Color(219, 219, 219));
                    seatBtn.setEnabled(true);
                    seatBtn.addItemListener(e -> {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            System.out.println("Seat Booking: " + seat.getRow() + seat.getNumber());
                            seatBooking.add(seat.getSeatId().toString());
                        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                            System.out.println("Cancel Booking: " + seat.getRow() + seat.getNumber());
                            seatBooking.remove(seat.getSeatId().toString());
                        }
                    });
                }
            }

            seatBtn.setPreferredSize(new Dimension(45, 45));
            seatBtn.setMaximumSize(new Dimension(45, 45));
            seatBtn.setFocusPainted(false);
            seatBtn.setBorderPainted(false);
            seatButtons.add(seatBtn);

            seatPanel.add(seatBtn);

            index++;
        }

    }

}
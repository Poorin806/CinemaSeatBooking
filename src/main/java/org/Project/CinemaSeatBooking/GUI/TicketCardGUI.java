package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Utils.EssentialsUtils;
import org.Project.CinemaSeatBooking.Utils.UserTicket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TicketCardGUI {

    private static final JPanel content = new JPanel(new BorderLayout());
    private static final JLabel titleLabel = new JLabel("Your tickets");
    private static final JPanel ticketListPanel = new JPanel();
    private static final JScrollPane scrollPane = new JScrollPane();

    private static List<TicketModel> ticketModelList = new ArrayList<>();

    public static JPanel get() {

        content.setPreferredSize(new Dimension(824, 768));
        content.setBackground(new Color(73, 73, 73));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        content.add(titleLabel, BorderLayout.NORTH);

        return content;
    }

    private static JPanel createTicketCard(TicketModel ticketModel) {

        JPanel ticketData = new JPanel(new BorderLayout());
        ticketData.setPreferredSize(new Dimension(824, 150));
        ticketData.setMaximumSize(new Dimension(824, 150)); // ปรับขนาด Ticket Card
        ticketData.setBackground(new Color(109, 109, 109));
        ticketData.setBorder(new EmptyBorder(10, 10, 10, 10));

        BackgroundImageJPanel movieImg = new BackgroundImageJPanel();
        movieImg.setImage(
                ticketModel.getMovieScheduleModel().getMovieModel().getImageUrl(), false);
        movieImg.setPreferredSize(new Dimension(80, 150));
        ticketData.add(movieImg, BorderLayout.WEST);

        JLabel movieTitle = new JLabel(
                ticketModel.getMovieScheduleModel().getMovieModel().getTitle()
        );
        JLabel scheduleLabel = new JLabel(
                "Screening time: " + EssentialsUtils.formatDateTime(
                        ticketModel.getMovieScheduleModel().getShowTime(), true
                )
        );
        JLabel theaterLabel = new JLabel("Theater: " + ticketModel.getMovieScheduleModel().getRoomModel().getName());
        JLabel seatLabel = new JLabel("Seat: " + ticketModel.getSeatModel().getRow() + ticketModel.getSeatModel().getNumber());

        movieTitle.setForeground(Color.WHITE);
        movieTitle.setFont(new Font("Arial", Font.BOLD, 20));

        for (JLabel label : new JLabel[] {
                scheduleLabel, theaterLabel, seatLabel
        }) {
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.ITALIC, 14));
        }

        JPanel movieContentPanel = new JPanel();
        movieContentPanel.setLayout(new BoxLayout(movieContentPanel, BoxLayout.Y_AXIS));
        movieContentPanel.setOpaque(false);
        movieContentPanel.setBorder(
                new EmptyBorder(5, 15, 0, 0)
        );
        movieContentPanel.add(movieTitle);
        movieContentPanel.add(Box.createVerticalStrut(5));
        movieContentPanel.add(scheduleLabel);
        movieContentPanel.add(Box.createVerticalStrut(5));
        movieContentPanel.add(theaterLabel);
        movieContentPanel.add(Box.createVerticalStrut(5));
        movieContentPanel.add(seatLabel);
        movieContentPanel.add(Box.createVerticalStrut(10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(
                new EmptyBorder(0, 90, 0, 0)
        );
        JButton cancelBtn = new JButton("Cancel Booking");
        JButton changeBtn = new JButton("Change Seat");
        for (JButton btn : new JButton[] {
                cancelBtn, changeBtn
        }) {
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(73, 73, 73));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setPreferredSize(new Dimension(150, 30));
            btn.setMaximumSize(new Dimension(150, 30));
        }
        buttonPanel.add(changeBtn);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelBtn);

        ticketData.add(movieContentPanel, BorderLayout.CENTER);
        ticketData.add(buttonPanel, BorderLayout.SOUTH);

        return ticketData;
    }

    public static void refreshData() {
        ticketModelList = UserTicket.getUserTicketList();
//        System.out.println(ticketModelList.toString());

        ticketListPanel.removeAll();
        ticketListPanel.setOpaque(false);
        ticketListPanel.setLayout(new BoxLayout(ticketListPanel, BoxLayout.Y_AXIS));

        if (!ticketModelList.isEmpty()) {
            for (TicketModel ticketModel : ticketModelList) {
                ticketListPanel.add(createTicketCard(ticketModel));
                ticketListPanel.add(Box.createVerticalStrut(10));
            }
        } else {
            JLabel emptyCartLabel = new JLabel("You have no tickets");
            emptyCartLabel.setForeground(Color.WHITE);
            emptyCartLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            emptyCartLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setOpaque(false);
            emptyPanel.add(emptyCartLabel, BorderLayout.CENTER);

            ticketListPanel.add(emptyPanel);
        }

        content.remove(scrollPane);

        scrollPane.setViewportView(ticketListPanel);
        scrollPane.setOpaque(false);
        scrollPane.setFocusable(false);
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        content.add(scrollPane, BorderLayout.CENTER);

        ticketListPanel.revalidate();
        ticketListPanel.repaint();

        scrollPane.revalidate();
        scrollPane.repaint();

        content.revalidate();
        content.repaint();
    }

}

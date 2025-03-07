package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Service.MovieScheduleService;
import org.Project.CinemaSeatBooking.Service.TicketService;
import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Utils.EssentialsUtils;
import org.Project.CinemaSeatBooking.Utils.UserTicket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketCardGUI {

    private static final JPanel content = new JPanel(new BorderLayout());
    private static final JLabel titleLabel = new JLabel("Your tickets");
    private static final JPanel ticketListPanel = new JPanel();
    private static final JScrollPane scrollPane = new JScrollPane();

    private static List<TicketModel> ticketModelList = new ArrayList<>();
    private static List<MovieScheduleModel> movieScheduleModelList = new ArrayList<>();

    private static final TicketService ticketService = new TicketService();

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

        changeBtn.addActionListener(e -> {
            try {
                selectBookingDetail(
                        ticketModel,
                        ticketModel.getMovieScheduleModel().getMovieModel()
                );
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        cancelBtn.addActionListener(e -> {
            try {
                cancelTicket(ticketModel);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

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

    private static void cancelTicket(TicketModel ticketModel) throws SQLException {

        int cancelOption = JOptionPane.showConfirmDialog(
                HomeGUI.getRootFrame(),
                "Are you sure you want to cancel this booking",
                "Cinema Seat Booking",
                JOptionPane.YES_NO_OPTION
        );

        if (cancelOption == JOptionPane.YES_OPTION) {

            if (ticketService.cancelTicket(ticketModel.getTicketId())) {
                JOptionPane.showMessageDialog(
                        HomeGUI.getRootFrame(),
                        "Canceled",
                        "Cinema Seat Booking",
                        JOptionPane.INFORMATION_MESSAGE
                );

                UserTicket.deleteTicketData(ticketModel);
                refreshData();
            } else {
                JOptionPane.showMessageDialog(
                        HomeGUI.getRootFrame(),
                        "Something went wrong, please read the log",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }

    }

    private static void selectBookingDetail(TicketModel ticketModel, MovieModel movieData) throws SQLException {

        JFrame rootFrame = HomeGUI.getRootFrame();
        JDialog popUpDialog = new JDialog(rootFrame, "Select Movie Booking Detail (Changing)", true);
        popUpDialog.setSize(760, 500);
        popUpDialog.setLayout(new BorderLayout());

        // Container Panel
        JPanel popUpContainer = new JPanel();
        popUpContainer.setOpaque(true);
        popUpContainer.setBackground(new Color(51, 51, 51));
        popUpContainer.setLayout(new BoxLayout(popUpContainer, BoxLayout.Y_AXIS));
        popUpContainer.setSize(popUpDialog.getWidth(), popUpDialog.getHeight());
        popUpContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(popUpDialog.getWidth(), 100));

        JLabel movieTitleLabel = new JLabel(movieData.getTitle());
        movieTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel movieDescriptionLabel = new JLabel(movieData.getDescription());
        movieDescriptionLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        JLabel releaseDateLabel = new JLabel("Release Date: " + EssentialsUtils.formatDate(movieData.getReleaseDate().toString()));
        releaseDateLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        for (JLabel tmp : new JLabel[]{ movieTitleLabel, movieDescriptionLabel, releaseDateLabel }) {
            tmp.setForeground(Color.WHITE);
        }

        JPanel movieDetailsPanel = new JPanel();
        movieDetailsPanel.setLayout(new BoxLayout(movieDetailsPanel, BoxLayout.Y_AXIS));
        movieDetailsPanel.setOpaque(false);
        movieDetailsPanel.add(Box.createVerticalGlue());
        movieDetailsPanel.add(movieTitleLabel);
        movieDetailsPanel.add(Box.createVerticalStrut(5));
        movieDetailsPanel.add(movieDescriptionLabel);
        movieDetailsPanel.add(Box.createVerticalStrut(5));
        movieDetailsPanel.add(releaseDateLabel);
        movieDetailsPanel.add(Box.createVerticalGlue());

        BackgroundImageJPanel movieImg = new BackgroundImageJPanel();
        movieImg.setPreferredSize(new Dimension(75, 100));
        movieImg.setBackground(new Color(217, 217, 217));
        movieImg.setImage(movieData.getImageUrl(), false);
        headerPanel.add(movieImg);
        headerPanel.add(movieDetailsPanel);

        // Selection Panel
        JPanel selectionPanel = new JPanel();
        selectionPanel.setOpaque(false);
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setPreferredSize(new Dimension(popUpDialog.getWidth(), 400));
        selectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Schedule list
        JPanel selectionContentPanel = new JPanel(new BorderLayout());
        selectionContentPanel.setPreferredSize(new Dimension(popUpDialog.getWidth(), 360));

        String sql = "SELECT * FROM movie_schedule ms WHERE ms.movie_id = '" + movieData.getMovieId() + "'";
        movieScheduleModelList = new MovieScheduleService().getMany(sql);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (MovieScheduleModel tmp : movieScheduleModelList) {
            String schedule = tmp.getScheduleId() + ", ";
            schedule += EssentialsUtils.formatDateTime(tmp.getShowTime(), true) + " - ";
            schedule += EssentialsUtils.formatDateTime(tmp.getEndTime(), true) + " [";
            schedule += tmp.getRoomModel().getName() + "]";
            listModel.addElement(schedule);
        }
        JList listOfSchedule = new JList(listModel);
        listOfSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfSchedule.setLayoutOrientation(JList.VERTICAL_WRAP);
        listOfSchedule.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(listOfSchedule);
        listScroller.setPreferredSize(new Dimension(popUpDialog.getWidth(), 360));
        selectionContentPanel.add(listScroller, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton closeBtn = new JButton("Cancel");
        JButton continueBtn = new JButton("Booking");
        closeBtn.addActionListener(e -> popUpDialog.dispose());
        continueBtn.addActionListener(e -> {
            if (listOfSchedule.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(rootFrame, "Please select the screening time", "Warnings", JOptionPane.WARNING_MESSAGE);
            } else {
                popUpDialog.dispose();
                try {
                    HomeGUI.changeToChangingSeat(
                            ticketModel,
                            movieData,
                            movieScheduleModelList.get(listOfSchedule.getSelectedIndex())
                    );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        for (JButton btn : new JButton[]{closeBtn, continueBtn}) {

            btn.setPreferredSize(new Dimension(200, 40));
            btn.setMaximumSize(new Dimension(200, 40));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setBackground(new Color(217, 217, 217));
            btn.setForeground(new Color(51, 51, 51));
            btn.setFont(new Font("Arial", Font.BOLD, 14));

        }
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(760, 40));
        buttonPanel.add(continueBtn);
        buttonPanel.add(closeBtn);

        selectionPanel.add(selectionContentPanel);
        selectionPanel.add(Box.createVerticalStrut(10));
        selectionPanel.add(buttonPanel);

        popUpContainer.add(headerPanel);
        popUpContainer.add(Box.createVerticalStrut(10));
        popUpContainer.add(selectionPanel);

        popUpDialog.add(popUpContainer, BorderLayout.CENTER);
        popUpDialog.setLocationRelativeTo(rootFrame);
        popUpDialog.setVisible(true);

    }

}

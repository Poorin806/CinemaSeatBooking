package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Model.GenreModel;
import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Service.MovieScheduleService;
import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Utils.EssentialsUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailGUI {

    private static final JPanel content = new JPanel(new BorderLayout());

    private static final JLabel titleLabel = new JLabel();
    private static final JLabel tagLabel = new JLabel("Tag: .........................");
    private static final JLabel releaseDateLabel = new JLabel("Release Date: ......");
    private static final JLabel showTimeLabel = new JLabel("Show time: ................");
    private static final JTextArea descriptionLabel = new JTextArea(
            "....");

    private static String imgURL = "";
    private static BackgroundImageJPanel movieImg = new BackgroundImageJPanel();

    private static MovieModel movieData = new MovieModel();
    private static List<MovieScheduleModel> movieScheduleModelList = new ArrayList<>();

    public static JPanel get() {

        content.setPreferredSize(new Dimension(824, 768)); // ขนาดพื้นที่ของ content
        content.setBackground(new Color(73, 73, 73));
        content.setBorder(new EmptyBorder(150, 20, 150, 20));

        JPanel mainGrid = new JPanel(new GridLayout(1, 2, 20, 0));
        mainGrid.setOpaque(false);

        movieImg.setBackground(new Color(0, 0, 0, 0));
        movieImg.setBackground(Color.LIGHT_GRAY);

        JPanel detailGrid = new JPanel(new GridLayout(2, 1, 0, 10));
        detailGrid.setOpaque(false);

        JPanel descriptionPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        descriptionPanel.setOpaque(false);

        JPanel movieTitlePanel = new JPanel();
        movieTitlePanel.setOpaque(false);
        movieTitlePanel.setLayout(new BoxLayout(movieTitlePanel, BoxLayout.Y_AXIS));

        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        tagLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        tagLabel.setForeground(Color.WHITE);

        titleLabel.setPreferredSize(new Dimension(500, 50)); // ขนาดที่เหมาะสม
        tagLabel.setPreferredSize(new Dimension(500, 30));

        movieTitlePanel.add(titleLabel);
        movieTitlePanel.add(Box.createVerticalStrut(5));
        movieTitlePanel.add(tagLabel);


        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setBackground(new Color(0, 0, 0, 0));
        descriptionLabel.setEditable(false);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setPreferredSize(new Dimension(500, 100));

        descriptionPanel.add(movieTitlePanel);
        descriptionPanel.add(descriptionLabel);

        JPanel managementPanel = new JPanel();
        managementPanel.setOpaque(false);
        managementPanel.setLayout(new BoxLayout(managementPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setMaximumSize(new Dimension(500, 50));

        JButton bookingBtn = new JButton("Booking");
        JButton favBtn = new JButton("Add to favorites");
        for (JButton btn : new JButton[]{bookingBtn, favBtn}) {
            btn.setMaximumSize(new Dimension(150, 40)); // กำหนดขนาดที่เหมาะสม
            btn.setBackground(new Color(217, 217, 217));
            btn.setForeground(new Color(0, 0, 0));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
        }

        buttonPanel.add(bookingBtn);
//        buttonPanel.add(Box.createHorizontalStrut(10));
//        buttonPanel.add(favBtn);
        // Button Events listeners
        bookingBtn.addActionListener(e -> {
            selectBookingDetail(movieData);
        });

        releaseDateLabel.setForeground(Color.WHITE);
        showTimeLabel.setForeground(Color.WHITE);
        releaseDateLabel.setFont(new Font("Arial", Font.ITALIC, 16));

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setOpaque(false);
        footerPanel.setPreferredSize(new Dimension(500, 40));
        footerPanel.setMaximumSize(new Dimension(500, 40));
        releaseDateLabel.setForeground(Color.WHITE);
        showTimeLabel.setForeground(Color.WHITE);
        footerPanel.add(releaseDateLabel);

        managementPanel.add(buttonPanel);
        managementPanel.add(Box.createVerticalStrut(20));
        managementPanel.add(footerPanel);

        detailGrid.add(descriptionPanel, BorderLayout.NORTH);
        detailGrid.add(managementPanel, BorderLayout.SOUTH);

        mainGrid.add(movieImg);
        mainGrid.add(detailGrid);

        content.add(mainGrid, BorderLayout.CENTER);

        return content;
    }

    public static void setMovieData(MovieModel movieModel) throws SQLException {
        titleLabel.setText(movieModel.getTitle());
        releaseDateLabel.setText("Release Date: " + EssentialsUtils.formatDate(movieModel.getReleaseDate().toString()));
        descriptionLabel.setText(movieModel.getDescription());

        String tagList = "";
        for (GenreModel tmp : movieModel.getGenreList()) {
            tagList += tmp.getName() + " ";
        }
        tagLabel.setText("Tags: " + tagList);

        imgURL = movieModel.getImageUrl();
        movieImg.setImage(imgURL, false);  // เปลี่ยนรูปภาพแบบไดนามิก
        movieImg.revalidate();
        movieImg.repaint();

        movieData = movieModel;

        // Set movie schedule list
        String sql = "SELECT * FROM movie_schedule ms WHERE ms.movie_id = '" + movieModel.getMovieId() + "'";
        movieScheduleModelList = new MovieScheduleService().getMany(sql);
    }

    private static void selectBookingDetail(MovieModel movieData) {

        JFrame rootFrame = HomeGUI.getRootFrame();
        JDialog popUpDialog = new JDialog(rootFrame, "Select Movie Booking Detail", true);
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
                    HomeGUI.changeToSeatBooking(
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

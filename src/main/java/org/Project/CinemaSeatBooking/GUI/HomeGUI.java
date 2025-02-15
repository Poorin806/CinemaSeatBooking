package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Model.MovieModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class HomeGUI {

    private static final JPanel homeContent;

    static {
        try {
            homeContent = HomeContentGUI.get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final CardLayout cardLayout = new CardLayout();

    private static final JPanel container = new JPanel(new BorderLayout());
    private static final JPanel cards = new JPanel(cardLayout);
    private static final JFrame frame = new JFrame();

    public static void show() throws SQLException {

        frame.getContentPane().setBackground(new Color(20, 20, 20));
        frame.setSize(1124, 868);
        frame.setMinimumSize(new Dimension(1124, 868));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cinema Seat Booking");

        // App Icon
        Image appIcon = Toolkit.getDefaultToolkit().getImage(
                HomeGUI.class.getClassLoader().getResource("AppIcon.png")
        );
        frame.setIconImage(appIcon);

        // Container layout settings
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Make container centered

        // Main Container [For fixed size of content]
        container.setPreferredSize(new Dimension(1024, 768)); // fixed size
        container.setBackground(new Color(51, 51, 51, 255));

        JPanel sideBar = UserSideBarGUI.get();


        container.add(sideBar, BorderLayout.WEST);

        // Dynamics Content
        cards.add(HomeContentGUI.get(), "homeContent");
        cards.add(MovieDetailGUI.get(), "movieDetail");
        cards.add(SeatBookingGUI.get(), "seatBooking");
        container.add(cards, BorderLayout.CENTER);
        cardLayout.show(cards, "homeContent");

        // Default show content
        HomeContentGUI.show();

        // Add container into frame
        frame.add(container, gbc);

        // Windows listener
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // TODO: Close database connection
                frame.dispose();
            }
        });

        // Make JFrame visible
        frame.setVisible(true);

    }

    public static void changeToHome() {
        cardLayout.show(cards, "homeContent");
    }

    public static void changeToMovieDetail(MovieModel movieModel) throws SQLException {
        MovieDetailGUI.setMovieData(movieModel);
        cardLayout.show(cards, "movieDetail");
    }

    public static void changeToSeatBooking(MovieModel data) {
        SeatBookingGUI.setMovieData(data);
        SeatBookingGUI.clearSeatSelections();
        cardLayout.show(cards, "seatBooking");
    }

    public static JFrame getRootFrame() {
        return frame;
    }

}

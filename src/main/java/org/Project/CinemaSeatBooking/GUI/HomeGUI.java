package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

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
        frame.setLocationRelativeTo(null);

        // App Icon
        Image appIcon = Toolkit.getDefaultToolkit().getImage(
                HomeGUI.class.getClassLoader().getResource("AppIcon.png"));
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

        // TODO: Change to admin Sidebar when changeToAdminDashboard calling...
        JPanel sideBar = UserSideBarGUI.get();
        JPanel adminSideBar = AdminSideBarGUI.get(homeContent);

        container.add(sideBar, BorderLayout.WEST);

        // Dynamics Content
        cards.add(HomeContentGUI.get(), "homeContent");
        cards.add(AllMovieGUI.get(), "allMovie");
        cards.add(MovieDetailGUI.get(), "movieDetail");
        cards.add(SeatBookingGUI.get(), "seatBooking");
        cards.add(TicketCardGUI.get(), "ticketCard");
        cards.add(DashboardGUI.get(), "dashboard");
        cards.add(MovieManagementGUI.get(), "movieManagement");
        cards.add(TheaterManagementGUI.get(), "theaterManagement");
        cards.add(ChangingSeatGUI.get(), "seatChanging");
        cards.add(RoomEditGUI.get(), "editRoom");
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
                MySQLConnection.closeConnection();
                frame.dispose();
            }
        });

        // Make JFrame visible
        frame.setVisible(true);

    }

    public static void changeToHome() {

        // ลบ Component ทั้งหมดในตำแหน่ง Sidebar ก่อน
        container.removeAll();

        // เพิ่ม UserSideBarGUI กลับเข้าไปที่ตำแหน่ง WEST (ฝั่งซ้าย)
        JPanel userSideBar = UserSideBarGUI.get();
        container.add(userSideBar, BorderLayout.WEST);

        // เพิ่มเนื้อหาหลัก (cards) กลับเข้ามาที่ CENTER (ฝั่งกลาง)
        container.add(cards, BorderLayout.CENTER);

        // รีเฟรชและวาดใหม่
        container.revalidate();
        container.repaint();

        // แสดงหน้า Home
        cardLayout.show(cards, "homeContent");
    }

    public static void changeToAllMovie() throws SQLException {
        AllMovieGUI.refreshData();
        cardLayout.show(cards, "allMovie");
    }
    public static void changeToEditRoom(String theaterName) throws SQLException {
        RoomEditGUI.setData(theaterName);
        cardLayout.show(cards, "editRoom");
    }

    public static void changeToMovieDetail(MovieModel movieModel) throws SQLException {
        MovieDetailGUI.setMovieData(movieModel);
        cardLayout.show(cards, "movieDetail");
    }

    public static void changeToSeatBooking(MovieModel movieModel, MovieScheduleModel movieScheduleModel)
            throws SQLException {
        SeatBookingGUI.setMovieData(movieModel, movieScheduleModel);
        SeatBookingGUI.clearSeatSelections();
        cardLayout.show(cards, "seatBooking");
    }

    public static void changeToChangingSeat(TicketModel ticketModel, MovieModel movieModel,
            MovieScheduleModel movieScheduleModel) throws SQLException {
        ChangingSeatGUI.setMovieData(ticketModel, movieModel, movieScheduleModel);
        ChangingSeatGUI.clearSeatSelections();
        cardLayout.show(cards, "seatChanging");
    }

    public static void changeToTicketCard() {
        TicketCardGUI.refreshData();
        cardLayout.show(cards, "ticketCard");
    }

    public static void changeToAdminDashboard(boolean fromLoginBtn) {

        if (fromLoginBtn) {
            container.remove(0);

            JPanel adminSideBar = AdminSideBarGUI.get(homeContent);
            container.add(adminSideBar, BorderLayout.WEST);

        }
        try {
            DashboardGUI.resetScreen();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cardLayout.show(cards, "dashboard");
    }

    public static void changeToMovieManagement() {
        cardLayout.show(cards, "movieManagement");
    }

    public static void changeToTheaterManagement() {
        cardLayout.show(cards, "theaterManagement");
    }

    public static JFrame getRootFrame() {
        return frame;
    }

    

	

}

package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DashboardGUI {

    public static void show() {

        // Create JFrame (App GUI)
        JFrame frame = new JFrame();
        frame.setSize(1124, 868);
        frame.setMinimumSize(new Dimension(1124, 868));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cinema Seat Booking");

        // Container layout settings
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Make container centered

        // Main Container [For fixed size of content]
        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(new Dimension(1024, 768)); // fixed size
        container.setBackground(Color.RED);

        // Sidebar panel (300px width)
        JPanel sideBar = new JPanel(new GridLayout(5, 1, 10, 30));
        sideBar.setPreferredSize(new Dimension(200, 768)); // Fixed width and height
        sideBar.setBackground(Color.GREEN);
        JButton menu1 = new JButton("Menu 1");
        JButton menu2 = new JButton("Menu 2");
        JButton menu3 = new JButton("Menu 3");
        JButton menu4 = new JButton("Menu 4");
        JButton menu5 = new JButton("Menu 5");
        sideBar.add(menu1);
        sideBar.add(menu2);
        sideBar.add(menu3);
        sideBar.add(menu4);
        sideBar.add(menu5);

        // Main content area
        JPanel dashboardContent = new JPanel(new BorderLayout());
        dashboardContent.setPreferredSize(new Dimension(824, 768)); // Remaining space
        dashboardContent.setBackground(Color.BLUE);

        JPanel dashboardCard = new JPanel(new GridLayout(1, 4, 10, 10));
        dashboardCard.setPreferredSize(new Dimension(dashboardContent.getWidth(), 120));
        dashboardCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        dashboardCard.setBackground(Color.YELLOW);
        JButton btn1 = new JButton("BTN 1");
        JButton btn2 = new JButton("BTN 1");
        JButton btn3 = new JButton("BTN 1");
        JButton btn4 = new JButton("BTN 1");
        dashboardCard.add(btn1);
        dashboardCard.add(btn2);
        dashboardCard.add(btn3);
        dashboardCard.add(btn4);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.LIGHT_GRAY);
        content.setPreferredSize(new Dimension(dashboardContent.getWidth(), dashboardContent.getHeight() - dashboardCard.getHeight()));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        dashboardContent.add(dashboardCard, BorderLayout.NORTH);
        dashboardContent.add(content, BorderLayout.CENTER);

        // Add panels to container
        container.add(sideBar, BorderLayout.WEST);
        container.add(dashboardContent, BorderLayout.CENTER); // ใช้ CENTER แทน EAST

        // Add container into frame
        frame.add(container, gbc);


        // Windows listener
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // MongoDB close-connection
                MongoDBConnection.Disconnect();
                frame.dispose();
            }
        });

        // Make JFrame visible
        frame.setVisible(true);

    }

}

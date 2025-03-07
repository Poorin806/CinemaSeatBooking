package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class UserSideBarGUI {

    public static JPanel get() {

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());
        sideBar.setPreferredSize(new Dimension(200, 768));
        sideBar.setBackground(new Color(51, 51, 51, 255));
        sideBar.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel sideBarButtonPanel = new JPanel();
        sideBarButtonPanel.setLayout(new BoxLayout(sideBarButtonPanel, BoxLayout.Y_AXIS));
        sideBarButtonPanel.setOpaque(false);

        Dimension buttonSize = new Dimension(180, 50); // ขนาดปุ่มคงที่

        JButton homeBtn = new JButton("Home");
        JButton movieBtn = new JButton("Movie");
        JButton exitBtn = new JButton("Exit");

        // Event listeners
        homeBtn.addActionListener(e -> {
            HomeGUI.changeToHome();
        });

        movieBtn.addActionListener(e -> {
            try {
                HomeGUI.changeToAllMovie();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        exitBtn.addActionListener(e -> {
            int isConfirmed = JOptionPane.showConfirmDialog(HomeGUI.getRootFrame(), "Are you sure?", "Cinema Seat Booking", JOptionPane.YES_NO_OPTION);
            if (isConfirmed == JOptionPane.YES_OPTION) {
                MySQLConnection.closeConnection();
                System.exit(0);
            }
        });

        // บังคับขนาดปุ่มให้เท่ากัน
        for (JButton btn : new JButton[]{homeBtn, movieBtn, exitBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMinimumSize(buttonSize);
            btn.setPreferredSize(buttonSize);
            btn.setMaximumSize(buttonSize);

            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setBackground(new Color(51, 51, 51));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 24));
        }

        // ทำให้ปุ่มอยู่ตรงกลางแนวตั้ง
        sideBarButtonPanel.add(Box.createVerticalGlue());
        sideBarButtonPanel.add(homeBtn);
        sideBarButtonPanel.add(Box.createVerticalStrut(15));
        sideBarButtonPanel.add(movieBtn);
        sideBarButtonPanel.add(Box.createVerticalStrut(15));
        sideBarButtonPanel.add(exitBtn);
        sideBarButtonPanel.add(Box.createVerticalGlue());

        JLabel title = new JLabel("Cinema Seat Booking");
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        sideBar.add(title, BorderLayout.NORTH);
        sideBar.add(sideBarButtonPanel, BorderLayout.CENTER);

        return sideBar;
    }

}

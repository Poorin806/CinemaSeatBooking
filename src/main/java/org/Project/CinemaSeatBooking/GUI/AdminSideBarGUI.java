package org.Project.CinemaSeatBooking.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminSideBarGUI {

    public static JPanel get(JPanel rootPanel) {

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());
        sideBar.setPreferredSize(new Dimension(200, 768));
        sideBar.setBackground(new Color(51, 51, 51, 255));
        sideBar.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel sideBarButtonPanel = new JPanel();
        sideBarButtonPanel.setLayout(new BoxLayout(sideBarButtonPanel, BoxLayout.Y_AXIS));
        sideBarButtonPanel.setOpaque(false);

        Dimension buttonSize = new Dimension(180, 50); // ขนาดปุ่มคงที่

        JButton homeBtn = new JButton("...");
        JButton movieBtn = new JButton("...");
        JButton showtimeBtn = new JButton("...");
        JButton logoutBtn = new JButton("Logout");

        // Event listeners
        logoutBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(rootPanel, "Are you sure you want to logout?", "Cinema Seat Booking", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) HomeGUI.changeToHome();
        });

        // บังคับขนาดปุ่มให้เท่ากัน
        for (JButton btn : new JButton[]{homeBtn, movieBtn, showtimeBtn, logoutBtn}) {
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
        sideBarButtonPanel.add(showtimeBtn);
        sideBarButtonPanel.add(Box.createVerticalStrut(15));
        sideBarButtonPanel.add(logoutBtn);
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

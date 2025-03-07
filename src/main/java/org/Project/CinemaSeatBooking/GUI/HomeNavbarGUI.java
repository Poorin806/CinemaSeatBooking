package org.Project.CinemaSeatBooking.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeNavbarGUI {

    private static final JPanel navbar = new JPanel();

    public static JPanel get(JPanel rootPanel) {

        navbar.setPreferredSize(new Dimension(824, 50));
        navbar.setBackground(new Color(109, 109, 109));
        navbar.setLayout(new BorderLayout());

        JPanel container = new JPanel(new GridLayout(1, 2));
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(5, 10, 5, 10));
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));

        for (JPanel panel : new JPanel[] {leftPanel, rightPanel}) {
            panel.setSize(new Dimension(824 / 2, 50));
        }

        JButton loginBtn = new JButton("Admin");
        JButton cartBtn = new JButton("Cart");
        for (JButton btn : new JButton[] {loginBtn, cartBtn}) {
            btn.setMaximumSize(new Dimension(150, 30));
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(217, 217, 217));
            btn.setForeground(new Color(51, 51, 51));
        }

        cartBtn.addActionListener(e -> {
            HomeGUI.changeToTicketCard();
        });

        loginBtn.addActionListener(e -> {
            login(rootPanel);
        });

        rightPanel.add(Box.createHorizontalGlue());
        rightPanel.add(cartBtn);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(loginBtn);

        container.add(leftPanel);
        container.add(rightPanel);

        navbar.add(container, BorderLayout.CENTER);

        return navbar;

    }

    public static void login(JPanel rootPanel) {

        HomeGUI.changeToAdminDashboard(true);
        return;

//        JPanel loginForm = new JPanel();
//        loginForm.setSize(200, 100);
//        loginForm.setLayout(new BoxLayout(loginForm, BoxLayout.Y_AXIS));
//
//        JLabel username = new JLabel("Username:");
//        JLabel password = new JLabel("Password:");
//        for (JLabel label : new JLabel[] { username, password }) {
//            label.setFont(new Font("Arial", Font.PLAIN, 16));
//        }
//
//        JTextField usernameField = new JTextField(35);
//        JPasswordField passwordField = new JPasswordField(35);
//
//        loginForm.add(username);
//        loginForm.add(Box.createVerticalStrut(5));
//        loginForm.add(usernameField);
//        loginForm.add(Box.createVerticalStrut(10));
//        loginForm.add(password);
//        loginForm.add(Box.createVerticalStrut(5));
//        loginForm.add(passwordField);
//
//        // กำหนดชื่อปุ่มเป็น "Login" และ "Cancel"
//        String[] options = { "Login", "Cancel" };
//        int result = JOptionPane.showOptionDialog(
//                rootPanel,                      // Parent component
//                loginForm,                   // Form panel
//                "Login",                     // Title
//                JOptionPane.YES_NO_OPTION,   // Option type
//                JOptionPane.PLAIN_MESSAGE,   // Message type
//                null,                        // Icon (null = default)
//                options,                     // ปุ่มที่จะแสดง
//                options[0]                   // ปุ่ม Default (ปุ่ม "Login")
//        );
//
//        usernameField.requestFocus();
//
//        if (result == JOptionPane.YES_OPTION) {
//
//            if (
//                    usernameField.getText().equals("admin") &&
//                            passwordField.getText().equals("admin")
//            ) {
//                JOptionPane.showMessageDialog(loginForm, "Login successfully", "Cinema Seat Booking", JOptionPane.INFORMATION_MESSAGE);
//                HomeGUI.changeToAdminDashboard(true);
//            }  else {
//                JOptionPane.showMessageDialog(loginForm, "Failed to login, username or password incorrect", "Cinema Seat Booking", JOptionPane.ERROR_MESSAGE);
//                login(rootPanel);
//            }
//
//        }
    }

}

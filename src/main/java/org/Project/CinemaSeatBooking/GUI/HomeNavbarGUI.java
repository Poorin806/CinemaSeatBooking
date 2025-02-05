package org.Project.CinemaSeatBooking.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeNavbarGUI {

    private static final JPanel navbar = new JPanel();

    public static JPanel get() {

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

        rightPanel.add(Box.createHorizontalGlue());
        rightPanel.add(cartBtn);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(loginBtn);

        container.add(leftPanel);
        container.add(rightPanel);

        navbar.add(container, BorderLayout.CENTER);

        return navbar;

    }

}

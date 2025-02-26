package org.Project.CinemaSeatBooking.GUI;

import javax.swing.*;
import java.awt.*;

public class DashboardGUI {

    private static final JPanel content = new JPanel();

    public static JPanel get() {

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); // ใช้ BoxLayout แนวตั้ง
        content.setPreferredSize(new Dimension(824, 768));
        content.setBackground(new Color(73, 73, 73));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // กำหนด Padding ให้กับ content

        JLabel test = new JLabel("Admin dashboard test");

        content.add(test);

        return content;

    }

}

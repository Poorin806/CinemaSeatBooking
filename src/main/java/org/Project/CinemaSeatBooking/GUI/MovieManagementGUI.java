package org.Project.CinemaSeatBooking.GUI;

import javax.swing.*;
import java.awt.*;

public class MovieManagementGUI {

    private static final JPanel content = new JPanel();

    public static JPanel get() {

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setPreferredSize(new Dimension(824, 768));
        content.setBackground(new Color(73, 73, 73));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel test = new JLabel("Movie Management GUI");

        content.add(test);

        return content;

    }

}

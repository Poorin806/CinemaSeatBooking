package org.Project.CinemaSeatBooking;

import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // MongoDB connection
        MongoDBConnection.Connect();

        // Create JFrame
        JFrame frame = new JFrame();
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cinema Seat Booking");

        // Set BorderLayout for the JFrame
        frame.setLayout(new BorderLayout());

        // SideBar Panel
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.BLACK);
        sideBar.setLayout(new GridLayout(4, 1, 10, 10)); // GridLayout แบ่งเป็น 4 แถว 1 คอลัมน์
        sideBar.setPreferredSize(new Dimension(280, frame.getHeight()));

        // Add Padding to Sidebar using EmptyBorder
        sideBar.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding: 20px รอบด้าน (top, left, bottom, right)

        // Buttons for SideBar
        JButton btn1 = new JButton("BTN 1");
        JButton btn2 = new JButton("BTN 2");
        JButton btn3 = new JButton("BTN 3");
        JButton btn4 = new JButton("BTN 4");
        // Set button size
        Dimension buttonSize = new Dimension(sideBar.getWidth(), 50); // กำหนดขนาดปุ่ม (Width x Height)
        Font btnFont = new Font("Arial", Font.BOLD, 48);
        btn1.setPreferredSize(buttonSize);
        btn2.setPreferredSize(buttonSize);
        btn3.setPreferredSize(buttonSize);
        btn4.setPreferredSize(buttonSize);
        sideBar.add(btn1);
        sideBar.add(btn2);
        sideBar.add(btn3);
        sideBar.add(btn4);

        btn1.setFont(btnFont);
        btn2.setFont(btnFont);
        btn3.setFont(btnFont);
        btn4.setFont(btnFont);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.RED);
        contentPanel.setLayout(new BorderLayout()); // สามารถเพิ่มเนื้อหาใน Content Panel ได้

        JLabel contentLabel = new JLabel("Content Area", SwingConstants.CENTER);
        contentLabel.setForeground(Color.WHITE);
        contentLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(contentLabel, BorderLayout.CENTER);

        // Add Panels to JFrame
        frame.add(sideBar, BorderLayout.WEST); // SideBar ด้านซ้าย
        frame.add(contentPanel, BorderLayout.CENTER); // Content Panel ตรงกลาง

        // Make JFrame visible
        frame.setVisible(true);

        // MongoDB close-connection
        MongoDBConnection.Disconnect();


    }

}

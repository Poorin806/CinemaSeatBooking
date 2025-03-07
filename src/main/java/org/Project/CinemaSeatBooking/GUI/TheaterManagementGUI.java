package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Service.RoomService;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;
import org.Project.CinemaSeatBooking.Model.RoomModel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheaterManagementGUI {

    private static final JPanel content = new JPanel();
    private static final JPanel theaterPanel = new JPanel();
    private static final List<String> theaterNumbers = new ArrayList<>();

    public static JPanel get() throws SQLException {

        content.setLayout(new BorderLayout());
        content.setPreferredSize(new Dimension(824, 768));
        content.setBackground(new Color(73, 73, 73));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title "Theater"
        JLabel titleLabel = new JLabel("Theater");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        content.add(titleLabel, BorderLayout.NORTH);

        // Panel สำหรับ Grid ของโรงภาพยนตร์
        theaterPanel.setLayout(new GridLayout(0, 2, 20, 20));
        theaterPanel.setBackground(new Color(73, 73, 73));

        // ดึงข้อมูลจากฐานข้อมูลและแสดง
        updateTheaterList();

        content.add(theaterPanel, BorderLayout.CENTER);

        // Panel ล่างสำหรับปุ่ม Add
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(73, 73, 73));

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(new Color(50, 150, 50));
        addButton.setForeground(Color.WHITE);

        // ActionListener สำหรับปุ่ม Add
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // ดึงหมายเลข Theatre ถัดไป
                    String newTheaterName = getNextAvailableTheaterName();

                    // เพิ่มโรงภาพยนตร์ใหม่
                    addTheaterToDatabase(newTheaterName);

                    // อัปเดตแสดงผลใน UI
                    updateTheaterList();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        bottomPanel.add(addButton);
        content.add(bottomPanel, BorderLayout.SOUTH);

        return content;
    }

    // คำนวณหมายเลข Theatre ที่ขาดหายไป
    private static String getNextAvailableTheaterName() throws SQLException {
        List<RoomModel> roomList = new RoomService().getAll();
        List<Integer> existingNumbers = new ArrayList<>();
    
        for (RoomModel room : roomList) {
            String name = room.getName();
            if (name.startsWith("Theatre ")) {
                try {
                    int number = Integer.parseInt(name.replace("Theatre ", ""));
                    existingNumbers.add(number);
                } catch (NumberFormatException ignored) {}
            }
        }
    
        // หาเลขที่ขาดหายไป
        int nextNumber = 1;
        while (existingNumbers.contains(nextNumber)) {
            nextNumber++;
        }
    
        return "Theatre " + nextNumber;
    }
    

    // อัปเดตหมายเลขของโรงภาพยนตร์
    private static void updateTheaterList() throws SQLException {
        List<RoomModel> roomList = new RoomService().getAll();
        theaterNumbers.clear();

        for (RoomModel room : roomList) {
            theaterNumbers.add(room.getRoomId());  
        }

        // Clear Panel และเติมโรงภาพยนตร์ที่มีอยู่
        theaterPanel.removeAll();

        for (String theaterName : theaterNumbers) {
            addTheaterBox(theaterName);
        }

        theaterPanel.revalidate();
        theaterPanel.repaint();
    }

    // เมธอดเพิ่มโรงภาพยนตร์ใหม่ลงฐานข้อมูล
    private static void addTheaterToDatabase(String theaterName) throws SQLException {
        String sql = "INSERT INTO room (room_name) VALUES ('" + theaterName + "')";
        MySQLConnection.query(sql); 
    }

    //เพิ่มช่องโรงภาพยนตร์ใน UI
    private static void addTheaterBox(String theaterName) throws SQLException {
        JPanel theaterBox = new JPanel();
        theaterBox.setPreferredSize(new Dimension(150, 100));
        theaterBox.setBackground(new Color(100, 100, 100));
        theaterBox.setLayout(new BorderLayout());

        JLabel label = new JLabel(theaterName);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        theaterBox.add(label, BorderLayout.NORTH);

        // Panel สำหรับปุ่ม Edit/Delete
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(100, 100, 100));

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 12));
        deleteButton.setBackground(new Color(150, 50, 50));
        deleteButton.setForeground(Color.WHITE);

        // ลบโรงภาพยนตร์เมื่อกดปุ่ม Delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // ลบโรงภาพยนตร์
                    String sql = "DELETE FROM room WHERE room_id = '" + theaterName + "'";
                    MySQLConnection.query(sql); 
                    //System.out.print(sql);

                    // อัปเดต UI
                    updateTheaterList();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonPanel.add(deleteButton);

        theaterBox.add(buttonPanel, BorderLayout.SOUTH);

        // เพิ่มเข้าไปใน Grid
        theaterPanel.add(theaterBox);
    }
}

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

        JLabel titleLabel = new JLabel("Theater Manage");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        content.add(titleLabel, BorderLayout.NORTH);

        theaterPanel.setLayout(new GridLayout(0, 2, 20, 20));
        theaterPanel.setBackground(new Color(73, 73, 73));

        updateTheaterList();

        content.add(theaterPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(73, 73, 73));

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(new Color(50, 150, 50));
        addButton.setForeground(Color.WHITE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newTheaterName = JOptionPane.showInputDialog("Enter Theater Name:");
                if (newTheaterName != null && !newTheaterName.trim().isEmpty()) {
                    try {
                        addTheaterToDatabase(newTheaterName);
                        updateTheaterList();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        bottomPanel.add(addButton);
        content.add(bottomPanel, BorderLayout.SOUTH);

        return content;
    }

    public static void updateTheaterList() throws SQLException {
        List<RoomModel> roomList = new RoomService().getAll();
        theaterNumbers.clear();

        theaterPanel.removeAll();

        for (RoomModel room : roomList) {
            addTheaterBox(room.getRoomId(), room.getName());  
        }

        theaterPanel.revalidate();
        theaterPanel.repaint();
    }

    private static void addTheaterToDatabase(String theaterName) throws SQLException {
        String sql = "INSERT INTO room (room_name) VALUES ('" + theaterName + "')";
        MySQLConnection.query(sql); 
    }

    private static void addTheaterBox(String theaterID, String TheatrName) throws SQLException {
        JPanel theaterBox = new JPanel();
        theaterBox.setPreferredSize(new Dimension(150, 100));
        theaterBox.setBackground(new Color(100, 100, 100));
        theaterBox.setLayout(new BorderLayout());

        JLabel label = new JLabel(TheatrName);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        theaterBox.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(100, 100, 100));

        JButton EditButton = new JButton("Edit");
        EditButton.setFont(new Font("Arial", Font.PLAIN, 12));
        EditButton.setBackground(new Color(0, 191, 255));
        EditButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 12));
        deleteButton.setBackground(new Color(150, 50, 50));
        deleteButton.setForeground(Color.WHITE);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete this theater?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        String sql = "DELETE FROM room WHERE room_id = '" + theaterID + "'";
                        MySQLConnection.query(sql);
                        updateTheaterList();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(
                            null,
                            "An error occurred while deleting: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        ex.printStackTrace();
                    }
                }
            }
        });

        EditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HomeGUI.changeToEditRoom(theaterID);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonPanel.add(deleteButton);
        buttonPanel.add(EditButton);
        theaterBox.add(buttonPanel, BorderLayout.SOUTH);
        theaterPanel.add(theaterBox);
    }
}
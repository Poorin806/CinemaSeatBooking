package org.Project.CinemaSeatBooking.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Model.RoomModel;
import org.Project.CinemaSeatBooking.Service.MovieService;
import org.Project.CinemaSeatBooking.Service.RoomService;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

public class RoomEditGUI {
    private static JPanel content;
    private static RoomModel roomModel = new RoomModel();
    private static JTextField roomNameTextField;

    public static JPanel get() {
        content = new JPanel(new GridBagLayout());
        content.setPreferredSize(new Dimension(600, 400));
        content.setBackground(new Color(50, 50, 50));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return content;
    }

    private static void updateRoomNameInDB(String updatedRoomName) {
        String sql = "UPDATE room SET room_name = '" + updatedRoomName + "' WHERE room_id = " + roomModel.getRoomId();
        MySQLConnection.query(sql);
        JOptionPane.showMessageDialog(content, "Room name updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        try {
            TheaterManagementGUI.updateTheaterList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void setData(String roomId) {
        String sql = "SELECT * FROM room WHERE room_id = " + roomId;
        try {
            roomModel = new RoomService().getOne(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        content.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Theatre Manage");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        content.add(titleLabel, gbc);

        // List<RoomModel> roomlist = new RoomService().getAll();

        gbc.gridy++;
        JLabel currentRoomLabel = new JLabel("Editing Room: " + roomModel.getName());
        currentRoomLabel.setForeground(Color.YELLOW);
        currentRoomLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        content.add(currentRoomLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel roomLabel = new JLabel("Room Name:");
        roomLabel.setForeground(Color.WHITE);
        roomLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        content.add(roomLabel, gbc);

        gbc.gridx = 1;
        roomNameTextField = new JTextField(15);
        roomNameTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        if (roomModel != null) {
            roomNameTextField.setText(roomModel.getName());
        } else {
            roomNameTextField.setText("No room found");
        }
        roomNameTextField.setText("");
        content.add(roomNameTextField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Arial", Font.PLAIN, 16));
        enterButton.setBackground(new Color(0, 191, 255));
        enterButton.setForeground(Color.WHITE);
        enterButton.setPreferredSize(new Dimension(120, 40));
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedRoomName = roomNameTextField.getText().trim();
                if (!updatedRoomName.isEmpty()) {
                    updateRoomNameInDB(updatedRoomName);
                } else {
                    JOptionPane.showMessageDialog(content, "Please enter a valid room name.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        content.add(enterButton, gbc);

        content.revalidate();
        content.repaint();

    }
}
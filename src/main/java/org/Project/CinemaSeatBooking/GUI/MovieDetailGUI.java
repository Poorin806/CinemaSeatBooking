package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Utils.GuiUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MovieDetailGUI {

    private static final JPanel content = new JPanel(new BorderLayout());

    private static JLabel titleLabel = new JLabel();
    private static JLabel tagLabel = new JLabel("Tag: .........................");
    private static JLabel releaseDateLabel = new JLabel("Release Date: ......");
    private static JLabel showTimeLabel = new JLabel("Show time: ................");
    private static JTextArea descriptionLabel = new JTextArea(
            "....");

    public static JPanel get() {

        content.setPreferredSize(new Dimension(824, 768)); // ขนาดพื้นที่ของ content
        content.setBackground(new Color(73, 73, 73));
        content.setBorder(new EmptyBorder(150, 20, 150, 20));

        JPanel mainGrid = new JPanel(new GridLayout(1, 2, 20, 0));
        mainGrid.setOpaque(false);

        BackgroundImageJPanel movieImg = new BackgroundImageJPanel(
                "https://minecraft.wiki/images/thumb/Charged_Creeper_JE1_BE1.png/150px-Charged_Creeper_JE1_BE1.png?87117",
                false
        );
        movieImg.setBackground(new Color(0, 0, 0, 0));
        movieImg.setBackground(Color.LIGHT_GRAY);

        JPanel detailGrid = new JPanel(new GridLayout(2, 1, 0, 10));
        detailGrid.setOpaque(false);

        JPanel descriptionPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        descriptionPanel.setOpaque(false);

        JPanel movieTitlePanel = new JPanel();
        movieTitlePanel.setOpaque(false);
        movieTitlePanel.setLayout(new BoxLayout(movieTitlePanel, BoxLayout.Y_AXIS));

        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        tagLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        tagLabel.setForeground(Color.WHITE);

        titleLabel.setPreferredSize(new Dimension(500, 50)); // ขนาดที่เหมาะสม
        tagLabel.setPreferredSize(new Dimension(500, 30));

        movieTitlePanel.add(titleLabel);
        movieTitlePanel.add(Box.createVerticalStrut(5));
        movieTitlePanel.add(tagLabel);


        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setBackground(new Color(0, 0, 0, 0));
        descriptionLabel.setEditable(false);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setPreferredSize(new Dimension(500, 100));

        descriptionPanel.add(movieTitlePanel);
        descriptionPanel.add(descriptionLabel);

        JPanel managementPanel = new JPanel();
        managementPanel.setOpaque(false);
        managementPanel.setLayout(new BoxLayout(managementPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setMaximumSize(new Dimension(500, 50));

        JButton bookingBtn = new JButton("Booking");
        JButton favBtn = new JButton("Add to favorites");
        for (JButton btn : new JButton[]{bookingBtn, favBtn}) {
            btn.setMaximumSize(new Dimension(150, 40)); // กำหนดขนาดที่เหมาะสม
            btn.setBackground(new Color(217, 217, 217));
            btn.setForeground(new Color(0, 0, 0));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
        }

        buttonPanel.add(bookingBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(favBtn);
        // Button Events listeners
        bookingBtn.addActionListener(e -> {
            selectBookingDetail(titleLabel.getText());
        });

        releaseDateLabel.setForeground(Color.WHITE);
        showTimeLabel.setForeground(Color.WHITE);
        releaseDateLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        showTimeLabel.setFont(new Font("Arial", Font.ITALIC, 16));

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // ใช้ FlowLayout และจัดตำแหน่งให้ชิดซ้าย
        footerPanel.setOpaque(false);
        footerPanel.setPreferredSize(new Dimension(500, 20));  // กำหนดขนาดที่ต้องการ
        footerPanel.setMaximumSize(new Dimension(500, 20));  // ขนาดสูงสุดที่ต้องการ
        JPanel footerPanel_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));  // ใช้ FlowLayout และจัดตำแหน่งให้ชิดซ้าย
        footerPanel_2.setOpaque(false);
        footerPanel_2.setPreferredSize(new Dimension(500, 20));  // กำหนดขนาดที่ต้องการ
        footerPanel_2.setMaximumSize(new Dimension(500, 20));  // ขนาดสูงสุดที่ต้องการ
        releaseDateLabel.setForeground(Color.WHITE);
        showTimeLabel.setForeground(Color.WHITE);
        footerPanel.add(releaseDateLabel);
        footerPanel_2.add(showTimeLabel);

        managementPanel.add(buttonPanel);
        managementPanel.add(Box.createVerticalStrut(20));
        managementPanel.add(footerPanel);
        managementPanel.add(footerPanel_2);

        detailGrid.add(descriptionPanel, BorderLayout.NORTH);
        detailGrid.add(managementPanel, BorderLayout.SOUTH);

        mainGrid.add(movieImg);
        mainGrid.add(detailGrid);

        content.add(mainGrid, BorderLayout.CENTER);

        return content;
    }

    public static void setMovieData(String name) {
        titleLabel.setText(name);
    }

    private static void selectBookingDetail(String movieData) {

        JFrame rootFrame = HomeGUI.getRootFrame();
        JDialog popUpDialog = new JDialog(rootFrame, "Select Movie Booking Detail", true);
        popUpDialog.setSize(760, 500);
        popUpDialog.setLayout(new BorderLayout());

        JButton closeBtn = new JButton("Cancel");
        JButton continueBtn = new JButton("Continue (Forced)");
        closeBtn.addActionListener(e -> popUpDialog.dispose());
        continueBtn.addActionListener(e -> {
            popUpDialog.dispose();
            HomeGUI.changeToSeatBooking(movieData);
        });
        for (JButton btn : new JButton[]{closeBtn, continueBtn}) {

            btn.setPreferredSize(new Dimension(200, 40));
            btn.setMaximumSize(new Dimension(200, 40));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setBackground(new Color(217, 217, 217));
            btn.setForeground(new Color(51, 51, 51));
            btn.setFont(new Font("Arial", Font.BOLD, 14));

        }

        // Container Panel
        JPanel popUpContainer = new JPanel();
        popUpContainer.setOpaque(true);
        popUpContainer.setBackground(new Color(51, 51, 51));
        popUpContainer.setLayout(new BoxLayout(popUpContainer, BoxLayout.Y_AXIS));
        popUpContainer.setSize(popUpDialog.getWidth(), popUpDialog.getHeight());
        popUpContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(popUpDialog.getWidth(), 100));

        JPanel movieImg = new JPanel();
        movieImg.setPreferredSize(new Dimension(75, 100));
        movieImg.setBackground(new Color(217, 217, 217));
        headerPanel.add(movieImg);

        // Selection Panel
        JPanel selectionPanel = new JPanel();
        selectionPanel.setOpaque(false);
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setPreferredSize(new Dimension(popUpDialog.getWidth(), 400));
        selectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel selectionContentPanel = new JPanel();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(760, 40));
        buttonPanel.add(continueBtn);
        buttonPanel.add(closeBtn);

        selectionPanel.add(selectionContentPanel);
        selectionPanel.add(Box.createVerticalStrut(10));
        selectionPanel.add(buttonPanel);

        popUpContainer.add(headerPanel);
        popUpContainer.add(Box.createVerticalStrut(10));
        popUpContainer.add(selectionPanel);

        popUpDialog.add(popUpContainer, BorderLayout.CENTER);
        popUpDialog.setLocationRelativeTo(rootFrame);
        popUpDialog.setVisible(true);

    }

}

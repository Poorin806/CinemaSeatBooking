package org.Project.CinemaSeatBooking.Utils;

import org.Project.CinemaSeatBooking.GUI.HomeGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class BackgroundImageJPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundImageJPanel() {
        try {

            backgroundImage = Toolkit.getDefaultToolkit().getImage(
                    BackgroundImageJPanel.class.getClassLoader().getResource("AppIcon.png")
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BackgroundImageJPanel(String imgPath_Link, boolean isFromDevice) {
        try {
            if (isFromDevice) {
                // Load image from resources folder
                backgroundImage = Toolkit.getDefaultToolkit().getImage(
                        BackgroundImageJPanel.class.getClassLoader().getResource(imgPath_Link)
                );

                // Validate if the image was loaded successfully
                if (backgroundImage == null) {
                    throw new IOException("Image not found in resources: " + imgPath_Link);
                }
            } else {
                // Load image from URL
                URL imgUrl = new URL(imgPath_Link);
                backgroundImage = ImageIO.read(imgUrl);

                // Validate if the image was loaded successfully
                if (backgroundImage == null) {
                    throw new IOException("Image could not be loaded from URL: " + imgPath_Link);
                }
            }

        } catch (Exception e) {
            // Handle the error more specifically
            System.err.println("Error loading background image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // วาดภาพพื้นหลัง
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}

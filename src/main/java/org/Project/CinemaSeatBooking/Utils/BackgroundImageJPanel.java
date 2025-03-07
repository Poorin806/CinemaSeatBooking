package org.Project.CinemaSeatBooking.Utils;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BackgroundImageJPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundImageJPanel() {
        this.backgroundImage = Toolkit.getDefaultToolkit().getImage(
                BackgroundImageJPanel.class.getClassLoader().getResource("AppIcon.png")
        );
    }

    private void loadImageFromURL(String urlPath) {
        try {
            URL imgUrl = new URL(urlPath);
            backgroundImage = ImageIO.read(imgUrl);
        } catch (IOException e) {
            System.err.println("Error loading image from URL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setImage(String imagePath, boolean isFromDevice) {
        this.backgroundImage = Toolkit.getDefaultToolkit().getImage(
                BackgroundImageJPanel.class.getClassLoader().getResource("AppIcon.png")
        );
        repaint();

        // โหลดรูปภาพใน Background Thread
        new Thread(() -> {
            try {
                Image loadedImage;
                if (isFromDevice) {
                    loadedImage = ImageIO.read(new File(imagePath));
                } else {
                    loadedImage = ImageIO.read(new URL(imagePath));
                }

                // อัปเดต UI หลังจากโหลดภาพเสร็จ
                SwingUtilities.invokeLater(() -> {
                    this.backgroundImage = loadedImage;
                    repaint();
                });

            } catch (IOException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }).start();
    }
}
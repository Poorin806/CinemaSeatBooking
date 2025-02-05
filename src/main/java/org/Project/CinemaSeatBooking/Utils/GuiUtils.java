package org.Project.CinemaSeatBooking.Utils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class GuiUtils {

    public static void makeRoundedPanel(JPanel panel, int cornerRadius) {
        panel.setOpaque(false); // ทำให้โปร่งใสเพื่อให้เห็นขอบมน
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // ป้องกันการซ้อนทับ
//        panel.setBackground(Color.WHITE); // ตั้งค่าสีพื้นหลัง

        // ใช้ anonymous class เพื่อ override paintComponent ของ panel ที่ส่งเข้ามา
        panel.setUI(null); // ปิดการใช้งาน UI ของ Swing เพื่อให้วาดเองได้
        panel.setLayout(new BorderLayout()); // ตั้ง layout ให้ใช้งานได้

        panel.add(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // วาดพื้นหลังให้เป็นสี่เหลี่ยมมุมมน
                g2.setColor(panel.getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

                g2.dispose();
            }
        });
    }

    public static void makeRoundedButton(JButton button, int radius) {
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBorderPainted(false); // Disable border painting

        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLUE);

        // Create a custom UI to keep the button rounded and consistent
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw the rounded rectangle with a consistent background
                g2d.setColor(button.getBackground());
                g2d.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), radius, radius);

                // Draw the text (foreground)
                super.paint(g, c);
            }

            public void paintBorder(Graphics g, JComponent c) {
                // No border to be drawn
            }

            @Override
            public void update(Graphics g, JComponent c) {
                // Call to prevent button from "unpainting" itself
                paint(g, c);
            }
        });
    }



}

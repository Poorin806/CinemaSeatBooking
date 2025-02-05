package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HomeContentGUI {

    private static final JPanel homeContent = new JPanel(new BorderLayout());

    public static JPanel get() {

        // Main content area
        homeContent.setPreferredSize(new Dimension(824, 768)); // Remaining space

        JPanel navbar = HomeNavbarGUI.get();

        JPanel movieList = new JPanel(new BorderLayout());
        movieList.setPreferredSize(new Dimension(homeContent.getWidth(), 700));
        movieList.setBackground(new Color(73, 73, 73));
        movieList.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel movieListTitle = new JLabel("Now showing");
        movieListTitle.setFont(new Font("Arial", Font.BOLD, 24));
        movieListTitle.setHorizontalAlignment(JLabel.LEFT);
        movieListTitle.setForeground(new Color(255, 255, 255));
        movieList.add(movieListTitle, BorderLayout.NORTH);

        JPanel movieItems = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 30));
        movieItems.setBackground(new Color(73, 73, 73));

        // 🔹 ข้อมูลของหนังแต่ละเรื่อง (กำหนดเป็น List)
        ArrayList<String[]> movies = new ArrayList<>(); // Maximum = 8 (No-responsive)
        movies.add(new String[]{"Jan 1, 2025", "Movie 1"});
        movies.add(new String[]{"Feb 10, 2025", "Movie 2"});
        movies.add(new String[]{"Mar 5, 2025", "Movie 3"});
        movies.add(new String[]{"Apr 15, 2025", "Movie 4"});
        movies.add(new String[]{"Apr 15, 2025", "Movie 5"});
        movies.add(new String[]{"Apr 15, 2025", "Movie 6"});
        movies.add(new String[]{"Apr 15, 2025", "Movie 7"});

        // 🔄 Loop สร้าง movieCard จากข้อมูล
        for (String[] movie : movies) {
            JPanel movieCard = new JPanel(new BorderLayout(0, 2));
            movieCard.setPreferredSize(new Dimension(150, 280));
            movieCard.setOpaque(false);

            BackgroundImageJPanel movieImg = new BackgroundImageJPanel(
                    "https://ssb.wiki.gallery/images/f/f3/Steve.png",
                    false
            );
            movieImg.setBackground(new Color(0, 0, 0, 0));
            movieImg.setPreferredSize(new Dimension(150, 220));

            // Evenet listeners
            movieCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    HomeGUI.changeToMovieDetail(movie[1]);
                }
            });

            JLabel movieDate = new JLabel(movie[0]);
            JLabel movieName = new JLabel(movie[1]);
            movieDate.setForeground(Color.WHITE);
            movieName.setForeground(Color.WHITE);
            movieDate.setFont(new Font("Arial", Font.PLAIN, 14));
            movieName.setFont(new Font("Arial", Font.BOLD, 20));

            movieCard.add(movieImg, BorderLayout.NORTH);
            movieCard.add(movieDate, BorderLayout.CENTER);
            movieCard.add(movieName, BorderLayout.SOUTH);

            movieItems.add(movieCard); // 🔹 เพิ่ม movieCard ลงใน movieItems
        }

        movieList.add(movieItems, BorderLayout.CENTER);

        homeContent.add(navbar, BorderLayout.NORTH);
        homeContent.add(movieList, BorderLayout.CENTER);

        homeContent.setVisible(false);

        return homeContent;

    }

    public static void show() {
        homeContent.setVisible(true);
    }

    public static void close() {
        homeContent.setVisible(false);
    }

}

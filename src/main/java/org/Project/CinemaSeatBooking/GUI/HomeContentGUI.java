package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Service.MovieService;
import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Utils.EssentialsUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class HomeContentGUI {

    private static final JPanel homeContent = new JPanel(new BorderLayout());

    public static JPanel get() throws SQLException {

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

        // [Get data from database]
        List<MovieModel> movieModelList = new MovieService().getAll();

        // Movie card Looping
        int counting = 1;
        for (int i = 0; i < movieModelList.size(); i++) {

            if (movieModelList.get(i).getIsActive() && counting <= 8) {

                JPanel movieCard = new JPanel(new BorderLayout(0, 2));
                movieCard.setPreferredSize(new Dimension(150, 280));
                movieCard.setOpaque(false);

                BackgroundImageJPanel movieImg = new BackgroundImageJPanel();
                movieImg.setBackground(new Color(0, 0, 0, 0));
                movieImg.setPreferredSize(new Dimension(150, 220));

                // Async loading
                movieImg.setImage(movieModelList.get(i).getImageUrl(), false);

                // Evenet listeners
                int finalI = i;
                movieCard.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("See detail: " + movieModelList.get(finalI).getTitle());
                        try {
                            HomeGUI.changeToMovieDetail(
                                    movieModelList.get(finalI)
                            );
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

                JLabel movieDate = new JLabel(
                        EssentialsUtils.formatDate(movieModelList.get(finalI).getReleaseDate().toString())
                );
                JLabel movieName = new JLabel(
                        movieModelList.get(finalI).getTitle()
                );
                movieDate.setForeground(Color.WHITE);
                movieName.setForeground(Color.WHITE);
                movieDate.setFont(new Font("Arial", Font.PLAIN, 14));
                movieName.setFont(new Font("Arial", Font.BOLD, 20));

                movieCard.add(movieImg, BorderLayout.NORTH);
                movieCard.add(movieDate, BorderLayout.CENTER);
                movieCard.add(movieName, BorderLayout.SOUTH);

                // Add movie card into panel
                movieItems.add(movieCard);

                counting++;

            }

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

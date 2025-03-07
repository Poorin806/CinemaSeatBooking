package org.Project.CinemaSeatBooking.GUI;

import org.Project.CinemaSeatBooking.Model.GenreModel;
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
import java.util.ArrayList;
import java.util.List;

public class AllMovieGUI {

    private static final JPanel content = new JPanel(new BorderLayout());

    private static final JPanel movieContainer = new JPanel();
    private static final JScrollPane scrollPane = new JScrollPane();

    private static List<MovieModel> movieModelList = new ArrayList<>();

    public static JPanel get() {

        content.setPreferredSize(new Dimension(824, 768));
        content.setBackground(new Color(73, 73, 73));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All movie");
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        content.add(titleLabel, BorderLayout.NORTH);

        return content;

    }

    public static void refreshData() throws SQLException {

        movieModelList = new MovieService().getAll();

        movieContainer.removeAll();
        movieContainer.setOpaque(false);
        movieContainer.setLayout(new BoxLayout(movieContainer, BoxLayout.Y_AXIS));

        if (!movieModelList.isEmpty()) {

            movieContainer.add(Box.createVerticalStrut(20));
            for (MovieModel movieModel : movieModelList) {

                movieContainer.add(createMovieCard(movieModel));
                movieContainer.add(Box.createVerticalStrut(20));

            }

        } else {
            JLabel emptyMovieDataLabel = new JLabel("There is no movie in database");
            emptyMovieDataLabel.setForeground(Color.WHITE);
            emptyMovieDataLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            emptyMovieDataLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setOpaque(false);
            emptyPanel.add(emptyMovieDataLabel, BorderLayout.CENTER);
        }

        content.remove(scrollPane);

        scrollPane.setViewportView(movieContainer);
        scrollPane.setOpaque(false);
        scrollPane.setFocusable(false);
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        content.add(scrollPane, BorderLayout.CENTER);

        movieContainer.revalidate();
        movieContainer.repaint();

        scrollPane.revalidate();
        scrollPane.repaint();

        content.revalidate();
        content.repaint();

    }

    private static JPanel createMovieCard(MovieModel movieModel) {

        JPanel movieCard = new JPanel();
        movieCard.setPreferredSize(new Dimension(824, 150));
        movieCard.setMaximumSize(new Dimension(824, 150));
        movieCard.setBackground(new Color(109, 109, 109));
        movieCard.setBorder(new EmptyBorder(10, 10, 10, 10));
        movieCard.setLayout(new BoxLayout(movieCard, BoxLayout.X_AXIS));

        // [Image Panel]
        BackgroundImageJPanel movieImagePanel = new BackgroundImageJPanel();
        movieImagePanel.setOpaque(false);
        movieImagePanel.setImage(movieModel.getImageUrl(), false);
        movieImagePanel.setPreferredSize(new Dimension(120, 150));
        movieImagePanel.setMaximumSize(new Dimension(120, 150));

        // [Movie Detail Panel]
        JPanel movieDetailPanel = new JPanel();
        movieDetailPanel.setLayout(new BoxLayout(movieDetailPanel, BoxLayout.Y_AXIS));
        movieDetailPanel.setOpaque(false);
        movieDetailPanel.setPreferredSize(new Dimension(680, 150));
        movieDetailPanel.setMaximumSize(new Dimension(680, 150));

        // [Movie Detail]
        JLabel movieTitle = new JLabel(movieModel.getTitle());
        JLabel movieDescription = new JLabel(movieModel.getDescription());
        JLabel movieDate = new JLabel(EssentialsUtils.formatDate(movieModel.getReleaseDate().toString()));
        String genres = "";
        for (GenreModel genre : movieModel.getGenreList()) {
            genres += genre.getName() + " ";
        }
        JLabel movieGenres = new JLabel(genres);

        for (JLabel label : new JLabel[] { movieTitle, movieDescription, movieGenres, movieDate }) {
            label.setForeground(Color.WHITE);
        }
        movieTitle.setFont(new Font("Arial", Font.BOLD, 26));
        movieDescription.setFont(new Font("Arial", Font.PLAIN, 20));
        movieGenres.setFont(new Font("Arial", Font.ITALIC, 16));
        movieDate.setFont(new Font("Arial", Font.ITALIC, 12));

        movieDetailPanel.add(Box.createVerticalGlue());
        movieDetailPanel.add(movieTitle);
        movieDetailPanel.add(Box.createVerticalStrut(5));
        movieDetailPanel.add(movieDate);
        movieDetailPanel.add(Box.createVerticalStrut(10));
        movieDetailPanel.add(movieDescription);
        movieDetailPanel.add(Box.createVerticalStrut(10));
        movieDetailPanel.add(movieGenres);
        movieDetailPanel.add(Box.createVerticalGlue());

        movieCard.add(movieImagePanel);
        movieCard.add(Box.createHorizontalStrut(15));
        movieCard.add(movieDetailPanel);

        // [Event]
        movieCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("See detail: " + movieModel.getTitle());
                try {
                    HomeGUI.changeToMovieDetail(
                            movieModel
                    );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return movieCard;

    }

}
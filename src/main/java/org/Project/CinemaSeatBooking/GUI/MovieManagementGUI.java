package org.Project.CinemaSeatBooking.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Service.MovieService;
import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Utils.EssentialsUtils;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

public class MovieManagementGUI {

    private static final JPanel content = new JPanel(new BorderLayout());
    private static JPanel currentContent = new JPanel(new BorderLayout()); // To manage card list or detail

    public static JPanel get() throws SQLException {
        currentContent = getCardList();
        content.removeAll();
        content.add(currentContent, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        return content;
    }

    private static JPanel getCardList() throws SQLException {
        currentContent.removeAll();
        currentContent.setLayout(new BorderLayout());
        currentContent.setPreferredSize(new Dimension(824, 768));
        currentContent.setBackground(new Color(73, 73, 73));
        currentContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel movieListTitle = new JLabel("Movie Management");
        movieListTitle.setFont(new Font("Arial", Font.BOLD, 24));
        movieListTitle.setHorizontalAlignment(JLabel.LEFT);
        movieListTitle.setForeground(Color.WHITE);
        titlePanel.add(movieListTitle, BorderLayout.WEST);
        titlePanel.setOpaque(false);
        currentContent.add(titlePanel, BorderLayout.NORTH);

        JPanel movieItems = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 30));
        movieItems.setBackground(new Color(73, 73, 73));
        movieItems.setPreferredSize(new Dimension(824, 10000));

        // Get data from database
        List<MovieModel> movieModelList = new MovieService().getAll();

        // Movie card Looping
        for (MovieModel movie : movieModelList) {
            JPanel movieCard = new JPanel(new BorderLayout(0, 2));
            movieCard.setPreferredSize(new Dimension(150, 280));
            movieCard.setOpaque(false);

            BackgroundImageJPanel movieImg = new BackgroundImageJPanel();
            movieImg.setBackground(new Color(0, 0, 0, 0));
            movieImg.setPreferredSize(new Dimension(150, 220));
            movieImg.setImage(movie.getImageUrl(), false);

            movieCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        currentContent = getDetail(movie);
                        content.removeAll();
                        content.add(currentContent, BorderLayout.CENTER);
                        content.revalidate();
                        content.repaint();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            JLabel movieDate = new JLabel(
                    EssentialsUtils.formatDate(movie.getReleaseDate().toString())
            );
            JLabel movieName = new JLabel(movie.getTitle());

            movieDate.setForeground(Color.WHITE);
            movieName.setForeground(Color.WHITE);
            movieDate.setFont(new Font("Arial", Font.PLAIN, 14));
            movieName.setFont(new Font("Arial", Font.BOLD, 20));

            movieCard.add(movieImg, BorderLayout.NORTH);
            movieCard.add(movieDate, BorderLayout.CENTER);
            movieCard.add(movieName, BorderLayout.SOUTH);

            movieItems.add(movieCard);
        }
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(movieItems);
        scrollPane.setOpaque(false);
        scrollPane.setFocusable(false);
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        currentContent.add(scrollPane, BorderLayout.CENTER);
        return currentContent;
    }

    private static JPanel getDetail(MovieModel movie) throws SQLException {
        JPanel detailContent = new JPanel(new BorderLayout());
        detailContent.setPreferredSize(new Dimension(824, 768));
        detailContent.setBackground(new Color(73, 73, 73));

        // Header Panel
        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(content.getWidth(), 60));
        header.setBorder(new EmptyBorder(20, 20, 20, 20));
        header.setBackground(new Color(73, 73, 73));

        JLabel backButton = new JLabel("< Back");
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    currentContent = getCardList();
                    content.removeAll();
                    content.add(currentContent, BorderLayout.CENTER);
                    content.revalidate();
                    content.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        header.add(backButton);

        detailContent.add(header, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainContent = new JPanel();
        mainContent.setBackground(new Color(73, 73, 73));
        mainContent.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        // Movie image
        BackgroundImageJPanel movieImg = new BackgroundImageJPanel();
        movieImg.setPreferredSize(new Dimension(200, 300));
        movieImg.setImage(movie.getImageUrl(), false);
        movieImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(movieImg);
        mainContent.add(Box.createVerticalStrut(20));

        // Movie Name (Editable)
        JTextField movieNameField = new JTextField(movie.getTitle());
        movieNameField.setFont(new Font("Arial", Font.BOLD, 30));
        movieNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        movieNameField.setForeground(Color.BLACK);
        movieNameField.setMaximumSize(new Dimension(400, 40));
        mainContent.add(movieNameField);
        mainContent.add(Box.createVerticalStrut(10));

        // Movie Date (Editable) with Date Picker and Active Button
        JPanel dateAndActivePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dateAndActivePanel.setOpaque(false);
        dateAndActivePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel movieDateLabel = new JLabel("Release Date: ");
        movieDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        movieDateLabel.setForeground(Color.WHITE);

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        Date releaseDate;
        try {
            releaseDate = dbFormat.parse(movie.getReleaseDate().toString());
        } catch (ParseException e) {
            releaseDate = new Date(); // ใช้วันที่ปัจจุบันหากเกิดข้อผิดพลาด
        }
        
        JTextField movieDateTextField = new JTextField(displayFormat.format(releaseDate));
        
        movieDateTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        movieDateTextField.setForeground(Color.BLACK);
        movieDateTextField.setEditable(false);
        movieDateTextField.setMaximumSize(new Dimension(150, 30));

        JButton datePickerButton = new JButton("Pick Date");
        datePickerButton.addActionListener(e -> {
            JDialog datePicker = new JDialog(HomeGUI.getRootFrame(), "Select Date", true);
            datePicker.setSize(300, 250);
            datePicker.setLocationRelativeTo(HomeGUI.getRootFrame());
            datePicker.setLayout(new BorderLayout());

            JPanel calendarPanel = new JPanel();
            calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.Y_AXIS));

            Calendar calendar = Calendar.getInstance();
            if (!movieDateTextField.getText().isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    calendar.setTime(sdf.parse(movieDateTextField.getText()));
                } catch (ParseException ex) {
                    // Handle parsing error
                }
            }

            int selectedYear = calendar.get(Calendar.YEAR);
            int selectedMonth = calendar.get(Calendar.MONTH);
            int selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

            JPanel dateSelectionPanel = createDateSelectionPanel(selectedYear, selectedMonth, selectedDay, selectedDate -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                movieDateTextField.setText(sdf.format(selectedDate));
                datePicker.dispose();
            });

            calendarPanel.add(dateSelectionPanel);
            datePicker.add(calendarPanel, BorderLayout.CENTER);
            datePicker.setVisible(true);
        });

        final boolean[] isActivate = {movie.getIsActive()};
        JButton isActivateBtn = new JButton(isActivate[0] ? "Active" : "Deactive");
        isActivateBtn.setMaximumSize(new Dimension(100, 30));
        isActivateBtn.addActionListener(e -> {
            isActivate[0] = !isActivate[0];
            isActivateBtn.setText(isActivate[0] ? "Active" : "Deactive");
        });

        dateAndActivePanel.add(movieDateLabel);
        dateAndActivePanel.add(movieDateTextField);
        dateAndActivePanel.add(datePickerButton);
        dateAndActivePanel.add(isActivateBtn);

        mainContent.add(dateAndActivePanel);
        mainContent.add(Box.createVerticalStrut(-100));
        
        //Movie Description
        JTextArea movieDescriptionArea = new JTextArea(movie.getDescription());
        movieDescriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        movieDescriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        movieDescriptionArea.setForeground(Color.BLACK);
        movieDescriptionArea.setLineWrap(true);
        movieDescriptionArea.setWrapStyleWord(true);
        movieDescriptionArea.setBorder(new LineBorder(Color.GRAY));
        JScrollPane descriptionScrollPane = new JScrollPane(movieDescriptionArea);
        descriptionScrollPane.setMaximumSize(new Dimension(400,100));
        mainContent.add(descriptionScrollPane);
        mainContent.add(Box.createVerticalStrut(20));

        // Movie Image URL (Editable)
        JTextField movieImageUrlField = new JTextField(movie.getImageUrl());
        movieImageUrlField.setFont(new Font("Arial", Font.PLAIN, 16));
        movieImageUrlField.setAlignmentX(Component.CENTER_ALIGNMENT);
        movieImageUrlField.setForeground(Color.BLACK);
        movieImageUrlField.setMaximumSize(new Dimension(400, 30));
        mainContent.add(movieImageUrlField);
        mainContent.add(Box.createVerticalStrut(10));

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setMaximumSize(new Dimension(100, 30));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = originalFormat.parse(
                        movieDateTextField.getText()
                    );
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = targetFormat.format(date);
                

                String SQL = "UPDATE movie m SET m.movie_title = '" + movieNameField.getText() + "'"+
            
                ", m.release_date = '" + formattedDate + "'"
                +", m.image_url = '" + movieImageUrlField.getText() + "'"
                +", m.is_active = " + (isActivate[0] ? 1 : 0)
                +", m.movie_description = '" + movieDescriptionArea.getText() + "'";

                SQL +=" WHERE m.movie_id = '" + movie.getMovieId() + "'";
                
            

                if (MySQLConnection.query(SQL) >= 1) {
                    JOptionPane.showMessageDialog(currentContent, "Save changes", SQL, 1);
                }
            }
        });
        
        mainContent.add(Box.createVerticalStrut(20));
        mainContent.add(saveButton);

        detailContent.add(mainContent, BorderLayout.CENTER);
        
        return detailContent;
    }
    
    private static JPanel createDateSelectionPanel(int year, int month, int day, DateSelectionListener listener) {
        JPanel panel = new JPanel(new BorderLayout());

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        JLabel monthYearLabel = new JLabel(new SimpleDateFormat("MMMM yyyy").format(calendar.getTime()));
        monthYearLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel navigationPanel = new JPanel(new FlowLayout());
        JButton prevMonthButton = new JButton("<");
        JButton nextMonthButton = new JButton(">");
        navigationPanel.add(prevMonthButton);
        navigationPanel.add(monthYearLabel);
        navigationPanel.add(nextMonthButton);

        JPanel daysPanel = new JPanel(new java.awt.GridLayout(0, 7));

        updateDaysPanel(daysPanel, calendar, listener);

        prevMonthButton.addActionListener(e -> {
            calendar.add(Calendar.MONTH, -1);
            monthYearLabel.setText(new SimpleDateFormat("MMMM yyyy").format(calendar.getTime()));
            daysPanel.removeAll();
            updateDaysPanel(daysPanel, calendar, listener);
            daysPanel.revalidate();
            daysPanel.repaint();
        });

        nextMonthButton.addActionListener(e -> {
            calendar.add(Calendar.MONTH, 1);
            monthYearLabel.setText(new SimpleDateFormat("MMMM yyyy").format(calendar.getTime()));
            daysPanel.removeAll();
            updateDaysPanel(daysPanel, calendar, listener);
            daysPanel.revalidate();
            daysPanel.repaint();
        });

        panel.add(navigationPanel, BorderLayout.NORTH);
        panel.add(daysPanel, BorderLayout.CENTER);

        return panel;
    }

    private static void updateDaysPanel(JPanel daysPanel, Calendar calendar, DateSelectionListener listener) {
        // Days of the week header
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String dayName : dayNames) {
            JLabel dayLabel = new JLabel(dayName);
            dayLabel.setHorizontalAlignment(JLabel.CENTER);
            daysPanel.add(dayLabel);
        }

        // Get first day of the month
        Calendar firstDay = (Calendar) calendar.clone();
        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);

        // Add empty labels for padding
        for (int i = 1; i < firstDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

        // Add days
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInMonth; i++) {
            JButton dayButton = new JButton(String.valueOf(i));
            dayButton.addActionListener(e -> {
                Calendar selectedDate = (Calendar) calendar.clone();
                selectedDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayButton.getText()));
                listener.onDateSelected(selectedDate.getTime());
            });
            daysPanel.add(dayButton);
        }
    }

    public interface DateSelectionListener {
        void onDateSelected(Date selectedDate);
    }
}   
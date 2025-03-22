package org.Project.CinemaSeatBooking.GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.Project.CinemaSeatBooking.Model.*;
import org.Project.CinemaSeatBooking.Service.*;
import org.Project.CinemaSeatBooking.Utils.BackgroundImageJPanel;
import org.Project.CinemaSeatBooking.Utils.EssentialsUtils;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

public class MovieManagementGUI {

    private static final JPanel content = new JPanel(new BorderLayout());
    private static JPanel currentContent = new JPanel(new BorderLayout()); // To manage card list or detail

    public static JPanel get() throws SQLException {
//        currentContent = refreshData();
        content.removeAll();
        content.add(currentContent, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        return content;
    }

    public static void refreshData() throws SQLException {
        currentContent.removeAll();
        currentContent.setLayout(new BorderLayout());
        currentContent.setPreferredSize(new Dimension(824, 768));
        currentContent.setBackground(new Color(73, 73, 73));
        currentContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JPanel titlePanel = new JPanel(new BorderLayout());

        JButton addNewMovieBtn = new JButton("Add new");
        addNewMovieBtn.setBorderPainted(false);
        addNewMovieBtn.setFocusPainted(false);
        addNewMovieBtn.setBackground(Color.DARK_GRAY);
        addNewMovieBtn.setForeground(Color.WHITE);
        addNewMovieBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addNewMovieBtn.setPreferredSize(new Dimension(150, 30));
        addNewMovieBtn.setMaximumSize(new Dimension(150, 30));
        addNewMovieBtn.addActionListener(e -> {
            addNewMovieData();
        });

        JLabel movieListTitle = new JLabel("Movie Management");
        movieListTitle.setFont(new Font("Arial", Font.BOLD, 24));
        movieListTitle.setHorizontalAlignment(JLabel.LEFT);
        movieListTitle.setForeground(Color.WHITE);
        titlePanel.add(movieListTitle, BorderLayout.WEST);
        titlePanel.add(addNewMovieBtn, BorderLayout.EAST);
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

//        return currentContent;
    }

    private static JPanel getDetail(MovieModel movie) throws SQLException {
        JPanel detailContent = new JPanel(new BorderLayout());
        detailContent.setPreferredSize(new Dimension(824, 768));
        detailContent.setBackground(new Color(73, 73, 73));

        // Header Panel
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(content.getWidth(), 60));
        header.setBorder(new EmptyBorder(20, 20, 20, 20));
        header.setBackground(new Color(73, 73, 73));

        JButton backBtn = new JButton("Back");
        JButton deleteBtn = new JButton("Delete");
        backBtn.addActionListener(e -> {
            try {
                refreshData();
                content.removeAll();
                content.add(currentContent, BorderLayout.CENTER);
                content.revalidate();
                content.repaint();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteBtn.addActionListener(e -> {
            int deleteConfirmation = JOptionPane.showConfirmDialog(
                    HomeGUI.getRootFrame(),
                    "If delete confirmed, all data about movie & ticket will be permanent deleted",
                    "Cinema Seat Booking",
                    JOptionPane.YES_NO_OPTION
            );

            if (deleteConfirmation == JOptionPane.YES_OPTION) {

                String confirmDeleteInput = JOptionPane.showInputDialog(
                        HomeGUI.getRootFrame(),
                        "To delete, please type '" + movie.getTitle() + "'",
                        "Cinema Seat Booking",
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (!movie.getTitle().equals(confirmDeleteInput)) {
                    JOptionPane.showMessageDialog(
                            HomeGUI.getRootFrame(),
                            "Failed to delete (invalid input), please try again",
                            "Cinema Seat Booking",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Delete Query SQL
                List<String> deleteMovieScheduleId = new ArrayList<>();
                List<String> deleteTicketId = new ArrayList<>();

                try {
                    // Get movie schedules to delete
                    List<MovieScheduleModel> deletedMovieScheduleList =
                            new MovieScheduleService().getMany("SELECT * FROM movie_schedule WHERE movie_id = '" + movie.getMovieId() + "'");
                    for (MovieScheduleModel movieScheduleModel : deletedMovieScheduleList) {
                        deleteMovieScheduleId.add(movieScheduleModel.getScheduleId());
                    }

                    if (!deleteMovieScheduleId.isEmpty()) {
                        // Get tickets related to those schedules
                        String scheduleIdString = deleteMovieScheduleId.stream()
                                .map(id -> "'" + id + "'") // Wrap IDs in single quotes
                                .collect(Collectors.joining(","));

                        List<TicketModel> deletedTicketList =
                                new TicketService().getMany("SELECT * FROM ticket WHERE movie_schedule_id IN (" + scheduleIdString + ")");
                        for (TicketModel ticketModel : deletedTicketList) {
                            deleteTicketId.add(ticketModel.getTicketId());
                        }

                        if (!deleteTicketId.isEmpty()) {
                            String ticketIdString = deleteTicketId.stream()
                                    .map(id -> "'" + id + "'")
                                    .collect(Collectors.joining(","));
                            MySQLConnection.query("DELETE FROM ticket_log WHERE ticket_id IN (" + ticketIdString + ")");
                            MySQLConnection.query("DELETE FROM ticket WHERE movie_schedule_id IN (" + scheduleIdString + ")");
                        }

                        MySQLConnection.query("DELETE FROM movie_schedule WHERE movie_id = '" + movie.getMovieId() + "'");
                    }

                    // Fix table name from 'move_genre' to 'movie_genre'
                    MySQLConnection.query("DELETE FROM movie_genre WHERE movie_id = '" + movie.getMovieId() + "'");
                    MySQLConnection.query("DELETE FROM movie WHERE movie_id = '" + movie.getMovieId() + "'");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(HomeGUI.getRootFrame(), "Delete completed", "Cinema Seat Booking", JOptionPane.INFORMATION_MESSAGE);
                try {
                    refreshData();
                    content.removeAll();
                    content.add(currentContent, BorderLayout.CENTER);
                    content.revalidate();
                    content.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        for (JButton btn : new JButton[] { backBtn, deleteBtn }) {
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(103, 103, 103));
            btn.setFont(new Font("Arial", Font.PLAIN, 20));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
        }
        deleteBtn.setBackground(Color.RED);
        header.add(backBtn, BorderLayout.WEST);
        header.add(deleteBtn, BorderLayout.EAST);

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

        for (JButton btn : new JButton[] { datePickerButton, isActivateBtn, saveButton }) {
            btn.setBackground(new Color(103, 103, 103));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
        }

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

    private static void addNewMovieData() {
        JDialog modal = new JDialog(HomeGUI.getRootFrame(), "Add new movie", true);
        modal.setSize(800, 600);
        modal.setLayout(new BorderLayout());
        modal.setLocationRelativeTo(HomeGUI.getRootFrame());

        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(new Color(73, 73, 73));
        body.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and Fields
        JLabel movieTitleLabel = new JLabel("Title:");
        JTextField movieTitleField = new JTextField(20);

        JLabel movieDescriptionLabel = new JLabel("Description:");
        JTextArea movieDescriptionArea = new JTextArea(5, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(movieDescriptionArea);

        JLabel releaseDateLabel = new JLabel("Release Date:");
        JTextField releaseDateField = new JTextField(20);

        JLabel movieImageUrlLabel = new JLabel("Image URL:");
        JTextField movieImageUrlField = new JTextField(20);

        JLabel movieGenresLabel = new JLabel("Genres:");

        List<GenreModel> genreModelList;
        try {
            genreModelList = new GenreService().getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.setOpaque(false);
        List<Integer> selectedGenreIdList = new ArrayList<>();
        for (GenreModel genreModel : genreModelList) {
            JCheckBox checkBox = new JCheckBox(genreModel.getName());
            checkBox.setOpaque(false);
            checkBox.setForeground(Color.WHITE);
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedGenreIdList.add(genreModel.getGenreId());
                } else {
                    selectedGenreIdList.remove(genreModel.getGenreId());
                }

                System.out.println("Current selected : " + selectedGenreIdList.toString());
            });

            checkBoxPanel.add(checkBox);
        }

        // Movie Schedule
        JLabel movieScheduleLabel = new JLabel("Movie Schedule:");

        // Add new movie schedule
        JPanel addNewScheduleInputPanel = new JPanel(new BorderLayout());
        addNewScheduleInputPanel.setOpaque(false);
        String dateTimePlaceholder = "yyyy-mm-dd hh:mm:ss";
        JTextField dateTimeScheduleField = new JTextField(20);
        dateTimeScheduleField.setForeground(Color.GRAY);
        dateTimeScheduleField.setText(dateTimePlaceholder);
        dateTimeScheduleField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dateTimeScheduleField.getText().equals(dateTimePlaceholder)) {
                    dateTimeScheduleField.setText("");
                    dateTimeScheduleField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dateTimeScheduleField.getText().isEmpty()) {
                    dateTimeScheduleField.setText(dateTimePlaceholder);
                    dateTimeScheduleField.setForeground(Color.GRAY);
                }
            }
        });
        List<RoomModel> roomModelList;
        try {
            roomModelList = new RoomService().getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String[] roomNameOptions = new String[roomModelList.size()];
        for (int i = 0; i < roomModelList.size(); i++) {
            roomNameOptions[i] = roomModelList.get(i).getName();
        }

        JComboBox<String> roomComboBox = new JComboBox<>(roomNameOptions);
        roomComboBox.addActionListener(e -> {

            System.out.println("You selected : " + roomComboBox.getSelectedItem());

        });

        addNewScheduleInputPanel.add(roomComboBox, BorderLayout.WEST);
        addNewScheduleInputPanel.add(dateTimeScheduleField, BorderLayout.EAST);

        // Style Labels
        JLabel[] labels = {movieTitleLabel, movieDescriptionLabel, releaseDateLabel, movieGenresLabel, movieImageUrlLabel, movieScheduleLabel};
        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 16));
        }

        // Row 1 - Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        body.add(movieTitleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        body.add(movieTitleField, gbc);

        // Row 2 - Description
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        body.add(movieDescriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        descriptionScrollPane.setPreferredSize(new Dimension(400, 100));
        movieDescriptionArea.setLineWrap(true);
        movieDescriptionArea.setWrapStyleWord(true);
        body.add(descriptionScrollPane, gbc);

        // Row 3 - Release Date
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        body.add(releaseDateLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        body.add(releaseDateField, gbc);

        // Row 4 - Genres
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        body.add(movieGenresLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        body.add(checkBoxPanel, gbc);

        // Row 5 - Image URL
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        body.add(movieImageUrlLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        body.add(movieImageUrlField, gbc);

        // Row 6 - Movie Schedule
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        body.add(movieScheduleLabel, gbc);

        gbc.gridx = 1;
        body.add(addNewScheduleInputPanel, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(73, 73, 73)); // ให้สีตรงกับ body

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        for (JButton btn : new JButton[] { submitButton, cancelButton }) {
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(103, 103, 103));
        }

        cancelButton.addActionListener(e -> modal.dispose());

        submitButton.addActionListener(e -> {
            String title = movieTitleField.getText();
            String description = movieDescriptionArea.getText();
            String imageUrl = movieImageUrlField.getText();
            String releaseDate = releaseDateField.getText();
            String genres = selectedGenreIdList.toString();
            String roomId = roomModelList.stream()
                    .filter(room -> room.getName().equals(roomComboBox.getSelectedItem().toString()))
                    .map(RoomModel::getRoomId)
                    .findFirst()
                    .orElse(null);

            String movieSchedule = dateTimeScheduleField.getText();

            String input = dateTimeScheduleField.getText();
            if (
                    !EssentialsUtils.isValidDateTime(input, false)||
                    !EssentialsUtils.isValidDateTime(releaseDate, true)
            ) {
                JOptionPane.showMessageDialog(HomeGUI.getRootFrame(),
                        "Invalid Date/Time format",
                        "Input Error", JOptionPane.ERROR_MESSAGE);

                return;
            }

            // Query in to database
            // Movie query
            String movieId_PK = MySQLConnection.genreratePK();
            String movieSQL = "INSERT INTO movie " +
                    "(movie_id, movie_title, movie_description, image_url, movie_time, movie_cost_percentage, release_date, is_active) " +
                    "VALUES " +
                    "('" + movieId_PK + "', '" + title + "', '" + description + "', '" + imageUrl + "', '" +
                    2 + "', " + 15.00 + ", '" + releaseDate + "', " + 1 + ");";

            if (MySQLConnection.query(movieSQL) > 0L) {
                for (int i = 0; i < selectedGenreIdList.size(); i++) {
                    String movieGenreSQL = "INSERT INTO movie_genre (movie_id, genre_id) VALUES ('" + movieId_PK + "', " + selectedGenreIdList.get(i) + ")";
                    if (MySQLConnection.query(movieGenreSQL) <= 0L) {
                        System.err.println("Something went wrong (Movie genre query)");
                        return;
                    }
                }

                String movieSchedule_PK = MySQLConnection.genreratePK();
                String movieScheduleSQL = "INSERT INTO movie_schedule (movie_schedule_id, movie_id, room_id, show_time, end_time) VALUES ('" +
                        movieSchedule_PK + "', '" + movieId_PK + "', '" + roomId + "', '" + movieSchedule + "', '" + EssentialsUtils.addHours(movieSchedule, 2) + "')";

                if (MySQLConnection.query(movieScheduleSQL) > 0L) {
                    JOptionPane.showMessageDialog(
                            HomeGUI.getRootFrame(), "Add new movie success", "Cinema Seat Booking", JOptionPane.INFORMATION_MESSAGE
                    );

                    try {
                        refreshData();
                        content.removeAll();
                        content.add(currentContent, BorderLayout.CENTER);
                        content.revalidate();
                        content.repaint();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    modal.dispose();
                }
            }

        });

        modal.add(body, BorderLayout.CENTER);
        modal.add(buttonPanel, BorderLayout.SOUTH);

        modal.setVisible(true);
    }


}   
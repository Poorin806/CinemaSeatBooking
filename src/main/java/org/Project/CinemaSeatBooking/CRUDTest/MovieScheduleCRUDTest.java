package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Model.RoomModel;
import org.Project.CinemaSeatBooking.Service.MovieScheduleService;
import org.Project.CinemaSeatBooking.Service.MovieService;
import org.Project.CinemaSeatBooking.Service.RoomService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieScheduleCRUDTest {

    private static final Scanner scanner = new Scanner(System.in);
    private static final MovieScheduleService movieScheduleService = new MovieScheduleService();

    public MovieScheduleCRUDTest() {

        int menu = 0;

        do {

            System.out.println("[Movie Schedule CRUD]");
            System.out.println("1. Show all data");
            System.out.println("2. Add");
            System.out.println("3. Edit");
            System.out.println("4. Delete");
            System.out.println("0. Exit");
            System.out.print("* Enter choice: ");
            menu = scanner.nextInt();
            scanner.nextLine(); // CLear buffer

            if (menu == 1) Show();
            else if (menu == 2) Insert();
            else if (menu == 3) Edit();
            else if (menu == 4) Delete();

        } while (menu != 0);

        scanner.close();

    }


    private void Show() {
        List<MovieScheduleModel> result = movieScheduleService.FindAll();
        System.out.println(result);
    }

    private void Insert() {

        MovieScheduleModel movieScheduleModel = new MovieScheduleModel();

        int choice = -1;

        // Sub query (Movie Model)
        MovieService movieService = new MovieService();
        List<MovieModel> movieModelList = movieService.FindAll();
        while (choice < 1 || choice > movieModelList.size()) {
            for (int i = 0; i < movieModelList.size(); i++) {
                System.out.println((i + 1) + ") " + movieModelList.get(i).getTitle());
            }
            System.out.print("* Enter movie: ");
            choice = scanner.nextInt();
        }
        movieScheduleModel.setMovieModel(movieModelList.get(choice - 1));

        choice = -1;

        // Sub query (Room Model)
        RoomService roomService = new RoomService();
        List<RoomModel> roomModelList = roomService.FindAll();
        while (choice < 1 || choice > roomModelList.size()) {
            for (int i = 0; i < roomModelList.size(); i++) {
                System.out.println((i + 1) + ") " + roomModelList.get(i).getName());
            }
            System.out.print("* Enter room: ");
            choice = scanner.nextInt();
        }
        movieScheduleModel.setRoomModel(roomModelList.get(choice - 1));

        movieScheduleModel.setShowTime(LocalDateTime.now());
        movieScheduleModel.setEndTime(LocalDateTime.now());

        if (movieScheduleService.InsertOne(movieScheduleModel))
            System.out.println("Inserted");
        else System.out.println("Failed");

    }

    private void Edit() {
        List<MovieScheduleModel> result = movieScheduleService.FindAll();

        int scheduleChoice = -1;

        // Display all schedules
        for (int i = 0; i < result.size(); i++) {
            System.out.println((i + 1) + ") " + result.get(i).getScheduleId());
        }

        while (scheduleChoice < 1 || scheduleChoice > result.size()) {
            System.out.print("* Enter choice: ");
            if (scanner.hasNextInt()) {
                scheduleChoice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        scanner.nextLine(); // Consume leftover newline

        MovieScheduleModel selectedSchedule = result.get(scheduleChoice - 1);

        // Sub query (Movie Model)
        MovieService movieService = new MovieService();
        List<MovieModel> movieModelList = movieService.FindAll();
        int movieChoice = -1;

        while (movieChoice < 1 || movieChoice > movieModelList.size()) {
            for (int i = 0; i < movieModelList.size(); i++) {
                System.out.println((i + 1) + ") " + movieModelList.get(i).getTitle());
            }
            System.out.print("* Enter movie: ");
            if (scanner.hasNextInt()) {
                movieChoice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        selectedSchedule.setMovieModel(movieModelList.get(movieChoice - 1));

        // Sub query (Room Model)
        RoomService roomService = new RoomService();
        List<RoomModel> roomModelList = roomService.FindAll();
        int roomChoice = -1;

        while (roomChoice < 1 || roomChoice > roomModelList.size()) {
            for (int i = 0; i < roomModelList.size(); i++) {
                System.out.println((i + 1) + ") " + roomModelList.get(i).getName());
            }
            System.out.print("* Enter room: ");
            if (scanner.hasNextInt()) {
                roomChoice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        selectedSchedule.setRoomModel(roomModelList.get(roomChoice - 1));

        // Update schedule time
        selectedSchedule.setShowTime(LocalDateTime.now());
        selectedSchedule.setEndTime(LocalDateTime.now().plusHours(2)); // Example: Movie duration is 2 hours

        // Update the schedule in the database
        if (movieScheduleService.UpdateOne(selectedSchedule))
            System.out.println("Updated");
        else
            System.out.println("Failed to update");
    }

    private void Delete() {

        List<MovieScheduleModel> result = movieScheduleService.FindAll();

        int choice = -1;

        for (int i = 0; i < result.size(); i++) {
            System.out.println((i +  1) + ") " + result.get(i).getScheduleId());
        }

        while (choice < 1 || choice > result.size()) {
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
        }
        scanner.nextLine();

        if (movieScheduleService.DeleteOneById(result.get(choice - 1).getScheduleId()))
            System.out.println("Deleted");
        else System.out.println("Failed to delete");

    }

}

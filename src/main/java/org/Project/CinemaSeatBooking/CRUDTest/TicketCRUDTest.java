package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Model.SeatModel;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Service.MovieScheduleService;
import org.Project.CinemaSeatBooking.Service.SeatService;
import org.Project.CinemaSeatBooking.Service.TicketService;

import java.util.List;
import java.util.Scanner;

public class TicketCRUDTest {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TicketService ticketService = new TicketService();

    public TicketCRUDTest() {

        int menu = 0;

        do {

            System.out.println("[Ticket CRUD]");
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

        List<TicketModel> result = ticketService.FindAll();
        System.out.println(result);

    }

    private void Insert() {

        TicketModel ticketModel = new TicketModel();

        System.out.println("* Enter customer name ('-' for none): ");
        String customerName = scanner.nextLine();
        ticketModel.setCustomer(
                customerName == "-" ? null : customerName
        );

        int choice = -1;

        // Sub Input Schedule Model
        MovieScheduleService movieScheduleService = new MovieScheduleService();
        List<MovieScheduleModel> movieScheduleModelList = movieScheduleService.FindAll();

        while (choice < 1 || choice > movieScheduleModelList.size()) {

            for (int i = 0; i < movieScheduleModelList.size(); i++) {
                System.out.println((i + 1) + ") " + movieScheduleModelList.get(i).getScheduleId());
            }
            System.out.print("* Enter schedule id: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }
        ticketModel.setMovieScheduleModel(
                movieScheduleModelList.get(choice - 1)
        );

        choice = -1;

        System.out.println("* Enter total price: ");
        ticketModel.setTotalPrice(scanner.nextDouble());
        scanner.nextLine();

        // Sub Input Seat Model
        SeatService seatService = new SeatService();
        List<SeatModel> seatModelList = seatService.FindAll();
        while (choice < 1 || choice > seatModelList.size()) {
            for (int i = 0; i < seatModelList.size(); i++) {
                System.out.println((i + 1) + ") " + seatModelList.get(i).getSeatId());
            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        ticketModel.setSeatModel(
                seatModelList.get(choice - 1)
        );

        System.out.print("* Enter is active (true / false): ");
        ticketModel.setIsActive(scanner.nextBoolean());

        if (ticketService.InsertOne(ticketModel))
            System.out.println("Inserted");
        else System.out.println("Failed");

    }

    private void Edit() {

        List<TicketModel> ticketModelList = ticketService.FindAll();
        int choice = -1;

        while (choice < 1 || choice > ticketModelList.size()) {

            for (int i = 0; i < ticketModelList.size(); i++) {
                System.out.println((1 + i) + ") " + ticketModelList.get(i).getTicketId());
            }
            System.out.print("* Enter chioce: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }

        TicketModel editData = ticketModelList.get(choice - 1);

        System.out.println("* Enter customer name ('-' for none): ");
        String customerName = scanner.nextLine();
        editData.setCustomer(
                customerName == "-" ? null : customerName
        );

        choice = -1;

        // Sub Input Schedule Model
        MovieScheduleService movieScheduleService = new MovieScheduleService();
        List<MovieScheduleModel> movieScheduleModelList = movieScheduleService.FindAll();

        while (choice < 1 || choice > movieScheduleModelList.size()) {

            for (int i = 0; i < movieScheduleModelList.size(); i++) {
                System.out.println((i + 1) + ") " + movieScheduleModelList.get(i).getScheduleId());
            }
            System.out.print("* Enter schedule id: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }
        editData.setMovieScheduleModel(
                movieScheduleModelList.get(choice - 1)
        );

        choice = -1;

        System.out.println("* Enter total price: ");
        editData.setTotalPrice(scanner.nextDouble());
        scanner.nextLine();

        // Sub Input Seat Model
        SeatService seatService = new SeatService();
        List<SeatModel> seatModelList = seatService.FindAll();
        while (choice < 1 || choice > seatModelList.size()) {
            for (int i = 0; i < seatModelList.size(); i++) {
                System.out.println((i + 1) + ") " + seatModelList.get(i).getSeatId());
            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        editData.setSeatModel(
                seatModelList.get(choice - 1)
        );

        System.out.print("* Enter is active (true / false): ");
        editData.setIsActive(scanner.nextBoolean());

        if (ticketService.UpdateOne(editData))
            System.out.println("Updated");
        else System.out.println("Failed to update");

    }

    private void Delete() {

        List<TicketModel> ticketModelList = ticketService.FindAll();
        int choice = -1;

        while (choice < 1 || choice > ticketModelList.size()) {

            for (int i = 0; i < ticketModelList.size(); i++) {
                System.out.println((1 + i) + ") " + ticketModelList.get(i).getTicketId());
            }
            System.out.print("* Enter chioce: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }

        if (ticketService.DeleteOneById(
                ticketModelList.get(choice - 1).getTicketId()
        ))
            System.out.println("Deleted");
        else System.out.println("Failed to delete");

    }


}

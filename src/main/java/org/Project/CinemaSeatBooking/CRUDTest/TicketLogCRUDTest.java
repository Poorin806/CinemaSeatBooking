package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.LogStatusModel;
import org.Project.CinemaSeatBooking.Model.SeatModel;
import org.Project.CinemaSeatBooking.Model.TicketLogModel;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Service.LogStatusService;
import org.Project.CinemaSeatBooking.Service.SeatService;
import org.Project.CinemaSeatBooking.Service.TicketLogService;
import org.Project.CinemaSeatBooking.Service.TicketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class TicketLogCRUDTest {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TicketLogService ticketLogService = new TicketLogService();

    public TicketLogCRUDTest() {

        int menu = 0;

        do {

            System.out.println("[Ticket Log CRUD]");
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
        List<TicketLogModel> result = ticketLogService.FindAll();
        System.out.println(result);
    }

    private void Insert() {

        TicketLogModel ticketLogModel = new TicketLogModel();

        int choice = -1;

        // Sub input Ticket Model
        TicketService ticketService = new TicketService();
        List<TicketModel> ticketModelList = ticketService.FindAll();

        while (choice < 1 || choice > ticketModelList.size()) {

            for (int i = 0; i < ticketModelList.size(); i++) {
                System.out.println((i + 1) + ") " + ticketModelList.get(i).getTicketId());
            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }
        ticketLogModel.setTicketModel(
                ticketModelList.get(choice - 1)
        );

        choice = -1;

        ticketLogModel.setTimestamp(LocalDateTime.now());

        // Sub input Log Status Model
        LogStatusService logStatusService = new LogStatusService();
        List<LogStatusModel> logStatusModelList = logStatusService.FindAll();

        while (choice < 1 || choice > logStatusModelList.size()) {

            for (int i = 0; i < logStatusModelList.size(); i++) {
                System.out.println(i + 1 + ") " + logStatusModelList.get(i).getName());
            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }
        ticketLogModel.setLogStatusModel(
                logStatusModelList.get(choice - 1)
        );

        choice = -1;

        // Sub input Seat Model
        SeatService seatService = new SeatService();
        List<SeatModel> seatModelList = seatService.FindAll();

        while (choice < 1 || choice > seatModelList.size()) {
            for (int i = 0; i < seatModelList.size(); i++) {
                System.out.println((i + 1) + ") " + seatModelList.get(i).getRow() + seatModelList.get(i).getNumber());
            }
            System.out.println("[Current seat]");
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        ticketLogModel.setCurrentSeat(
                seatModelList.get(choice - 1)
        );

        choice = -1;

        while (choice < 1 || choice > seatModelList.size()) {
            for (int i = 0; i < seatModelList.size(); i++) {
                System.out.println((i + 1) + ") " + seatModelList.get(i).getRow() + seatModelList.get(i).getNumber());
            }
            System.out.println("[New seat]");
            System.out.print("* Enter choice: ('-1' for cancel): ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == -1) break;
        }
        ticketLogModel.setNewSeat(
                choice != -1 ? seatModelList.get(choice - 1) : null
        );

        choice = -1;

        System.out.print("* Enter note ('-' for none): ");
        String tmpInput = scanner.nextLine();
        ticketLogModel.setNote(
                (Objects.equals(tmpInput, "-")) ? null : tmpInput
        );

        ticketLogModel.setTimestamp(LocalDateTime.now());

        if (ticketLogService.InsertOne(ticketLogModel))
            System.out.println("Inserted");
        else System.out.println("Failed to insert");

    }

    private void Edit() {

        int choice = -1;
        List<TicketLogModel> ticketLogModelList = ticketLogService.FindAll();
        while (choice < 1 || choice > ticketLogModelList.size()) {

            for (int i = 0; i < ticketLogModelList.size(); i++)
                System.out.println((i + 1) + ") " + ticketLogModelList.get(i).get_id());

            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        TicketLogModel editData = ticketLogModelList.get(choice - 1);
        choice = -1;

        // Sub input Ticket Model
        TicketService ticketService = new TicketService();
        List<TicketModel> ticketModelList = ticketService.FindAll();

        while (choice < 1 || choice > ticketModelList.size()) {

            for (int i = 0; i < ticketModelList.size(); i++) {
                System.out.println((i + 1) + ") " + ticketModelList.get(i).getTicketId());
            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }
        editData.setTicketModel(
                ticketModelList.get(choice - 1)
        );

        choice = -1;

        editData.setTimestamp(LocalDateTime.now());

        // Sub input Log Status Model
        LogStatusService logStatusService = new LogStatusService();
        List<LogStatusModel> logStatusModelList = logStatusService.FindAll();

        while (choice < 1 || choice > logStatusModelList.size()) {

            for (int i = 0; i < logStatusModelList.size(); i++) {
                System.out.println(i + 1 + ") " + logStatusModelList.get(i).getName());
            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }
        editData.setLogStatusModel(
                logStatusModelList.get(choice - 1)
        );

        choice = -1;

        // Sub input Seat Model
        SeatService seatService = new SeatService();
        List<SeatModel> seatModelList = seatService.FindAll();

        while (choice < 1 || choice > seatModelList.size()) {
            for (int i = 0; i < seatModelList.size(); i++) {
                System.out.println((i + 1) + ") " + seatModelList.get(i).getRow() + seatModelList.get(i).getNumber());
            }
            System.out.println("[Current seat]");
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        editData.setCurrentSeat(
                seatModelList.get(choice - 1)
        );

        choice = -1;

        while (choice < 1 || choice > seatModelList.size()) {
            for (int i = 0; i < seatModelList.size(); i++) {
                System.out.println((i + 1) + ") " + seatModelList.get(i).getRow() + seatModelList.get(i).getNumber());
            }
            System.out.println("[New seat]");
            System.out.print("* Enter choice: ('-1' for cancel): ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == -1) break;
        }
        editData.setNewSeat(
                choice == -1 ? null : seatModelList.get(choice - 1)
        );

        choice = -1;

        System.out.print("* Enter note ('-' for none): ");
        String tmpInput = scanner.nextLine();
        editData.setNote(
                (Objects.equals(tmpInput, "-")) ? null : tmpInput
        );

        editData.setTimestamp(LocalDateTime.now());

        if (ticketLogService.UpdateOne(editData))
            System.out.println("Updated");
        else System.out.println("Failed to update");

    }

    private void Delete() {

        int choice = -1;
        List<TicketLogModel> ticketLogModelList = ticketLogService.FindAll();
        while (choice < 1 || choice > ticketLogModelList.size()) {

            for (int i = 0; i < ticketLogModelList.size(); i++)
                System.out.println((i + 1) + ") " + ticketLogModelList.get(i).get_id());

            System.out.print("* Enter chioce: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        TicketLogModel deleteData = ticketLogModelList.get(choice - 1);
        choice = -1;

        if (ticketLogService.DeleteOneById(deleteData.get_id()))
            System.out.println("Deleted");
        else System.out.println("Failed to delete");

    }

}

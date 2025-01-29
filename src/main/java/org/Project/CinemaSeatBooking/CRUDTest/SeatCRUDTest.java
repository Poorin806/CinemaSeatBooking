package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.RoomModel;
import org.Project.CinemaSeatBooking.Model.SeatModel;
import org.Project.CinemaSeatBooking.Model.SeatTypeModel;
import org.Project.CinemaSeatBooking.Service.RoomService;
import org.Project.CinemaSeatBooking.Service.SeatService;
import org.Project.CinemaSeatBooking.Service.SeatTypeService;

import java.util.List;
import java.util.Scanner;

public class SeatCRUDTest {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SeatService seatService = new SeatService();

    public SeatCRUDTest() {

        int menu = 0;

        do {

            System.out.println("[Seat CRUD]");
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

        List<SeatModel> result = seatService.FindAll();
        System.out.println(result);

    }

    private void Insert() {
        SeatModel seatModel = new SeatModel();
        int choice = -1;

        // Sub input Seat Type
        SeatTypeService seatTypeService = new SeatTypeService();
        List<SeatTypeModel> seatTypeModelList = seatTypeService.FindAll();

        while (choice < 1 || choice > seatTypeModelList.size()) {
            for (int i = 0; i < seatTypeModelList.size(); i++) {
                System.out.println((i + 1) + ") " + seatTypeModelList.get(i).getName());
            }
            System.out.print("* Enter choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        seatModel.setSeatTypeModel(seatTypeModelList.get(choice - 1));
        choice = -1;

        // Sub input Room Model
        RoomService roomService = new RoomService();
        List<RoomModel> roomModelList = roomService.FindAll();

        while (choice < 1 || choice > roomModelList.size()) {
            for (int i = 0; i < roomModelList.size(); i++) {
                System.out.println((i + 1) + ") " + roomModelList.get(i).getName());
            }
            System.out.print("* Enter choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        seatModel.setRoomModel(roomModelList.get(choice - 1));

        // Seat Row Input
        String seatRow;
        do {
            System.out.print("* Enter seat row (A-Z): ");
            seatRow = scanner.nextLine().toUpperCase();
            if (!seatRow.matches("[A-Z]")) {
                System.out.println("Invalid input. Please enter a single letter between A and Z.");
            }
        } while (!seatRow.matches("[A-Z]"));
        seatModel.setRow(seatRow);

        // Seat Number Input
        int seatNumber = -1;
        while (seatNumber < 1 || seatNumber > 20) {
            System.out.print("* Enter seat number (1-20): ");
            if (scanner.hasNextInt()) {
                seatNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 20.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        seatModel.setNumber(seatNumber);

        // Insert Seat Model
        if (seatService.InsertOne(seatModel))
            System.out.println("Inserted");
        else
            System.out.println("Failed");
    }


    private void Delete() {

        int choice = -1;

        List<SeatModel> result = seatService.FindAll();

        while (choice < 1 || choice > result.size()) {
            for (int i = 0; i < result.size(); i++) {
                System.out.println((i + 1) + ") " + result.get(i).getRow() + result.get(i).getNumber());
            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
        }

        if (seatService.DeleteOneById(
                result.get(choice - 1).getSeatId()
        ))
            System.out.println("Deleted");
        else
            System.out.println("Failed to delete");
    }

    private void Edit() {

        int choice = -1;

        // Get all seats
        List<SeatModel> result = seatService.FindAll();
        if (result.isEmpty()) {
            System.out.println("No seats available to edit.");
            return;
        }

        // Display seat list and get user choice
        while (choice < 1 || choice > result.size()) {
            for (int i = 0; i < result.size(); i++) {
                System.out.println((i + 1) + ") " + result.get(i).getRow() + result.get(i).getNumber());
            }
            System.out.print("* Enter choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        SeatModel editData = result.get(choice - 1);
        choice = -1;

        // Sub input Seat Type
        SeatTypeService seatTypeService = new SeatTypeService();
        List<SeatTypeModel> seatTypeModelList = seatTypeService.FindAll();
        if (seatTypeModelList.isEmpty()) {
            System.out.println("No seat types available.");
            return;
        }

        while (choice < 1 || choice > seatTypeModelList.size()) {
            for (int i = 0; i < seatTypeModelList.size(); i++) {
                System.out.println((i + 1) + ") " + seatTypeModelList.get(i).getName());
            }
            System.out.print("* Enter choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        editData.setSeatTypeModel(seatTypeModelList.get(choice - 1));
        choice = -1;

        // Sub input Room Model
        RoomService roomService = new RoomService();
        List<RoomModel> roomModelList = roomService.FindAll();
        if (roomModelList.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        while (choice < 1 || choice > roomModelList.size()) {
            for (int i = 0; i < roomModelList.size(); i++) {
                System.out.println((i + 1) + ") " + roomModelList.get(i).getName());
            }
            System.out.print("* Enter choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        editData.setRoomModel(roomModelList.get(choice - 1));

        // Seat Row Input
        String seatRow;
        do {
            System.out.print("* Enter seat row (A-Z): ");
            seatRow = scanner.next().toUpperCase();
            if (!seatRow.matches("[A-Z]")) {
                System.out.println("Invalid input. Please enter a single letter between A and Z.");
            }
        } while (!seatRow.matches("[A-Z]"));
        editData.setRow(seatRow);

        // Seat Number Input
        int seatNumber = -1;
        while (seatNumber < 1 || seatNumber > 20) {
            System.out.print("* Enter seat number (1-20): ");
            if (scanner.hasNextInt()) {
                seatNumber = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a valid number between 1 and 20.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        editData.setNumber(seatNumber);

        // Update Seat Model
        if (seatService.UpdateOne(editData)) {
            System.out.println("Updated successfully.");
        } else {
            System.out.println("Failed to update.");
        }
    }


}

package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.RoomModel;
import org.Project.CinemaSeatBooking.Service.RoomService;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class RoomCRUDTest {

    private static final Scanner scanner = new Scanner(System.in);
    private static final RoomService roomService = new RoomService();

    public RoomCRUDTest() {

        int menu = 0;

        do {

            System.out.println("[Room CRUD]");
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
        List<RoomModel> result = roomService.FindAll();
        System.out.println(result);
    }

    private void Insert() {

        RoomModel roomModel = new RoomModel();
        System.out.println("\n[Insert data]");
        System.out.print("* Enter room name: ");
        roomModel.setName(scanner.nextLine());



        if (roomService.InsertOne(roomModel)) JOptionPane.showMessageDialog(null, "Result = 100");
        else System.out.println("Failed to insert");

    }
    private void Edit() {

        List<RoomModel> result = roomService.FindAll();

        int choice = -1;

        for (int i = 0; i < result.size(); i++) {
            System.out.println((i +  1) + ") " + result.get(i).getName());
        }

        while (choice < 1 || choice > result.size()) {
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
        }
        scanner.nextLine();

        RoomModel roomModel = new RoomModel();
        roomModel.setRoomId(result.get(choice - 1).getRoomId());
        System.out.print("* Enter new name: ");
        roomModel.setName(scanner.nextLine());

        if (roomService.UpdateOne(roomModel)) System.out.println("Updated");
        else System.out.println("Failed to update");

    }

    private void Delete() {
        List<RoomModel> result = roomService.FindAll();

        int choice = -1;

        for (int i = 0; i < result.size(); i++) {
            System.out.println((i +  1) + ") " + result.get(i).getName());
        }

        while (choice < 1 || choice > result.size()) {
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
        }
        scanner.nextLine();

        if (roomService.DeleteOneById(result.get(choice - 1).getRoomId()))
            System.out.println("Deleted");
        else System.out.println("Failed to delete");
    }

}

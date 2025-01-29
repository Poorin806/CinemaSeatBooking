package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.SeatTypeModel;
import org.Project.CinemaSeatBooking.Service.SeatTypeService;

import java.util.List;
import java.util.Scanner;

public class SeatTypeCRUDTest {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SeatTypeService seatTypeService = new SeatTypeService();

    public SeatTypeCRUDTest() {
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
        List<SeatTypeModel> result = seatTypeService.FindAll();
        System.out.println(result);
    }

    private void Insert() {

        SeatTypeModel seatTypeModel = new SeatTypeModel();
        System.out.println("\n[Insert data]");
        System.out.print("* Enter seat type name: ");
        seatTypeModel.setName(scanner.nextLine());

        System.out.print("* Enter price: ");
        seatTypeModel.setPrice(scanner.nextDouble());

        if (seatTypeService.InsertOne(seatTypeModel)) System.out.println("Inserted");
        else System.out.println("Failed");

    }

    private void Delete() {

        List<SeatTypeModel> result = seatTypeService.FindAll();
        int choice = -1;

        for (int i = 0; i < result.size(); i++) {
            System.out.println((i +  1) + ") " + result.get(i).getName());
        }

        while (choice < 1 || choice > result.size()) {
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
        }
        scanner.nextLine();

        if (seatTypeService.DeleteOneById(
                result.get(choice - 1).getSeatTypeId())
        ) System.out.println("Deleted");
        else System.out.println("Failed");

    }

    private void Edit() {

        List<SeatTypeModel> result = seatTypeService.FindAll();
        int choice = -1;

        for (int i = 0; i < result.size(); i++) {
            System.out.println((i +  1) + ") " + result.get(i).getName());
        }

        while (choice < 1 || choice > result.size()) {
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
        }
        scanner.nextLine();

        SeatTypeModel seatTypeModel = result.get(choice - 1);
        System.out.print("* Enter seat type name: ");
        seatTypeModel.setName(scanner.nextLine());

        System.out.print("* Enter price: ");
        seatTypeModel.setPrice(scanner.nextDouble());

        if (seatTypeService.UpdateOne(seatTypeModel))
            System.out.println("Updated");
        else System.out.println("Failed");

    }
}

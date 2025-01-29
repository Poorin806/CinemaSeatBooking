package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.LogStatusModel;
import org.Project.CinemaSeatBooking.Service.LogStatusService;

import java.util.List;
import java.util.Scanner;

public class LogStatusCRUDTest {

    private static final Scanner scanner = new Scanner(System.in);
    private static final LogStatusService logStatusService = new LogStatusService();

    public LogStatusCRUDTest() {

        int menu = 0;

        do {

            System.out.println("[Log status CRUD]");
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
        List<LogStatusModel> logStatusModelList = logStatusService.FindAll();
        System.out.println(logStatusModelList);
    }

    private void Insert() {

        LogStatusModel logStatusModel = new LogStatusModel();

        System.out.print("* Enter log name: ");
        logStatusModel.setName(scanner.nextLine());

        if (logStatusService.InsertOne(logStatusModel))
            System.out.println("Inserted");
        else System.out.println("Failed to insert");

    }

    private void Edit() {

        List<LogStatusModel> logStatusModelList = logStatusService.FindAll();
        int choice = -1;

        while (choice < 1 || choice > logStatusModelList.size()) {

            for (int i = 0; i < logStatusModelList.size(); i++) {

                System.out.println((1+i) + ") " + logStatusModelList.get(i).getName());

            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }

        System.out.println("* Enter name name: ");
        logStatusModelList.get(choice - 1).setName(scanner.nextLine());

        if (
                logStatusService.UpdateOne(
                        logStatusModelList.get(choice - 1)
                )
        ) System.out.println("Updated");
        else System.out.println("Failed to update");

    }

    private void Delete() {

        List<LogStatusModel> logStatusModelList = logStatusService.FindAll();
        int choice = -1;

        while (choice < 1 || choice > logStatusModelList.size()) {

            for (int i = 0; i < logStatusModelList.size(); i++) {

                System.out.println((1+i) + ") " + logStatusModelList.get(i).getName());

            }
            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }

        if (
                logStatusService.DeleteOneById(
                        logStatusModelList.get(choice - 1).getLogStatusId()
                )
        )
            System.out.println("Deleted");
        else System.out.println("Failed to delete");

    }

}

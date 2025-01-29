package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.GenreModel;
import org.Project.CinemaSeatBooking.Service.GenreService;

import java.util.List;
import java.util.Scanner;

public class GenreCRUDTest {

    private static final Scanner scanner = new Scanner(System.in);
    private static final GenreService genreService = new GenreService();

    public GenreCRUDTest() {

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

        List<GenreModel> genreModelList = genreService.FindAll();
        System.out.println(genreModelList);

    }

    private void Insert() {
        GenreModel genreModel = new GenreModel();

        System.out.print("* Enter genre name: ");
        genreModel.setName(scanner.nextLine());

        if (genreService.InsertOne(genreModel))
            System.out.println("Inserted");
        else System.out.println("Failed to insert");
    }

    private void Delete() {

        List<GenreModel> genreModelList = genreService.FindAll();

        int choice = -1;

        while (choice < 1 || choice > genreModelList.size()) {

            for (int i = 0; i < genreModelList.size(); i++)
                System.out.println((i + 1) + ") " + genreModelList.get(i).getName());

            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }

        if (genreService.DeleteOneById(
                genreModelList.get(choice - 1).getGenreId()
        ))
            System.out.println("Deleted");
        else System.out.println("Failed to delete");

    }

    private void Edit() {

        List<GenreModel> genreModelList = genreService.FindAll();

        int choice = -1;

        while (choice < 1 || choice > genreModelList.size()) {

            for (int i = 0; i < genreModelList.size(); i++)
                System.out.println((i + 1) + ") " + genreModelList.get(i).getName());

            System.out.print("* Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

        }
        GenreModel editData = genreModelList.get(choice - 1);
        choice = -1;

        System.out.println("* Enter new name: ");
        editData.setName(scanner.nextLine());

        if (genreService.UpdateOne(editData))
            System.out.println("Updated");
        else System.out.println("Failed to update");

    }

}

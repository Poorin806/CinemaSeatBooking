package org.Project.CinemaSeatBooking.CRUDTest;

import org.Project.CinemaSeatBooking.Model.GenreModel;
import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Service.GenreService;
import org.Project.CinemaSeatBooking.Service.MovieService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MovieCRUDTest {

    private final Scanner scanner = new Scanner(System.in);

    public MovieCRUDTest() {

        int menu = 0;

        do {

            System.out.println("[Movie CRUD]");
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

        } while (menu != 0);

        scanner.close();

    }

    private void Show() {

        MovieService movieService = new MovieService();
        List<MovieModel> movieModelList = movieService.FindAll();
        for (MovieModel movieModel : movieModelList) {
            System.out.println(movieModel);
        }

        System.out.println("\n\n");

    }

    private void Insert() {
        MovieModel movieModel = new MovieModel();

        System.out.print("\n\n*Enter the movie title: ");
        movieModel.setTitle(scanner.nextLine());

        System.out.print("\n\n*Enter the movie description: ");
        movieModel.setDescription(scanner.nextLine());

        // TODO: Must use a file upload
        System.out.print("\n\n*Enter the image URL: ");
        movieModel.setImageURL(scanner.nextLine());

        System.out.print("\n\n*Enter the movie cost (%): ");
        movieModel.setMovieCost(scanner.nextDouble());
        scanner.nextLine();

        movieModel.setReleaseDate(LocalDate.now());
        movieModel.setIsActive(true);

        System.out.print("\n\n*Enter the movie time: ");
        movieModel.setMovieTime(scanner.nextInt());
        scanner.nextLine();

        int genreSelection = -1;
        List<GenreModel> genreModelList = new ArrayList<>();
        while (true) {
            GenreService genreService = new GenreService();
            List<GenreModel> genreModelList_tmp = genreService.FindAll();

            for (int i = 0; i < genreModelList_tmp.size(); i++) {
                System.out.println("- " + (i + 1) + ". " + genreModelList_tmp.get(i).getName());
            }
            System.out.print("* Enter genre (0 for exit): ");

            try {
                genreSelection = scanner.nextInt();
                scanner.nextLine(); // เคลียร์บัฟเฟอร์หลังจาก nextInt()
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // เคลียร์ input ที่ผิดพลาด
                continue;
            }

            if (genreSelection == 0) {
                if (!genreModelList.isEmpty()) break;
                else System.out.println("Genres list is empty");
            } else if (genreSelection > 0 && genreSelection <= genreModelList_tmp.size()) {
                GenreModel selectedGenre = genreModelList_tmp.get(genreSelection - 1);
                if (genreModelList.stream().noneMatch(genre -> genre.getGenreId().equals(selectedGenre.getGenreId()))) {
                    genreModelList.add(selectedGenre);
                } else {
                    System.out.println("This genre is already selected.");
                }
            } else {
                System.out.println("Invalid input.");
            }
        }
        movieModel.setGenreList(genreModelList);

//        System.out.println("Debugging: \n" + movieModel);

        // Insert to database
        MovieService movieService = new MovieService();
        if (movieService.InsertOne(movieModel)) System.out.println("Added " + movieModel);
        else System.out.println("Failed to insert " + movieModel);

    }

    private void Edit() {

        MovieService movieService = new MovieService();
        List<MovieModel> movieModelList = movieService.FindAll();
        int editChoice = -1;

        do {

            for (int i = 0; i < movieModelList.size(); i++) {
                System.out.println("- " + (1 + i) + ") " + movieModelList.get(i).getTitle());
            }
            System.out.print("* Enter the number of movies to edit (0 = cancel): ");
            editChoice = scanner.nextInt();
            scanner.nextLine();
            if (editChoice == 0) return;
        } while (editChoice < 0 || editChoice > movieModelList.size());

        System.out.println("\n\n** Movie selected: " + movieModelList.get(editChoice - 1).getTitle() + " **");

        // Updating
        MovieModel movieModel = new MovieModel();
        movieModel.setMovieId(movieModelList.get(editChoice - 1).getMovieId());
        System.out.print("\n\n*Enter the movie title (default: " + movieModelList.get(editChoice - 1).getTitle() + "): ");
        movieModel.setTitle(scanner.nextLine());

        System.out.print("\n\n*Enter the movie description (default: " + movieModelList.get(editChoice - 1).getDescription() + "): ");
        movieModel.setDescription(scanner.nextLine());

        System.out.print("\n\n*Enter the image URL (default: " + movieModelList.get(editChoice - 1).getImageURL() + "): ");
        movieModel.setImageURL(scanner.nextLine());

        System.out.print("\n\n*Enter the movie cost (%) (default: " + movieModelList.get(editChoice - 1).getMovieCost() + "): ");
        movieModel.setMovieCost(scanner.nextDouble());
        scanner.nextLine();

        movieModel.setReleaseDate(LocalDate.now());
        movieModel.setIsActive(true);

        System.out.print("\n\n*Enter the movie time (default: " + movieModelList.get(editChoice - 1).getMovieTime() + "): ");
        movieModel.setMovieTime(scanner.nextInt());
        scanner.nextLine();

        int genreSelection = -1;
        List<GenreModel> genreModelList = movieModelList.get(editChoice - 1).getGenreList();
        while (true) {
            GenreService genreService = new GenreService();
            List<GenreModel> genreModelList_tmp = genreService.FindAll();

            for (int i = 0; i < genreModelList_tmp.size(); i++) {
                System.out.println("- " + (i + 1) + ". " + genreModelList_tmp.get(i).getName());
            }
            System.out.print("* Enter genre (0 for exit): ");

            try {
                genreSelection = scanner.nextInt();
                scanner.nextLine(); // เคลียร์บัฟเฟอร์หลังจาก nextInt()
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // เคลียร์ input ที่ผิดพลาด
                continue;
            }

            if (genreSelection == 0) {
                if (!genreModelList.isEmpty()) break;
                else System.out.println("Genres list is empty");
            } else if (genreSelection > 0 && genreSelection <= genreModelList_tmp.size()) {
                GenreModel selectedGenre = genreModelList_tmp.get(genreSelection - 1);
                if (genreModelList.stream().noneMatch(genre -> genre.getGenreId().equals(selectedGenre.getGenreId()))) {
                    genreModelList.add(selectedGenre);
                } else {
                    System.out.println("This genre is already selected.");
                }
            } else {
                System.out.println("Invalid input.");
            }
        }
        movieModel.setGenreList(genreModelList);

//        System.out.println("Debugging: \n" + movieModel);

        if (movieService.UpdateOne(movieModel)) System.out.println("Updated \n" + movieModel);
        else System.out.println("Failed to update: \n" + movieModel);

    }

}

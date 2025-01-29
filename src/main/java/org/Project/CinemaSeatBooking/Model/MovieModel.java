package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.GenreService;
import org.bson.Document;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Data
public class MovieModel {

    private String MovieId;

    private String Title;

    private String Description;

    private String ImageURL;

    private List<GenreModel> GenreList;

    private Integer MovieTime;  // Minutes units (Ex: MovieTime = 60 = 1 hour)

    private Double MovieCost; // Percentage of income to be shared with the film owner

    private LocalDate ReleaseDate;  // Format: yyyy-MM-dd

    private Boolean IsActive; // Whether to show the film (true = the move is still showing, false = the movie has been removed from the cinema)

    public MovieModel DTO(Document doc) {
        MovieModel result = new MovieModel();

        result.setMovieId(doc.getObjectId("_id").toString());
        result.setTitle(doc.getString("Title"));
        result.setDescription(doc.getString("Description"));
        result.setImageURL(doc.getString("ImageURL"));

        // Sub DTO (Genre)
        GenreService genreService = new GenreService();
        List<GenreModel> genreModelList = new ArrayList<>();
        List<String> genreIdList = (List<String>) doc.get("GenreList");
        for (String genreId : genreIdList) {
            GenreModel genreModel = genreService.FindOneById(genreId);
            genreModelList.add(genreModel);
        }
        result.setGenreList(genreModelList);

        result.setMovieTime(doc.getInteger("MovieTime"));
        result.setMovieCost(doc.getDouble("MovieCost"));

        // Date format
        final LocalDate localDate = doc.getDate("ReleaseDate").toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        result.setReleaseDate(localDate);

        result.setIsActive(doc.getBoolean("IsActive"));

        return result;
    }

}

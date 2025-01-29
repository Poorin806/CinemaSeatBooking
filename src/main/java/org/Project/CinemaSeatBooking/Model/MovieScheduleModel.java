package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.MovieService;
import org.Project.CinemaSeatBooking.Service.RoomService;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class MovieScheduleModel {

    private String ScheduleId;

    private MovieModel movieModel;

    private RoomModel roomModel;

    private LocalDateTime ShowTime; // Format: yyyyy-MM-dd hh:mm:ss

    private LocalDateTime EndTime; // Format: yyyyy-MM-dd hh:mm:ss

    public MovieScheduleModel DTO(Document doc) {

        MovieScheduleModel movieScheduleModel = new MovieScheduleModel();

        movieScheduleModel.setScheduleId(
                doc.getObjectId("_id").toString()
        );

        // Sub DTO Movie Model
        MovieService movieService = new MovieService();
        MovieModel movieModelTmp = movieService.FindOneById(
                doc.getString("MovieId")
        );
        movieScheduleModel.setMovieModel(movieModelTmp);

        // Sub DTO Room Model
        RoomService roomService = new RoomService();
        RoomModel roomModelTmp = roomService.FindOneById(
                doc.getString("RoomId")
        );
        movieScheduleModel.setRoomModel(roomModelTmp);

        // Convert Date to LocalDateTime for ShowTime
        if (doc.getDate("ShowTime") != null) {
            movieScheduleModel.setShowTime(
                    doc.getDate("ShowTime").toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
            );
        }

        // Optional: Handle EndTime if required
        if (doc.getDate("EndTime") != null) {
            movieScheduleModel.setEndTime(
                    doc.getDate("EndTime").toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
            );
        }

        return movieScheduleModel;

    }

}

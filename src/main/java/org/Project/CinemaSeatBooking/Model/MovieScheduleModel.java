package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.Project.CinemaSeatBooking.Service.MovieService;
import org.Project.CinemaSeatBooking.Service.RoomService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
public class MovieScheduleModel {

    private String scheduleId;

    private MovieModel movieModel;

    private RoomModel roomModel;

    private LocalDateTime showTime; // Format: yyyyy-MM-dd hh:mm:ss

    private LocalDateTime endTime; // Format: yyyyy-MM-dd hh:mm:ss

    private static List<MovieModel> movieModelList = null;
    private static List<RoomModel> roomModelList = null;

    public MovieScheduleModel() {}

    public MovieScheduleModel(ResultSet resultSet) throws SQLException {

        this.scheduleId = resultSet.getString("movie_schedule_id");

        String movieId = resultSet.getString("movie_id");
        if (movieModelList == null) movieModelList = new MovieService().getAll();
        for (MovieModel tmp : movieModelList) {
            if (tmp.getMovieId().equals(movieId)) this.movieModel = tmp;
        }

        String roomId = resultSet.getString("room_id");
        if (roomModelList == null) roomModelList = new RoomService().getAll();
        for (RoomModel tmp : roomModelList) {
            if (tmp.getRoomId().equals(roomId)) this.roomModel = tmp;
        }

        this.showTime = resultSet.getObject("show_time", LocalDateTime.class);
        this.endTime = resultSet.getObject("end_time", LocalDateTime.class);


    }

}

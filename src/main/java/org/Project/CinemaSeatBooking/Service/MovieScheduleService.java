package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.MovieScheduleModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MovieScheduleService implements MongoDBQuery<MovieScheduleModel> {

    private final MongoDBConnection mongoDB;

    public MovieScheduleService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("MovieSchedule");
    }

    @Override
    public Boolean InsertOne(MovieScheduleModel movieScheduleModel) {

        try {

            InsertOneResult result = mongoDB.collection.insertOne(
                    new Document()
                            .append("_id", new ObjectId())
                            .append("MovieId", movieScheduleModel.getMovieModel().getMovieId())
                            .append("RoomId", movieScheduleModel.getRoomModel().getRoomId())
                            .append("ShowTime", movieScheduleModel.getShowTime())
                            .append("EndTime", movieScheduleModel.getEndTime())
            );

            return result.getInsertedId() != null;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<MovieScheduleModel> movieScheduleModelList) {

        try {

            List<Document> dataList = new ArrayList<>();
            for (MovieScheduleModel movieScheduleModel : movieScheduleModelList) {

                dataList.add(
                        new Document()
                                .append("_id", new ObjectId())
                                .append("MovieId", movieScheduleModel.getMovieModel().getMovieId())
                                .append("RoomId", movieScheduleModel.getRoomModel().getRoomId())
                                .append("ShowTime", movieScheduleModel.getShowTime())
                                .append("EndTime", movieScheduleModel.getEndTime())
                );

            }

            InsertManyResult result = mongoDB.collection.insertMany(dataList);
            return !result.getInsertedIds().isEmpty();

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<MovieScheduleModel> FindAll() {

        try {

            List<MovieScheduleModel> result = new ArrayList<>();
            FindIterable<Document> rawDataList = mongoDB.collection.find();
            for (Document rawData : rawDataList) {

                MovieScheduleModel movieScheduleModel = new MovieScheduleModel();
                movieScheduleModel = movieScheduleModel.DTO(rawData);
                result.add(movieScheduleModel);

            }

            return result;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public MovieScheduleModel FindOneById(String scheduleId) {

        try {

            Bson query = eq("_id", new ObjectId(scheduleId));
            Document data = mongoDB.collection.find().first();

            if (data == null) return null;
            MovieScheduleModel movieScheduleModel = new MovieScheduleModel();
            movieScheduleModel = movieScheduleModel.DTO(data);

            return movieScheduleModel;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String scheduleId) {
        try {
            Bson query = eq("_id", new ObjectId(scheduleId));
            DeleteResult result = mongoDB.collection.deleteOne(query);

            return result.getDeletedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean DeleteManyById(List<String> scheduleIdList) {
        try {
            for (String scheduleId : scheduleIdList) {
                Bson query = eq("_id", new ObjectId(scheduleId));
                mongoDB.collection.deleteOne(query);
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateOne(MovieScheduleModel movieScheduleModel) {

        try {

            Bson filters = eq("_id", new ObjectId(movieScheduleModel.getScheduleId()));
            Bson query = Updates.combine(
                    Updates.set("MovieId", movieScheduleModel.getMovieModel().getMovieId()),
                    Updates.set("RoomId", movieScheduleModel.getRoomModel().getRoomId()),
                    Updates.set("ShowTime", movieScheduleModel.getShowTime()),
                    Updates.set("EndTime", movieScheduleModel.getEndTime())
            );

            UpdateResult result = mongoDB.collection.updateOne(filters, query);

            return result.getModifiedCount() > 0L;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<MovieScheduleModel> movieScheduleModelList) {

        try {

            for (MovieScheduleModel movieScheduleModel : movieScheduleModelList) {
                Bson filters = eq("_id", new ObjectId(movieScheduleModel.getScheduleId()));
                Bson query = Updates.combine(
                        Updates.set("MovieId", movieScheduleModel.getMovieModel().getMovieId()),
                        Updates.set("RoomId", movieScheduleModel.getRoomModel().getRoomId()),
                        Updates.set("ShowTime", movieScheduleModel.getShowTime()),
                        Updates.set("EndTime", movieScheduleModel.getEndTime())
                );

                UpdateResult result = mongoDB.collection.updateOne(filters, query);

                if (result.getModifiedCount() <= 0L) return false;
            }

            return true;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }
}

package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.GenreModel;
import org.Project.CinemaSeatBooking.Model.MovieModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MovieService implements MongoDBQuery<MovieModel> {

    private final MongoDBConnection mongoDB;

    // Constructor
    public MovieService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("Movie");
    }

    @Override
    public Boolean InsertOne(MovieModel movieModel) {
        try {
            InsertOneResult result = mongoDB.collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("Title", movieModel.getTitle())
                    .append("Description", movieModel.getDescription())
                    .append("ImageURL", movieModel.getImageURL())
                    .append("GenreList", movieModel.getGenreList().stream().map(GenreModel::getGenreId).toList())
                    .append("MovieTime", movieModel.getMovieTime())
                    .append("MovieCost", movieModel.getMovieCost())
                    .append("ReleaseDate", movieModel.getReleaseDate())
                    .append("IsActive", movieModel.getIsActive())
            );

            return result.getInsertedId() != null;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<MovieModel> movieModelList) {

        try {
            List<Document> dataList = new ArrayList<>();
            for (MovieModel movieModel : movieModelList) {
                dataList.add(new Document()
                        .append("Title", movieModel.getTitle())
                        .append("Description", movieModel.getDescription())
                        .append("ImageURL", movieModel.getImageURL())
                        .append("GenreList", movieModel.getGenreList().stream().map(GenreModel::getGenreId).toList())
                        .append("MovieTime", movieModel.getMovieTime())
                        .append("MovieCost", movieModel.getMovieCost())
                        .append("ReleaseDate", movieModel.getReleaseDate())
                        .append("IsActive", movieModel.getIsActive())
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
    public List<MovieModel> FindAll() {

        try {
            List<MovieModel> result = new ArrayList<>();
            FindIterable<Document> rawDataList = mongoDB.collection.find();
            for (Document rawData : rawDataList) {
                MovieModel tmp = new MovieModel();
                tmp = tmp.DTO(rawData);
                result.add(tmp);
            }

            return result;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public MovieModel FindOneById(String movieId) {
        MovieModel movieModel = new MovieModel();

        try {
            Bson query = eq("_id", new ObjectId(movieId));
            Document data = mongoDB.collection.find(query).first();

            if (data == null) return null;
            movieModel = movieModel.DTO(data);

            return movieModel;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String movieId) {
        try {
            Bson query = eq("_id", new ObjectId(movieId));
            DeleteResult result = mongoDB.collection.deleteOne(query);

            return result.getDeletedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean DeleteManyById(List<String> movieIdList) {
        try {
            for (String movieId : movieIdList) {
                Bson query = eq("_id", new ObjectId(movieId));
                mongoDB.collection.deleteOne(query);
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateOne(MovieModel movieModel) {
        try {
            Bson filters = eq("_id", new ObjectId(movieModel.getMovieId()));
            Bson query = Updates.combine(
                    Updates.set("Title", movieModel.getTitle()),
                    Updates.set("Description", movieModel.getDescription()),
                    Updates.set("ImageURL", movieModel.getImageURL()),
                    Updates.set("GenreList", movieModel.getGenreList().stream().map(GenreModel::getGenreId).toList()),
                    Updates.set("MovieTime", movieModel.getMovieTime()),
                    Updates.set("MovieCost", movieModel.getMovieCost()),
                    Updates.set("ReleaseDate", movieModel.getReleaseDate()),
                    Updates.set("IsActive", movieModel.getIsActive())
            );

            UpdateResult result = mongoDB.collection.updateOne(filters, query);

            return result.getModifiedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<MovieModel> movieModelList) {
        try {
            for (MovieModel movieModel : movieModelList) {
                Bson filters = eq("_id", new ObjectId(movieModel.getMovieId()));
                Bson query = Updates.combine(
                        Updates.set("Title", movieModel.getTitle()),
                        Updates.set("Description", movieModel.getDescription()),
                        Updates.set("ImageURL", movieModel.getImageURL()),
                        Updates.set("GenreList", movieModel.getGenreList().stream().map(GenreModel::getGenreId).toList()),
                        Updates.set("MovieTime", movieModel.getMovieTime()),
                        Updates.set("MovieCost", movieModel.getMovieCost()),
                        Updates.set("ReleaseDate", movieModel.getReleaseDate()),
                        Updates.set("IsActive", movieModel.getIsActive())
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

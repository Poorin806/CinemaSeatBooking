package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.GenreModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class GenreService implements MongoDBQuery<GenreModel> {

    private final MongoDBConnection mongoDB;

    public GenreService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("Genre");
    }

    @Override
    public Boolean InsertOne(GenreModel genreModel) {
        try {

            InsertOneResult result = mongoDB.collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("Name", genreModel.getName())
            );

            return result.getInsertedId() != null;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<GenreModel> genreModelList) {
        try {
            List<Document> dataList = new ArrayList<>();
            for (GenreModel genreModel : genreModelList) {
                dataList.add(new Document("Name", genreModel.getName()));
            }
            InsertManyResult result = mongoDB.collection.insertMany(dataList);

            return !result.getInsertedIds().isEmpty();
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<GenreModel> FindAll() {
        try {
            List<GenreModel> result = new ArrayList<>();
            FindIterable<Document> rawDataList = mongoDB.collection.find();
            for (Document row : rawDataList) {
                GenreModel tmp = new GenreModel();
                tmp = tmp.DTO(row);
                result.add(tmp);
            }

            return result;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public GenreModel FindOneById(String genreId) {
        GenreModel genreModel = new GenreModel();

        try {

            Bson query = eq("_id", new ObjectId(genreId));
            Document data = mongoDB.collection.find(query).first();

            if (data == null) return null;
            genreModel = genreModel.DTO(data);

            return genreModel;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String genreId) {
        try {
            Bson query = eq("_id", new ObjectId(genreId));
            DeleteResult result = mongoDB.collection.deleteOne(query);

            return result.getDeletedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean DeleteManyById(List<String> genreIdList) {
        try {
            for (String genreId : genreIdList) {
                Bson query = eq("_id", new ObjectId(genreId));
                mongoDB.collection.deleteOne(query);
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateOne(GenreModel genreModel) {
        try {
            Bson filters = eq("_id", new ObjectId(genreModel.getGenreId()));
            Bson query = Updates.combine(
                    Updates.set("Name", genreModel.getName())
            );

            UpdateResult result = mongoDB.collection.updateOne(filters, query);

            return result.getModifiedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<GenreModel> genreModelList) {
        try {
            for (GenreModel genreModel : genreModelList) {
                Bson filters = eq("_id", new ObjectId(genreModel.getGenreId()));
                Bson query = Updates.combine(
                        Updates.set("Name", genreModel.getName())
                );

                UpdateResult result = mongoDB.collection.updateOne(filters, query);
                if (result.getModifiedCount() <= 0) return  false;
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }
}

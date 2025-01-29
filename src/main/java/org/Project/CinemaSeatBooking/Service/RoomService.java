package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.RoomModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RoomService implements MongoDBQuery<RoomModel> {

    private final MongoDBConnection mongoDB;

    public RoomService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("Room");
    }

    @Override
    public Boolean InsertOne(RoomModel roomModel) {

        try {

            InsertOneResult result = mongoDB.collection.insertOne(
                    new Document()
                            .append("_id", new ObjectId())
                            .append("Name", roomModel.getName())
            );

            return result.getInsertedId() != null;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<RoomModel> roomModelList) {

        try {

            List<Document> dataList = new ArrayList<>();
            for (RoomModel roomModel : roomModelList) {
                dataList.add(
                        new Document()
                                .append("_id", new ObjectId())
                                .append("Name", roomModel.getName())
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
    public List<RoomModel> FindAll() {

        try {

            List<RoomModel> result = new ArrayList<>();
            FindIterable<Document> rawDataList = mongoDB.collection.find();
            for (Document rawData : rawDataList) {
                RoomModel roomModel = new RoomModel();
                roomModel = roomModel.DTO(rawData);
                result.add(roomModel);
            }

            return result;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public RoomModel FindOneById(String roomId) {

        try {

            Bson query = eq("_id", new ObjectId(roomId));
            Document data = mongoDB.collection.find(query).first();

            if (data == null) return null;
            RoomModel roomModel = new RoomModel();
            roomModel = roomModel.DTO(data);

            return roomModel;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String roomId) {
        try {
            Bson query = eq("_id", new ObjectId(roomId));
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
    public Boolean UpdateOne(RoomModel roomModel) {

        try {

            Bson filter = eq("_id", new ObjectId(roomModel.getRoomId()));
            Bson query = Updates.combine(
                    Updates.set("Name", roomModel.getName())
            );

            UpdateResult result = mongoDB.collection.updateOne(filter, query);

            return result.getModifiedCount() > 0L;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<RoomModel> roomModelList) {

        try {

            for (RoomModel roomModel : roomModelList) {
                Bson filter = eq("_id", new ObjectId(roomModel.getRoomId()));
                Bson query = Updates.combine(
                        Updates.set("Name", roomModel.getName())
                );

                UpdateResult result = mongoDB.collection.updateOne(filter, query);

                if (result.getModifiedCount() <= 0L) return false;
            }

            return true;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }
}

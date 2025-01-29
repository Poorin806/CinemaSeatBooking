package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.SeatModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class SeatService implements MongoDBQuery<SeatModel> {

    private final MongoDBConnection mongoDB;

    public SeatService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("Seat");
    }

    @Override
    public Boolean InsertOne(SeatModel seatModel) {

        try {

            InsertOneResult result = mongoDB.collection.insertOne(
                    new Document()
                            .append("_id", new ObjectId())
                            .append("RoomId", seatModel.getRoomModel().getRoomId())
                            .append("Row", seatModel.getRow())
                            .append("Number", seatModel.getNumber())
                            .append("SeatTypeId", seatModel.getSeatTypeModel().getSeatTypeId())
            );

            return result.getInsertedId() != null;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<SeatModel> seatModelList) {

        try {

            List<Document> dataList = new ArrayList<>();
            for (SeatModel seatModel : seatModelList) {

                dataList.add(
                        new Document()
                                .append("_id", new ObjectId())
                                .append("RoomId", seatModel.getRoomModel().getRoomId())
                                .append("Row", seatModel.getRow())
                                .append("Number", seatModel.getNumber())
                                .append("SeatTypeId", seatModel.getSeatTypeModel().getSeatTypeId())
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
    public List<SeatModel> FindAll() {

        try {

            List<SeatModel> result = new ArrayList<>();

            FindIterable<Document> rawDataList = mongoDB.collection.find();
            for (Document rawData : rawDataList) {
                SeatModel tmp = new SeatModel();
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
    public SeatModel FindOneById(String seatId) {

        try {

            SeatModel seatModel = new SeatModel();
            Bson query = eq("_id", new ObjectId(seatId));
            Document data = mongoDB.collection.find(query).first();

            if (data == null) return null;
            seatModel = seatModel.DTO(data);

            return seatModel;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String seatId) {
        try {
            Bson query = eq("_id", new ObjectId(seatId));
            DeleteResult result = mongoDB.collection.deleteOne(query);

            return result.getDeletedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean DeleteManyById(List<String> seatIdList) {
        try {
            for (String seatId : seatIdList) {
                Bson query = eq("_id", new ObjectId(seatId));
                mongoDB.collection.deleteOne(query);
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateOne(SeatModel seatModel) {

        try {

            Bson filters = eq("_id", new ObjectId(seatModel.getSeatId()));
            Bson query = Updates.combine(
                    Updates.set("RoomId", seatModel.getRoomModel().getRoomId()),
                    Updates.set("Row", seatModel.getRow()),
                    Updates.set("Number", seatModel.getNumber()),
                    Updates.set("SeatTypeId", seatModel.getSeatTypeModel().getSeatTypeId())
            );

            UpdateResult result = mongoDB.collection.updateOne(filters, query);

            return result.getModifiedCount() > 0L;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<SeatModel> seatModelList) {

        try {

            for (SeatModel seatModel : seatModelList) {
                Bson filters = eq("_id", new ObjectId(seatModel.getSeatId()));
                Bson query = Updates.combine(
                        Updates.set("RoomId", seatModel.getRoomModel().getRoomId()),
                        Updates.set("Row", seatModel.getRow()),
                        Updates.set("Number", seatModel.getNumber()),
                        Updates.set("SeatTypeId", seatModel.getSeatTypeModel().getSeatTypeId())
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

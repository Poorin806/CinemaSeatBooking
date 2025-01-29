package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.SeatTypeModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class SeatTypeService implements  MongoDBQuery<SeatTypeModel> {

    private final MongoDBConnection mongoDB;

    public SeatTypeService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("SeatType");
    }

    @Override
    public Boolean InsertOne(SeatTypeModel seatTypeModel) {

        try {

            InsertOneResult result = mongoDB.collection.insertOne(
                    new Document()
                            .append("_id", new ObjectId())
                            .append("Name", seatTypeModel.getName())
                            .append("Price", seatTypeModel.getPrice())
            );

            return result.getInsertedId() != null;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<SeatTypeModel> seatTypeModelList) {

        try {

            List<Document> dataList = new ArrayList<>();
            for (SeatTypeModel seatTypeModel : seatTypeModelList) {

                dataList.add(
                        new Document()
                                .append("_id", new ObjectId())
                                .append("Name", seatTypeModel.getName())
                                .append("Price", seatTypeModel.getPrice())
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
    public List<SeatTypeModel> FindAll() {

        try {
            FindIterable<Document> rawDataList = mongoDB.collection.find();
            List<SeatTypeModel> result = new ArrayList<>();
            for (Document rawData : rawDataList) {
                SeatTypeModel seatTypeModel = new SeatTypeModel();
                seatTypeModel = seatTypeModel.DTO(rawData);
                result.add(seatTypeModel);
            }

            return result;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SeatTypeModel FindOneById(String seatTypeId) {

        try {

            Bson query = eq("_id", new ObjectId(seatTypeId));
            Document data = mongoDB.collection.find(query).first();

            if (data == null) return null;
            SeatTypeModel seatTypeModel = new SeatTypeModel();
            seatTypeModel = seatTypeModel.DTO(data);

            return seatTypeModel;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String seatTypeId) {
        try {
            Bson query = eq("_id", new ObjectId(seatTypeId));
            DeleteResult result = mongoDB.collection.deleteOne(query);

            return result.getDeletedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean DeleteManyById(List<String> seatTypeIdList) {
        try {
            for (String seatTypeId : seatTypeIdList) {
                Bson query = eq("_id", new ObjectId(seatTypeId));
                mongoDB.collection.deleteOne(query);
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateOne(SeatTypeModel seatTypeModel) {

        try {

            Bson filters = eq("_id", new ObjectId(seatTypeModel.getSeatTypeId()));
            Bson query = Updates.combine(
                    Updates.set("Name", seatTypeModel.getName()),
                    Updates.set("Price", seatTypeModel.getPrice())
            );

            UpdateResult result = mongoDB.collection.updateOne(filters, query);

            return result.getModifiedCount() > 0L;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<SeatTypeModel> seatTypeModelList) {
        try {

            for (SeatTypeModel seatTypeModel : seatTypeModelList) {
                Bson filters = eq("_id", new ObjectId(seatTypeModel.getSeatTypeId()));
                Bson query = Updates.combine(
                        Updates.set("Name", seatTypeModel.getName()),
                        Updates.set("Price", seatTypeModel.getPrice())
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

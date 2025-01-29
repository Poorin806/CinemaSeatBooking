package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.LogStatusModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class LogStatusService implements MongoDBQuery<LogStatusModel> {

    private final MongoDBConnection mongoDB;

    public LogStatusService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("LogStatus");
    }

    @Override
    public Boolean InsertOne(LogStatusModel logStatusModel) {

        try {

            InsertOneResult result = mongoDB.collection.insertOne(
                    new Document()
                            .append("_id", new ObjectId())
                            .append("Name", logStatusModel.getName())
            );

            return result.getInsertedId() != null;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<LogStatusModel> logStatusModelList) {

        try {

            List<Document> dataList = new ArrayList<>();
            for (LogStatusModel logStatusModel : logStatusModelList) {

                dataList.add(
                        new Document()
                                .append("_id", new ObjectId())
                                .append("Name", logStatusModel.getName())
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
    public List<LogStatusModel> FindAll() {

        try {

            List<LogStatusModel> result = new ArrayList<>();
            FindIterable<Document> rawDataList = mongoDB.collection.find();

            for (Document rawData : rawDataList) {

                LogStatusModel logStatusModel = new LogStatusModel();
                logStatusModel = logStatusModel.DTO(rawData);

                result.add(logStatusModel);

            }

            return result;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public LogStatusModel FindOneById(String logStatusId) {

        try {

            Bson query = eq("_id", new ObjectId(logStatusId));
            Document data = mongoDB.collection.find(query).first();

            if (data == null) return null;

            LogStatusModel logStatusModel = new LogStatusModel();
            logStatusModel = logStatusModel.DTO(data);

            return logStatusModel;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String logStatusId) {
        try {
            Bson query = eq("_id", new ObjectId(logStatusId));
            DeleteResult result = mongoDB.collection.deleteOne(query);

            return result.getDeletedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean DeleteManyById(List<String> logStatusIdList) {
        try {
            for (String logStatusId : logStatusIdList) {
                Bson query = eq("_id", new ObjectId(logStatusId));
                mongoDB.collection.deleteOne(query);
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateOne(LogStatusModel logStatusModel) {

        try {

            Bson filters = eq("_id", new ObjectId(logStatusModel.getLogStatusId()));
            Bson query = Updates.combine(
                    Updates.set("Name", logStatusModel.getName())
            );

            UpdateResult result = mongoDB.collection.updateOne(filters, query);

            return result.getModifiedCount() > 0L;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<LogStatusModel> logStatusModelList) {

        try {

            for (LogStatusModel logStatusModel : logStatusModelList) {

                Bson filters = eq("_id", new ObjectId(logStatusModel.getLogStatusId()));
                Bson query = Updates.combine(
                        Updates.set("Name", logStatusModel.getName())
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

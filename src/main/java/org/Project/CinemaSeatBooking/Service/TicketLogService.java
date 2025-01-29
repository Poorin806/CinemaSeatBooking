package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.TicketLogModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TicketLogService implements MongoDBQuery<TicketLogModel> {

    private final MongoDBConnection mongoDB;

    public TicketLogService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("TicketLog");
    }

    @Override
    public Boolean InsertOne(TicketLogModel ticketLogModel) {

        try {

            InsertOneResult result = mongoDB.collection.insertOne(

                    new Document()
                            .append("_id", new ObjectId())
                            .append("TicketId", ticketLogModel.getTicketModel().getTicketId())
                            .append("Timestamp", ticketLogModel.getTimestamp())
                            .append("LogStatusId", ticketLogModel.getLogStatusModel().getLogStatusId())
                            .append("CurrentSeat", ticketLogModel.getCurrentSeat().getSeatId())
                            .append("NewSeat",
                                    ticketLogModel.getNewSeat().getSeatId() == null ? null : ticketLogModel.getNewSeat().getSeatId()
                                )
                            .append("Note", ticketLogModel.getNote())

            );

            return result.getInsertedId() != null;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<TicketLogModel> ticketLogModelList) {

        try {

            List<Document> dataList = new ArrayList<>();
            for (TicketLogModel ticketLogModel : ticketLogModelList) {

                dataList.add(
                        new Document()
                                .append("_id", new ObjectId())
                                .append("TicketId", ticketLogModel.getTicketModel().getTicketId())
                                .append("Timestamp", ticketLogModel.getTimestamp())
                                .append("LogStatusId", ticketLogModel.getLogStatusModel().getLogStatusId())
                                .append("CurrentSeat", ticketLogModel.getCurrentSeat().getSeatId())
                                .append("NewSeat",
                                        ticketLogModel.getNewSeat().getSeatId() == null ? null : ticketLogModel.getNewSeat().getSeatId()
                                )
                                .append("Note", ticketLogModel.getNote())
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
    public List<TicketLogModel> FindAll() {

        try {

            List<TicketLogModel> result = new ArrayList<>();
            FindIterable<Document> rawDataList = mongoDB.collection.find();

            for (Document rawData : rawDataList) {

                TicketLogModel ticketLogModel = new TicketLogModel();
                ticketLogModel = ticketLogModel.DTO(rawData);

                result.add(ticketLogModel);

            }

            return result;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public TicketLogModel FindOneById(String _id) {

        try {

            Bson query = eq("_id", new ObjectId(_id));
            Document data = mongoDB.collection.find(query).first();

            if (data == null) return null;

            TicketLogModel ticketLogModel = new TicketLogModel();
            ticketLogModel = ticketLogModel.DTO(data);

            return ticketLogModel;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String _id) {
        try {
            Bson query = eq("_id", new ObjectId(_id));
            DeleteResult result = mongoDB.collection.deleteOne(query);

            return result.getDeletedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean DeleteManyById(List<String> _idList) {
        try {
            for (String _id : _idList) {
                Bson query = eq("_id", new ObjectId(_id));
                mongoDB.collection.deleteOne(query);
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateOne(TicketLogModel ticketLogModel) {

        try {

            Bson filters = eq("_id", new ObjectId(ticketLogModel.get_id()));
            Bson query = Updates.combine(
                    Updates.set("TicketId", ticketLogModel.getTicketModel().getTicketId()),
                    Updates.set("Timestamp", ticketLogModel.getTimestamp()),
                    Updates.set("LogStatusId", ticketLogModel.getLogStatusModel().getLogStatusId()),
                    Updates.set("CurrentSeat", ticketLogModel.getCurrentSeat().getSeatId()),
                    Updates.set("NewSeat", ticketLogModel.getNewSeat() == null ? null : ticketLogModel.getNewSeat().getSeatId()),
                    Updates.set("Note", ticketLogModel.getNote())
            );

            UpdateResult result = mongoDB.collection.updateOne(filters, query);

            return result.getModifiedCount() > 0L;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<TicketLogModel> ticketLogModelList) {

        try {

            for (TicketLogModel ticketLogModel : ticketLogModelList) {

                Bson filters = eq("_id", new ObjectId(ticketLogModel.get_id()));
                Bson query = Updates.combine(
                        Updates.set("TicketId", ticketLogModel.getTicketModel().getTicketId()),
                        Updates.set("TimeStamp", ticketLogModel.getTimestamp()),
                        Updates.set("LogStatusId", ticketLogModel.getLogStatusModel().getLogStatusId()),
                        Updates.set("CurrentSeat", ticketLogModel.getCurrentSeat().getSeatId()),
                        Updates.set("NewSeat", ticketLogModel.getNewSeat().getSeatId() == null ? null : ticketLogModel.getNewSeat().getSeatId()),
                        Updates.set("Note", ticketLogModel.getNote())
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

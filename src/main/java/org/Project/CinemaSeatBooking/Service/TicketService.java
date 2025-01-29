package org.Project.CinemaSeatBooking.Service;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.Project.CinemaSeatBooking.Model.TicketModel;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TicketService implements MongoDBQuery<TicketModel> {

    private final MongoDBConnection mongoDB;

    public TicketService() {
        mongoDB = new MongoDBConnection();
        mongoDB.SetTable("Ticket");
    }

    @Override
    public Boolean InsertOne(TicketModel ticketModel) {

        try {

            InsertOneResult result = mongoDB.collection.insertOne(
                    new Document()
                            .append("_id", new ObjectId())
                            .append("ScheduleId", ticketModel.getMovieScheduleModel().getScheduleId())
                            .append("TotalPrice", ticketModel.getTotalPrice())
                            .append("SeatId", ticketModel.getSeatModel().getSeatId())
                            .append("Customer", ticketModel.getCustomer())
                            .append("IsActive", ticketModel.getIsActive())
            );

            return result.getInsertedId() != null;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean InsertMany(List<TicketModel> ticketModelList) {

        try {

            List<Document> dataList = new ArrayList<>();
            for (TicketModel ticketModel : ticketModelList) {

                dataList.add(
                        new Document()
                                .append("_id", new ObjectId())
                                .append("ScheduleId", ticketModel.getMovieScheduleModel().getScheduleId())
                                .append("TotalPrice", ticketModel.getTotalPrice())
                                .append("SeatId", ticketModel.getSeatModel().getSeatId())
                                .append("Customer", ticketModel.getCustomer())
                                .append("IsActive", ticketModel.getIsActive())
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
    public List<TicketModel> FindAll() {

        try {

            List<TicketModel> result = new ArrayList<>();
            FindIterable<Document> rawDataList = mongoDB.collection.find();
            for (Document rawData : rawDataList) {

                TicketModel tmp = new TicketModel();
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
    public TicketModel FindOneById(String ticketId) {

        try {

            Bson query = eq("_id", new ObjectId(ticketId));
            Document data = mongoDB.collection.find(query).first();

            if (data == null) return null;

            TicketModel ticketModel = new TicketModel();
            ticketModel = ticketModel.DTO(data);
            return ticketModel;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean DeleteOneById(String ticketId) {
        try {
            Bson query = eq("_id", new ObjectId(ticketId));
            DeleteResult result = mongoDB.collection.deleteOne(query);

            return result.getDeletedCount() > 0L;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean DeleteManyById(List<String> ticketIdList) {
        try {
            for (String ticketId : ticketIdList) {
                Bson query = eq("_id", new ObjectId(ticketId));
                mongoDB.collection.deleteOne(query);
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateOne(TicketModel ticketModel) {

        try {

            Bson filters = eq("_id", new ObjectId(ticketModel.getTicketId()));
            Bson query = Updates.combine(
                    Updates.set("ScheduleId", ticketModel.getMovieScheduleModel().getScheduleId()),
                    Updates.set("TotalPrice", ticketModel.getTotalPrice()),
                    Updates.set("SeatId", ticketModel.getSeatModel().getSeatId()),
                    Updates.set("Customer", ticketModel.getCustomer()),
                    Updates.set("IsActive", ticketModel.getIsActive())
            );

            UpdateResult result = mongoDB.collection.updateOne(filters, query);

            return result.getModifiedCount() > 0L;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Boolean UpdateMany(List<TicketModel> ticketModelList) {

        try {

            for (TicketModel ticketModel : ticketModelList) {

                Bson filters = eq("_id", new ObjectId(ticketModel.getTicketId()));
                Bson query = Updates.combine(
                        Updates.set("ScheduleId", ticketModel.getMovieScheduleModel().getScheduleId()),
                        Updates.set("TotalPrice", ticketModel.getTotalPrice()),
                        Updates.set("SeatId", ticketModel.getSeatModel().getSeatId()),
                        Updates.set("Customer", ticketModel.getCustomer()),
                        Updates.set("IsActive", ticketModel.getIsActive())
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

package org.Project.CinemaSeatBooking.Service;

import java.util.List;

public interface MongoDBQuery<T> {

    Boolean InsertOne(T model);

    Boolean InsertMany(List<T> modelList);

    List<T> FindAll();

    T FindOneById(String id);

    Boolean DeleteOneById(String id);

    Boolean DeleteManyById(List<String> idList);

    Boolean UpdateOne(T model);

    Boolean UpdateMany(List<T> modelList);

}

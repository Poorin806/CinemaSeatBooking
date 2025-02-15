package org.Project.CinemaSeatBooking.Service;

import java.sql.SQLException;
import java.util.List;

public interface MySQLQueryInterface<T> {

    List<T> getAll() throws SQLException;

    List<T> getMany(String sql) throws SQLException;

    T getOne(String sql) throws SQLException;

}

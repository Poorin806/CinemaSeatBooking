package org.Project.CinemaSeatBooking;

import org.Project.CinemaSeatBooking.GUI.HomeGUI;
import org.Project.CinemaSeatBooking.Utils.MySQLConnection;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        MySQLConnection.getConnection();

        HomeGUI.show();

    }

}

package org.Project.CinemaSeatBooking;

import org.Project.CinemaSeatBooking.GUI.DashboardGUI;
import org.Project.CinemaSeatBooking.GUI.HomeGUI;
import org.Project.CinemaSeatBooking.Utils.MongoDBConnection;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // MongoDB connection
//        MongoDBConnection.Connect();

        // MongoDB close connection when close GUI
        HomeGUI.show();

    }

}

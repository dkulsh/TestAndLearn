package OfficeUtil;

import org.apache.commons.collections.map.HashedMap;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class VenueOperationalHours {

    Connection tableMgmtConnection;
    Connection fnbConnection;

    public static void main(String[] args) throws SQLException {

        VenueOperationalHours vOH = new VenueOperationalHours();
        vOH.connectDB();

        vOH.readTablMgmtData();
    }

    private void readTablMgmtData() throws SQLException {

        ResultSet venuesSet = tableMgmtConnection.createStatement().executeQuery("SELECT * FROM venues");
        Map<String, String> venueToDxpIdMap = new HashMap<>();

        while (venuesSet.next()){
            String publicId = venuesSet.getString("public_id");
            String dxpId = venuesSet.getString("dxp_id");
            String name = venuesSet.getString("name");

            venueToDxpIdMap.put(publicId, dxpId);
            System.out.println("public_id = " + publicId + ", dxpId = " + dxpId + ", name = " + name);
        }

        ResultSet venueOpsSet = tableMgmtConnection.createStatement().executeQuery("SELECT * FROM venues_venueoperationhour");

        while (venueOpsSet.next()){

            String status = venueOpsSet.getString("status");
            String period = venueOpsSet.getString("period");
            String venueId = venueOpsSet.getString("venue_id");

            System.out.println("status = " + status + ", period = " + period + ", venueId = " + venueId);

            String vxpVenueId = venueToDxpIdMap.get(venueId);
            String startDate = period.substring(2, 12);
            String endDate = period.substring(30, 40);
            String startTime = period.substring(13, 21);
            String endTime = period.substring(41, 49);
            String addedBy = "adhocLoad";
            String type = status;

            insertIntoFnBDb(vxpVenueId, startDate, endDate, startTime, endTime, addedBy, type);
        }

        fnbConnection.close();

    }

    private void insertIntoFnBDb(String vxpVenueId, String startDate, String endDate, String startTime, String endTime,
                                 String addedBy, String type) throws SQLException {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        PreparedStatement preparedStatement = fnbConnection.prepareStatement("INSERT INTO venueeffectivewindow " +
                "(venueEffectiveWindowId, venueId, startDate, endDate, startTime, endTime, addedBy, type)  " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setObject(1, UUID.randomUUID());
        preparedStatement.setObject(2, UUID.fromString(vxpVenueId));
        preparedStatement.setDate(3, Date.valueOf(LocalDate.parse(startDate)));
        preparedStatement.setDate(4, Date.valueOf(LocalDate.parse(endDate)));
        preparedStatement.setTime(5, Time.valueOf(LocalTime.parse(startTime)));
        preparedStatement.setTime(6, Time.valueOf(LocalTime.parse(endTime)));
        preparedStatement.setString(7, addedBy);
        preparedStatement.setString(8, type);

        preparedStatement.executeUpdate();
    }

    private void connectDB() throws SQLException {

        Properties props = new Properties();
        props.setProperty("user","pgappuser");
//        props.setProperty("password","rnKZxhw!eYg7bCWH9v8V"); // Shore
        props.setProperty("password","pgappuser*101"); // Ship

//        String fnbUrl = "jdbc:postgresql://10.117.137.30:5432/foodandbeverage"; // Shore
        String fnbUrl = "jdbc:postgresql://10.61.69.24:4001/foodandbeverage"; // Ship

        fnbConnection = DriverManager.getConnection(fnbUrl, props);

//        String tableMgmtUrl = "jdbc:postgresql://10.117.137.30:5432/table_management"; // Shore
        String tableMgmtUrl = "jdbc:postgresql://10.61.69.24:4001/table_management";

        tableMgmtConnection = DriverManager.getConnection(tableMgmtUrl, props);
    }
}

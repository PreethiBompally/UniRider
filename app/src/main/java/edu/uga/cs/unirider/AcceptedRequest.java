package edu.uga.cs.unirider;

public class AcceptedRequest {

    private String key;
    private String driverName;
    private String riderName;
    private String date;
    private String time;
    private String pickup;
    private String dropoff;
    private int userPoints;
    private boolean riderConfirmed;

    // Default constructor
    public AcceptedRequest() {
        this.driverName = null;
        this.riderName = null;
        this.date = null;
        this.time = null;
        this.pickup = null;
        this.dropoff = null;
        this.userPoints = 0;
        this.riderConfirmed = false;
    }

    // Parameterized constructor
    public AcceptedRequest(String driverName, String riderName, String date, String time,
                           String pickup, String dropoff, int userPoints, boolean riderConfirmed) {
        this.driverName = driverName;
        this.riderName = riderName;
        this.date = date;
        this.time = time;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.userPoints = userPoints;
        this.riderConfirmed = riderConfirmed;
    }

    public boolean isRiderConfirmed() {
        return riderConfirmed;
    }

    public void setRiderConfirmed(boolean riderConfirmed){
        this.riderConfirmed = riderConfirmed;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    // Getter and Setter for driverName
    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    // Getter and Setter for riderName
    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    // Getter and Setter for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter and Setter for time
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Getter and Setter for pickup
    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    // Getter and Setter for dropoff
    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    // Getter and Setter for userPoints
    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }
}


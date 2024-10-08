package edu.uga.cs.unirider;

public class RideRequest {
    private String key;
    private String riderName;
    private String time;
    private String date;
    private String pickup;
    private String dropoff;
    private boolean riderAccepted;
    private boolean driverAccepted;

    public RideRequest() {
        this.riderName = null;
        this.time = null;
        this.date = null;
        this.pickup = null;
        this.dropoff = null;
        this.riderAccepted = false;
        this.driverAccepted = false;
    }

    public RideRequest(String riderName, String time, String date, String pickup, String dropoff) {
        this.riderName = riderName;
        this.time = time;
        this.date = date;
        this.pickup = pickup;
        this.dropoff = dropoff;
    }

    public String getKey() {
        return key;
    }

    public String getRiderName() {
        return riderName;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public boolean getRiderAccepted() {
        return this.riderAccepted;
    }
    public boolean getDriverAccepted() {
        return this.driverAccepted;
    }

    // setter methods
    public void setKey(String key) {
        this.key = key;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public void setRiderAccepted(boolean riderAccepted){
        this.riderAccepted = riderAccepted;
    }
    public void setDriverAccepted(boolean driverAccepted){
        this.driverAccepted = driverAccepted;
    }
}


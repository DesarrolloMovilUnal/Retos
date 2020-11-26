package co.edu.unal.jsonrequest;

public class Data {
    private String year;
    private String month;
    private String location;
    private String place;
    private String connectionsAverage;
    private String consumptionBandwidth;

    public Data(String year, String month, String location, String place, String connectionsAverage, String consumptionBandwidth) {
        this.year = year;
        this.month = month;
        this.location = location;
        this.place = place;
        this.connectionsAverage = connectionsAverage;
        this.consumptionBandwidth = consumptionBandwidth;
    }

    public Data(String place) {
        this.place = place;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getConnectionsAverage() {
        return connectionsAverage;
    }

    public void setConnectionsAverage(String connectionsAverage) {
        this.connectionsAverage = connectionsAverage;
    }

    public String getConsumptionBandwidth() {
        return consumptionBandwidth;
    }

    public void setConsumptionBandwidth(String consumptionBandwidth) {
        this.consumptionBandwidth = consumptionBandwidth;
    }
}

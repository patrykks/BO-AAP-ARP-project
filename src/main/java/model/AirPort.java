package model;

/**
 * Created by patrykks on 27/03/16.
 */
public class AirPort {
    private int id;
    private String IATA;
    private String ICAO;
    private double latitude;
    private double longitude;

    public AirPort(int id, String IATA, String ICAO, double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.IATA = IATA;
        this.ICAO = ICAO;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double distance(AirPort other) {
        double lat1 = latitude;
        double lon1 = longitude;
        double lon2 = other.getLongitude();
        double lat2 = other.getLatitude();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    public String getName() {
        return "IATA: " + IATA + " ICAO:" + ICAO;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirPort airPort = (AirPort) o;
        return id == airPort.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "model.AirPort{" +
                "id=" + id +
                ", IATA='" + IATA + '\'' +
                ", ICAO='" + ICAO + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

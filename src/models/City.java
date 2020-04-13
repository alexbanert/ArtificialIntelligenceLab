package models;

public class City {
    private int id;

    public int getId() {
        return id;
    }

    private float x;
    private float y;

    public City(int id, float x, float y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public static double distance(City first, City second, String type){
        if(type.equals("EUC_2D"))
            return euclidianDistance(first, second);
        else if(type.equals("GEO"))
            return geographicalDistance(first,second);
        else
            return -1;
    }

    public static double euclidianDistance(City first, City second){
        double xDistance = first.x - second.x;
        double yDistance = first.y - second.y;
        double resultDistance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
        return Math.round(resultDistance);
    }

    @Override
    public String toString() {
        return "models.City{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public static int geographicalDistance(City first, City second){
        double pi = Math.PI;
        double latitudeI = 2 * pi * first.x / 180;
        double longitudeI = 2 * pi * first.y / 180;
        double latitudeJ = 2 * pi * second.x / 180;
        double longitudeJ = 2 * pi * second.y / 180;

        double RRR = 6378.388;
        double q1 = Math.cos(longitudeI - longitudeJ);
        double q2 = Math.cos(latitudeI - latitudeJ);
        double q3 = Math.cos(latitudeI + latitudeJ);
        return (int) (RRR * Math.acos(0.5 * ((1.0 + q1)*q2 - (1.0-q1)*q3)) + 1.0);
    }


    private static double coordinates2Radians(double coordinate) {
        return Math.PI * coordinate / 180.0;
    }


    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return Math.sqrt(distance);
    }


}

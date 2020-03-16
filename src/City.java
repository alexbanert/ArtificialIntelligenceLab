public class City {
    private int id;
    private float x;
    private float y;

    public City(int id, float x, float y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public static float euclidianDistance(City first, City second){
        float xDistance = first.x - second.x;
        float yDistance = first.y - second.y;
        float resultDistance = (float) Math.sqrt(xDistance * xDistance + yDistance * yDistance);
        return resultDistance;
    }

}

import java.util.ArrayList;
import java.util.Arrays;

public class Individual {
    private static ArrayList<City> cities = (ArrayList<City>) TSPLoader.loadCities("berlin11_modified.tsp");
    private int[] genotype;

    public Individual(int[] genotype){
        this.genotype = genotype;
    }

    public int[] getGenotype(){
        return this.genotype;
    }

    public boolean setGenotype(int[] newGenotype){
        if(newGenotype.length == this.genotype.length) {
            this.genotype = newGenotype;
            return true;
        }
        else
            return false;
    }

    public float totalDistance(){
        float result = 0;
        for(int i=0; i<cities.size() - 1; i++){
            // sum distance for each next pair of cities
            result += City.euclidianDistance(cities.get(i), cities.get(i+1));
        }
        return Math.round(result);
    }

    public static int numberOfCities(){
        return cities.size();
    }

    @Override
    public String toString() {
        return "Individual{distance=" +
                this.totalDistance() +
                ", genotype=" + Arrays.toString(genotype) +
                '}';
    }
}

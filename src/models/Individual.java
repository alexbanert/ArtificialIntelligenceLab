package models;

import utils.TSPLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class Individual {
    private static TSPLoader tspLoader;
    private static ArrayList<City> cities;
    private static String weightType;
    private static DistanceMatrix distanceMatrix;

    private int[] genotype;

    public Individual(int[] genotype) throws Exception {
        if(tspLoader == null || cities == null || weightType == null){
            throw new Exception("models.Individual was not configured properly");
        }
        else {
            int[] newGenotype = new int[genotype.length];
            for(int i = 0; i < genotype.length; i++){
                newGenotype[i] = genotype[i];
            }
            this.genotype = newGenotype;
        }
    }

    public Individual(Individual copy){
        int[] copyGenotype = copy.getGenotype();
        int[] newGenotype = new int[copyGenotype.length];
        for(int i = 0; i < copyGenotype.length; i++){
            newGenotype[i] = copyGenotype[i];
        }
        this.genotype = newGenotype;
    }

    public static void configureIndividual(TSPLoader tspLoader){
        Individual.tspLoader = tspLoader;
        Individual.cities = (ArrayList<City>) Individual.tspLoader.getCities();
        Individual.weightType = Individual.tspLoader.getWeightType();
        Individual.distanceMatrix = new DistanceMatrix(cities, weightType);
    }

    public TSPLoader getLoader(){
        return tspLoader;
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

    public int totalDistance(){
        int result = 0;
        for (int i = 0; i < cities.size(); i++) {
            // sum distance for each next pair of cities
            int firstCity = cities.get(genotype[i % cities.size()] - 1 ).getId();
            int secondCity = cities.get(genotype[(i + 1) % cities.size()] - 1 ).getId();
            result += distanceMatrix.getDistance(firstCity, secondCity);
        }
        return result;
    }

    public static int numberOfCities() throws Exception {
        if(tspLoader == null || cities == null || weightType == null){
            throw new Exception("models.Individual was not configured properly");
        }
        return cities.size();
    }

    @Override
    public String toString() {
        return "models.Individual{distance=" +
                this.totalDistance() +
                ", genotype=" + Arrays.toString(genotype) +
                '}';
    }
}

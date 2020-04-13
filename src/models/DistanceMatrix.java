package models;

import models.City;

import java.util.ArrayList;

public class DistanceMatrix {
    private double[][] matrix;
    public DistanceMatrix(ArrayList<City> cities, String weightType){
        matrix = new double[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                if (i == j) {
                    matrix[j][i] = 0;
                    continue;
                }
                matrix[j][i] = City.distance(cities.get(j), cities.get(i), weightType);
            }
        }
    }

    public double getDistance(int id1, int id2) {
        //System.out.println("LINE:" + id1 + " " + id2);
        return matrix[id1-1][id2-1];
    }
}

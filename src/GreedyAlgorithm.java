import models.City;
import models.Result;
import utils.TSPLoader;

import java.util.ArrayList;
import java.util.List;

public class GreedyAlgorithm {
    private List<City> cities;
    private String weightType;

    public GreedyAlgorithm(TSPLoader tspLoader){
        this.cities = tspLoader.getCities();
        this.weightType = tspLoader.getWeightType();
    }

    public ArrayList<Result> run(){
        ArrayList<Result> resultArrayList = new ArrayList<>();
        for(int i = 1; i< cities.size() + 1; i++){
            int distance = (int) evaluate(i);
            resultArrayList.add(new Result(distance,distance,distance));
        }
        return resultArrayList;
    }

    public double evaluate(int startingPoint){
        ArrayList<Integer> result = new ArrayList();
        result.add(startingPoint);
        int currentCityId = startingPoint;
        City currentCity = cities.get(currentCityId - 1);
        int resultDistance = 0;

        while(result.size() < cities.size()){
            int closestCity = -1;
            double closestDistance = Double.POSITIVE_INFINITY;
            for(City c :cities){
                if(!result.contains(c.getId())){
                    double currentDistance = City.distance(currentCity, c, weightType);
                    if(currentDistance < closestDistance){
                        closestDistance = currentDistance;
                        closestCity = c.getId();
                    }
                }
            }
            result.add(closestCity);
            resultDistance += closestDistance;
            currentCity = cities.get(closestCity - 1);
        }
        resultDistance += City.distance(cities.get(result.get(0) - 1) ,
                                        cities.get(result.get(result.size() - 1) - 1),
                                        weightType);
        return resultDistance;
    }

}

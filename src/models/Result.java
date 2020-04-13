package models;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private int bestFitness;
    private double avgFitness;
    private int worstFitness;

    public Result(int bestFitness, double avgFitness, int worstFitness) {
        this.bestFitness = bestFitness;
        this.avgFitness = avgFitness;
        this.worstFitness = worstFitness;
    }

    public int getBestFitness() {
        return bestFitness;
    }

    public double getAvgFitness() {
        return avgFitness;
    }

    public int getWorstFitness() {
        return worstFitness;
    }

    public static void displayResults(List<Result> resultList){
        int numberOfTimesRun = resultList.size();
        int bestResult = Integer.MAX_VALUE;
        double avgSum=0;
        double worstSum=0;
        ArrayList<Double> avgArrayList = new ArrayList<>();
        for(Result result : resultList){
            if(result.getBestFitness() < bestResult)
                bestResult = result.getBestFitness();
            avgSum += result.getAvgFitness();
            worstSum += result.getWorstFitness();
            avgArrayList.add(result.getAvgFitness());
        }
        double avgResult = avgSum / numberOfTimesRun;
        double worstResult = worstSum / numberOfTimesRun;


        double[] avgArray = new double[numberOfTimesRun];
        for(int i = 0; i < numberOfTimesRun; i++){
            avgArray[i] = avgArrayList.get(i);
        }
        double standardDeviation = calculateSD(avgArray);

        String resultMessage = "The algorithm was run " + numberOfTimesRun + " times\n"
                + "Best result found: " + bestResult
                + "\nAverage result: " + Math.round(avgResult)
                + "\nAverage worst result: " + Math.round(worstResult)
                + "\nStandard deviation: " + Math.round(standardDeviation);
        System.out.println(resultMessage);
    }

    private static double calculateSD(double[] numArray)
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }
}


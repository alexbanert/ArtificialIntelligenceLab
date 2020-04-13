import models.Individual;
import models.Result;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomAlgorithm {
    public RandomAlgorithm(){

    }
    public ArrayList<Result> run(int N){
        ArrayList<Result> resultArrayList = new ArrayList<>();
        for(int i = 0; i < N; i++)
            resultArrayList.add(runOnce());
        return resultArrayList;
    }
    private Result runOnce(){

            try {
                Individual individual = randomIndividual();
                int fitness = individual.totalDistance();
                return new Result(fitness, fitness, fitness);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    private Individual randomIndividual() throws Exception {
        int genotypeSize = Individual.numberOfCities();
        int[] array = IntStream.rangeClosed(1, genotypeSize).toArray();
        shuffleArray(array);
        return new Individual(array);
    }

    private static void shuffleArray(int[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}

package ga;

import models.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Population {
    private ArrayList<Individual> individuals = new ArrayList<>();

    public int getPopulationSize() {
        return individuals.size();
    }

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    public void addIndividual(Individual individual){
        individuals.add(individual);
    }

    public Population(){
        individuals = new ArrayList<>();
    }


    /**
     * Create random population of given size
     * @param populationSize number of individuals in population
     */
    public Population(int populationSize) throws Exception {
        for(int i = 0 ; i<populationSize; i++){
            individuals.add(randomIndividual());
        }
    }

    /**
     * Create population from given individuals
     * @param population List of individuals for current population
     */
    public Population(List<Individual> population){
        this.individuals = (ArrayList<Individual>) population;
    }


    public Individual bestIndividual() throws Exception {
        int best = Integer.MAX_VALUE;
        int[] genotype = null;
        for(int i = 0; i<individuals.size();i++){
            int current = individuals.get(i).totalDistance();
            if(current < best){
                best = current;
                genotype = individuals.get(i).getGenotype();
            }
        }
        return new Individual(genotype);
    }

    public int bestFitness(){
        int best = Integer.MAX_VALUE;
        for(int i = 0; i<individuals.size();i++){
            int current = individuals.get(i).totalDistance();
            if(current < best){
                best = current;
            }
        }
        return best;
    }

    public int worstFitness(){
        int worst = 0;
        for(int i = 0; i<individuals.size();i++){
            int current = individuals.get(i).totalDistance();
            if(current > worst){
                worst = current;
            }
        }
        return worst;
    }

    public double avgFitness(){
        double totalFitness = 0;
        for(int i = 0; i<individuals.size();i++){
            totalFitness += individuals.get(i).totalDistance();
        }
        return totalFitness/individuals.size();
    }

    public int[] fitness(){
        int[] fitnessArray = new int[individuals.size()];
        for(int i = 0; i<individuals.size();i++){
            fitnessArray[i] = individuals.get(i).totalDistance();
        }
        return fitnessArray;
    }


    private Individual randomIndividual() throws Exception {
        int genotypeSize = Individual.numberOfCities();
        int[] array = IntStream.rangeClosed(1, genotypeSize).toArray();
        shuffleArray(array);
        return new Individual(array);
    }

    /**
     * Shuffle genotype array
     * @param ar genotype array
     */
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

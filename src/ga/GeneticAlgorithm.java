package ga;

import models.Individual;
import models.Result;
import utils.Logger;

import java.util.*;

public class GeneticAlgorithm {
    private int populationSize;
    private int generations;
    private float mutationProb;
    private float crossoverProb;
    private int tournamentSize;
    private SelectionType selectionType = SelectionType.TOURNAMENT;
    private MutationType mutationType = MutationType.INVERSION;
    private Logger logger = new Logger("data1");

    public GeneticAlgorithm(int populationSize, int generations, float mutationProb, float crossoverProb, int tournamentSize){
        if(populationSize <= 0 ||
                generations <= 0 ||
                mutationProb > 1 ||
                mutationProb < 0 ||
                crossoverProb > 1 ||
                crossoverProb < 0 ||
                tournamentSize < 0
        ) {
            throw new IllegalArgumentException();
        }
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationProb = mutationProb;
        this.crossoverProb = crossoverProb;
        this.tournamentSize = tournamentSize;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public float getMutationProb() {
        return mutationProb;
    }

    public void setMutationProb(float mutationProb) {
        this.mutationProb = mutationProb;
    }

    public float getCrossoverProb() {
        return crossoverProb;
    }

    public void setCrossoverProb(float crossoverProb) {
        this.crossoverProb = crossoverProb;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public void setLogger(String filename) {
        this.logger = new Logger(filename);
    }

    public Result runAlgorithm() throws Exception {

        // Initiate Random for further rng
        Random random = new Random();

        // Create new generation at random
        Population currentGeneration = new Population(populationSize);

        double bestFitness = Double.POSITIVE_INFINITY;
        Individual bestIndividual=null;

        // Start time measure
        long timeStart = System.currentTimeMillis();

        // Start iterating generations
        for(int i = 0; i<generations;i++){

            Population nextGeneration = new Population();
            while(nextGeneration.getPopulationSize() < populationSize){

                Individual parent1;
                Individual parent2;

                if(selectionType == SelectionType.TOURNAMENT){
                    parent1 = selectTournament(currentGeneration, tournamentSize);
                    parent2 = selectTournament(currentGeneration, tournamentSize);
                }
                else{
                    parent1 = selectRoulette(currentGeneration);
                    parent2 = selectRoulette(currentGeneration);
                }

                Individual offspring;

                // Crossover
                if(random.nextFloat() < crossoverProb){
                    offspring = oxCrossover(parent1, parent2);
                }
                else{
                    offspring = parent1;
                }

                // Mutation
                if(random.nextFloat() < mutationProb){
                    if(mutationType == MutationType.INVERSION)
                        offspring = inversion(offspring);
                    else
                        offspring = swap(offspring);
                }
                nextGeneration.addIndividual(offspring);
            }
            currentGeneration = nextGeneration;
            int bestCurrent = currentGeneration.bestFitness();
            if(bestCurrent < bestFitness) {
                // Save best individual for result
                bestFitness = bestCurrent;
                bestIndividual = currentGeneration.bestIndividual();
            }
            logger.writeGeneration(
                    i,
                    bestCurrent,
                    currentGeneration.avgFitness(),
                    currentGeneration.worstFitness()
            );

            // Display algorithm progress
//            float ratio = ((float) i)/generations;
//            int percentage = (int) ((ratio) * 100);
//            System.out.println(percentage+"%");
        }

        // Display time it took to run
        long timeDelta = System.currentTimeMillis()- timeStart;
        double elapsedSeconds = timeDelta / 1000.0;
        System.out.println("Elapsed seconds: " + elapsedSeconds);

        // Display results
        System.out.println("BEST INDIVIDUAL SCORE: " + bestIndividual.totalDistance());
        logger.close();

        double avgResult = currentGeneration.avgFitness();
        int worstResult = currentGeneration.worstFitness();

        return new Result((int) bestFitness, avgResult, worstResult);
    }

    private Individual selectRoulette(Population population)throws Exception{
        int populationSize = population.getPopulationSize();
        double[] distances = new double[populationSize];
        ArrayList<Individual> individuals = population.getIndividuals();

        double worstDistance = 0;
        for(int i = 0; i < populationSize; i++){
            double distance = individuals.get(i).totalDistance();
            distances[i] = distance;

            if(distance > worstDistance)
                worstDistance = distance;
        }

        double sum = 0;
        for(int i = 0; i < populationSize; i++){
            distances[i] = worstDistance - distances[i];
            sum += distances[i];
        }

        for(int i = 0; i < populationSize; i++){
            distances[i] = distances[i] / sum;
        }

        double[] rouletteWheel = new double[populationSize];
        double currentChance = 0;
        for(int i = 0; i < populationSize; i++){
            rouletteWheel[i] = distances[i] + currentChance;
            currentChance += distances[i];
        }

        Random rand = new Random();
        double roll = rand.nextDouble();

        int resultIndex = 0;
        for(int i = 0; i < populationSize; i++){
            if(roll < rouletteWheel[i]){
                resultIndex = i;
                break;
            }
        }
        return new Individual(individuals.get(resultIndex).getGenotype());

    }


    private Individual selectTournament(Population population, int N) throws Exception {
        int populationSize = population.getPopulationSize();
        ArrayList<Individual> chosenOnes = new ArrayList<>();
        for (int i = 0; i< N;i++){
            int randomIndex = new Random().nextInt(populationSize);
            chosenOnes.add(population.getIndividuals().get(randomIndex));
        }
        Individual bestOne = chosenOnes.get(0);
        for (Individual i: chosenOnes){
            if(i.totalDistance() < bestOne.totalDistance()){
                bestOne = i;
            }
        }
        return new Individual(bestOne.getGenotype());
    }


    private Individual oxCrossover(Individual first, Individual second) throws Exception {
        int firstIndex = new Random().nextInt(first.getGenotype().length);
        int secondIndex = new Random().nextInt(second.getGenotype().length);

        if(firstIndex > secondIndex){
            int tmp = firstIndex;
            firstIndex = secondIndex;
            secondIndex = tmp;
        }

        int[] firstGenotype = first.getGenotype();
        int[] secondGenotype = second.getGenotype();
        int[] resultGenotype = new int[first.getGenotype().length];
        ArrayList<Integer> secondGenes = new ArrayList<>();
        for(int i = 0; i<secondGenotype.length;i++){
            secondGenes.add(secondGenotype[i]);
        }

        ArrayList<Integer> toRemove = new ArrayList();
        for(int i = firstIndex; i<=secondIndex; i++){
            resultGenotype[i] = firstGenotype[i];
            toRemove.add(firstGenotype[i]);
        }
        secondGenes.removeAll(toRemove);
        for(int i = 0; i<firstIndex;i++){
            resultGenotype[i] =  secondGenes.remove(0);

        }
        for(int i = secondIndex+1; i<resultGenotype.length;i++){
            resultGenotype[i] = secondGenes.remove(0);
        }
        return new Individual(resultGenotype);

    }

    /**
     * Inversion mutation
     * @param individual individual to be mutated
     * @return new mutated individual
     * @throws Exception models.Individual not configured
     */
    private Individual inversion (Individual individual) throws Exception {
        int[] genotype = individual.getGenotype();
        int random1 = new Random().nextInt(genotype.length);
        int random2;
        do {
             random2 = new Random().nextInt(genotype.length);
        }while (random1 == random2);
        if(random1 > random2){
            int tmp = random1;
            random1 = random2;
            random2 = tmp;
        }
        while(random1 < random2){
            int tmp = genotype[random1];
            genotype[random1] = genotype[random2];
            genotype[random2] = tmp;
            random1++;
            random2--;
        }
        return new Individual(genotype);
    }

    /**
     * Swap mutation
     * @param individual individual to be mutated
     * @return new mutated inidividual
     * @throws Exception models.Individual not configured
     */
    private Individual swap(Individual individual) throws Exception {
        int[] genotype = individual.getGenotype();
        int random1 = new Random().nextInt(genotype.length);
        int random2 = new Random().nextInt(genotype.length);
        int tmp = genotype[random1];
        genotype[random1] = genotype[random2];
        genotype[random2] = tmp;
        return new Individual(genotype);
    }

}

import ga.GeneticAlgorithm;
import ga.MutationType;
import ga.SelectionType;
import models.Individual;
import models.Result;
import utils.TSPLoader;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String[] tspProblems = {"berlin11_modified.tsp", "berlin52.tsp", "ali535.tsp", "fl417.tsp", "gr666.tsp",
                                "kroA100.tsp", "kroA150.tsp", "kroA200.tsp", "nrw1379.tsp", "pr2392.tsp"};



        // EA initial configuration
        int populationSize = 100;
        int generations = 3000;
        float mutationProb = 0.05f;
        float crossoverProb = 0.8f;
        int tournamentSize = 10;

        TSPLoader tspLoader = new TSPLoader(tspProblems[6]);
        Individual.configureIndividual(tspLoader);
//
//        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(tspLoader);
//        models.Result.displayResults(greedyAlgorithm.run());
//        RandomAlgorithm randomAlgorithm = new RandomAlgorithm();
//        models.Result.displayResults(randomAlgorithm.run(10000));
//
        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, generations, mutationProb, crossoverProb, tournamentSize);
        ga.setMutationType(MutationType.INVERSION);
        ga.setSelectionType(SelectionType.TOURNAMENT);
        try {
            //ga.setMutationType(ga.MutationType.SWAP);
            ArrayList<Result> resultArrayList = new ArrayList<>();
            for(int i = 0; i < 3; i++){
                Result result = ga.runAlgorithm();
                ga.setLogger("Data"+i);
                resultArrayList.add(result);
            }
            Result.displayResults(resultArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}

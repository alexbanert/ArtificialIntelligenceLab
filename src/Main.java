public class Main {

    public static void main(String[] args) {
	    TSPLoader.loadCities("berlin52.tsp");
	    int[] test = {1, 2, 3};
	    Individual I1 = new Individual(test);
        System.out.println(I1);
    }
}

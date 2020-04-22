package COMP417.WHPP.GA;

public class GeneticAlgorithm{
	
	public static void main(String args[]){
		Population pop = new Population();
		//pop.printPop();
		int generation = 0;
		while(generation < 1000){
			pop.evaluate();
			pop.printBest();
			pop.select();
			pop.crossbreed();
			pop.mutate();
			generation++;
		}
	}
}
package COMP417.WHPP.GA;

public class GeneticAlgorithm{
	
	public static void main(String args[]){
		Population pop = new Population();
		//pop.printPop();
		int generation = 0;
		pop.evaluate();
		pop.printBest();
		pop.printAvg();
		while(generation < 500){
			pop.select();
			pop.crossbreed();
			pop.mutate();
			pop.evaluate();
			pop.printBest();
			generation++;
		}
		pop.printAvg();
	}
}
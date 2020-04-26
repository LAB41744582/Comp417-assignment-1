package COMP417.WHPP.GA;

public class GeneticAlgorithm{
	
	public static void main(String args[]){
		Population pop = new Population();
		//pop.printPop();
		int generation = 0;
		int no_improvement = 0;
		//int avg = pop.getAvg();
		//int new_avg = avg;
		int best = pop.getFittest();
		int new_best = best;
		pop.evaluate();
		//pop.printBest();
		//pop.printAvg();
		while(true){
			if(no_improvement >= 10)
				break;
			pop.select();
			pop.crossbreed();
			pop.mutate();
			pop.evaluate();
			//pop.printBest();
			generation++;
			pop.printAvg();
			//new_avg = pop.getAvg();
			new_best = pop.getFittest();
			if(new_best == best)
			//if(new_avg == avg)
				no_improvement++;
			else
				no_improvement = 0;
			//avg = new_avg;
			best = new_best;
		}
		pop.printBest();
	}
}
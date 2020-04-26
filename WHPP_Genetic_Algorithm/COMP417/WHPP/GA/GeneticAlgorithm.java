package COMP417.WHPP.GA;

public class GeneticAlgorithm{
	
	public static void main(String args[]){
		Population pop = new Population();
		int generation = 0;
		int no_improvement = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		int best = pop.getFittest(); // Store the fittest(lowest score) individual
		int new_best = best;	// Used to compare with the previous fittest
		
		//pop.printBest();
		//pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossbreed();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generation++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		pop.printBest();	// Print the best offspring that occurs
	}
}
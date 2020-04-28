package COMP417.WHPP.GA;

public class GeneticAlgorithm{
	
	public static void main(String args[]){
		
		tests();
	}
	
	public static void tests(){
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6 (not used), psel = 1.15, crossover = 1 (uniform crossover), mutate = 1 (bit-flip mutation) */
/******************************************** no improvement for 10 generations **************************************************/
		int generations = 0;
		int no_improvement = 0;
		
		Population pop = new Population(0.05, 0.6, 1.15, 1, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		int best = pop.getFittest(); // Store the fittest(lowest score) individual
		int new_best = best;	// Used to compare with the previous fittest
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, bit-flip mutation");
		System.out.println("Initial population:");
		pop.printBest();
		pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();	// Print the best offspring that occurs
		pop.printAvg();
		
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6 (not used), psel = 1.15, crossover = 1 (uniform crossover), mutate = 1 (bit-flip mutation) */
/***************************************************** max generation 500 ********************************************************/
		
		pop = new Population(0.05, 0.6, 1.15, 1, 1);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, bit-flip mutation, 500 generations");
		System.out.println("Initial population:");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new populatio
			generations++;
		}
		System.out.println("Final population:");
		pop.printBest();	// Print the best offspring that occurs
		pop.printAvg();
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6, psel = 1.15, crossover = 2 (k-point crossover), mutate = 2 (swap mutation) 	             */
/******************************************** no improvement for 10 generations **************************************************/
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.05, 0.6, 1.15, 2, 2);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, pcross = 0,6,  psel = 1.15, k-point crossover, swap mutation");
		System.out.println("Initial population:");
		best = pop.getFittest(); // Store the fittest(lowest score) individual
		new_best = best;	// Used to compare with the previous fittest
		
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6, psel = 1.15, crossover = 2 (k-point crossover), mutate = 2 (swap mutation) 	             */
/***************************************************** max generation 500 ********************************************************/
		
		pop = new Population(0.05, 0.6, 1.15, 2, 2);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, pcross = 0,6,  psel = 1.15, k-point crossover, swap mutation, 500 generations");
		System.out.println("Initial population:");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new populatio
			generations++;
		}
		System.out.println("Final population:");
		pop.printBest();
		pop.printAvg();

/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6 (not used), psel = 1.15, crossover = 1 (uniform crossover), mutate = 2 (swap mutation)     */
/******************************************** no improvement for 10 generations **************************************************/

		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.05, 0.6, 1.15, 1, 2);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, swap mutation");
		System.out.println("Initial population:");
		best = pop.getFittest(); // Store the fittest(lowest score) individual
		new_best = best;	// Used to compare with the previous fittest
		
		pop.printBest();
		pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6 (not used), psel = 1.15, crossover = 1 (uniform crossover), mutate = 2 (swap mutation)     */
/***************************************************** max generation 500 ********************************************************/
		
		pop = new Population(0.05, 0.6, 1.15, 1, 2);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, swap mutation, 500 generations");
		System.out.println("Initial population:");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new populatio
			generations++;
		}
		System.out.println("Final population:");
		pop.printBest();
		pop.printAvg();
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6, psel = 1.15, crossover = 2 (k-point crossover), mutate = 1 (bit-flip mutation)            */
/******************************************** no improvement for 10 generations **************************************************/
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.05, 0.6, 1.15, 2, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, pcross = 0.6, psel = 1.15, k-point crossover, bit-flip mutation");
		System.out.println("Initial population:");
		best = pop.getFittest(); // Store the fittest(lowest score) individual
		new_best = best;	// Used to compare with the previous fittest
		
		pop.printBest();
		pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6, psel = 1.15, crossover = 2 (k-point crossover), mutate = 1 (bit-flip mutation)            */
/***************************************************** max generation 500 ********************************************************/
		
		pop = new Population(0.05, 0.6, 1.15, 2, 1);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, pcross = 0.6, psel = 1.15, k-point crossover, bit-flip mutation, 500 generations");
		System.out.println("Initial population:");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population:");
		pop.printBest();
		pop.printAvg();

/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 1 (uniform crossover), mutate = 1 (bit-flip mutation)  */
/******************************************** no improvement for 10 generations **************************************************/
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.01, 0.9, 1.3, 1, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, psel = 1.3, uniform crossover, bit-flip mutation");
		System.out.println("Initial population:");
		best = pop.getFittest(); // Store the fittest(lowest score) individual
		new_best = best;	// Used to compare with the previous fittest
		
		pop.printBest();
		pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 1 (uniform crossover), mutate = 1 (bit-flip mutation)  */
/***************************************************** max generation 500 ********************************************************/
		
		pop = new Population(0.01, 0.9, 1.3, 1, 1);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, psel = 1.3, uniform crossover, bit-flip mutation, 500 generations");
		System.out.println("Initial population:");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population:");
		pop.printBest();
		pop.printAvg();
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 2 (k-point crossover), mutate = 2 (swap mutation) 	 */
/******************************************** no improvement for 10 generations **************************************************/
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.01, 0.9, 1.3, 2, 2);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, swap mutation");
		System.out.println("Initial population:");
		best = pop.getFittest(); // Store the fittest(lowest score) individual
		new_best = best;	// Used to compare with the previous fittest
		
		pop.printBest();
		pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();

/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 2 (k-point crossover), mutate = 2 (swap mutation) 	 */
/***************************************************** max generation 500 ********************************************************/
		
		pop = new Population(0.01, 0.9, 1.3, 2, 2);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, swap mutation, 500 generations");
		System.out.println("Initial population:");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population:");
		pop.printBest();
		pop.printAvg();
				
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 1 (uniform crossover), mutate = 2 (swap mutation) 	 */
/******************************************** no improvement for 10 generations **************************************************/
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.01, 0.9, 1.3, 1, 2);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, psel = 1.3, uniform crossover, swap mutation");
		System.out.println("Initial population:");
		best = pop.getFittest(); // Store the fittest(lowest score) individual
		new_best = best;	// Used to compare with the previous fittest
		
		pop.printBest();
		pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 1 (uniform crossover), mutate = 2 (swap mutation) 	 */
/***************************************************** max generation 500 ********************************************************/
		
		pop = new Population(0.01, 0.9, 1.3, 1, 2);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, psel = 1.3, uniform crossover, swap mutation, 500 generations");
		System.out.println("Initial population:");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population:");
		pop.printBest();
		pop.printAvg();
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9, psel = 1.3, crossover = 2 (k-point crossover), mutate = 1 (bit-flip mutation)             */
/******************************************** no improvement for 10 generations **************************************************/
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.01, 0.9, 1.3, 2, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, bit-flip mutation");
		System.out.println("Initial population:");
		best = pop.getFittest(); // Store the fittest(lowest score) individual
		new_best = best;	// Used to compare with the previous fittest
		
		pop.printBest();
		pop.printAvg();

		while(true){ // Our main loop runs until we stop it
		
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population:");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();

/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9, psel = 1.3, crossover = 2 (k-point crossover), mutate = 1 (bit-flip mutation) 	         */
/***************************************************** max generation 500 ********************************************************/
		
		pop = new Population(0.01, 0.9, 1.3, 2, 1);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		pop.printBest();
		pop.printAvg();
		
		System.out.println("Running for pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, bit-flip mutation, 500 generations");
		System.out.println("Initial population:");
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population:");
		pop.printBest();
		pop.printAvg();
				
	}
}
package COMP417.WHPP.GA;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GeneticAlgorithm{
	
	static int t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16;
	static int g1,g2,g3,g4,g5,g6,g7,g8;
	
	static int[] averages = new int[500];
	static int[] be = new int[500];
	
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		t1 = 0;t2 = 0;t3 = 0;t4 = 0;t5 = 0;t6 = 0;t7 = 0;t8 = 0;t9 = 0;t10 = 0;t11 = 0;t12 = 0;t13 = 0;t14 = 0;t15 = 0;t16 = 0;
		g1 = 0;g2 = 0;g3 = 0;g4 = 0;g5 = 0;g6 = 0;g7 = 0;g8 = 0;
		int choice;
		while(true){
			System.out.println("Choose:");
			System.out.println("(1) Run best method");
			System.out.println("(2) Run tests x1 (~10 mins)");
			System.out.println("(3) Run tests x5 (~60 mins, calculates averages of runs)");
			System.out.println("(4) Exit");
			choice = input.nextInt();
			if(choice == 1){
				exec();
				writefile();
			}
			else if(choice == 2){
				tests();
			}
			else if(choice == 3){
				long tstartTime = System.currentTimeMillis();
				for(int i = 0;i < 5;i++)
					tests();
				printStats();
				long tendTime = System.currentTimeMillis();
 
				long tduration = (tendTime - tstartTime);  //Total execution time in milli seconds
     
				System.out.println("All tests Time elapsed: "+tduration/1000);
			}
			else if(choice == 4)
				break;
			else
				System.out.println("Invalid option!");
		}
		
	}
	
	public static void exec(){
		
		long startTime = System.currentTimeMillis();
		
		int i = 0;
		
		int generations = 0;
		int no_improvement = 0;
		
		Population pop = new Population(0.05, 0.6, 1.15, 1, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, bit-flip mutation");
		System.out.println("Initial population");
		int best = pop.getFittest(); // Store the fittest(lowest score) individual
		int new_best = best;	// Used to compare with the previous fittest
		
		pop.printBest();
		pop.printAvg();
		
		averages[i] = pop.getAvg();
		be[i] = pop.getFittest();
		i++;
		
		/* The most efficient termination condition is the no_improvement condition
		   500 generations (or more) may or may not lead to a better solution however	
		   it take's double or more execution time to find a 3-4% better solution at most,
		   making it a bad cost-to-efficiency trade-off */
		while(true){ // Our main loop runs until we stop it
		//while(generations < 500){
			if(no_improvement >= 10) // Our termination condition
				break;	// If no improvement (lower scores) occurs for 10 generations then we stop
			
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population 
			pop.printBest();
			
			averages[i] = pop.getAvg();
			be[i] = pop.getFittest();
			i++;
			
			generations++;	// A counter for the generations passed
			
			new_best = pop.getFittest();	// Store the new fittest(lowest score) individual
			if(new_best == best)	// Compare with the old one
				no_improvement++;	// If there's no change increment
			else
				no_improvement = 0;	// Else reset
			
			best = new_best;	// Store the fittest(lowest score) individual for future comparison
		}
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		
		long endTime = System.currentTimeMillis();
 
		long duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
	}
	
	public static void tests(){
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6 (not used), psel = 1.15, crossover = 1 (uniform crossover), mutate = 1 (bit-flip mutation) */
/******************************************** no improvement for 10 generations **************************************************/
		long wstartTime = System.currentTimeMillis();
		long startTime = System.currentTimeMillis();
		
		int generations = 0;
		int no_improvement = 0;
		
		Population pop = new Population(0.05, 0.6, 1.15, 1, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		int best = pop.getFittest(); // Store the fittest(lowest score) individual
		int new_best = best;	// Used to compare with the previous fittest
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, bit-flip mutation");
		System.out.println("Initial population");
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
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();	// Print the best offspring that occurs
		pop.printAvg();
		t1 += pop.getAvg();
		g1 += generations;
		
		long endTime = System.currentTimeMillis();
 
		long duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration);
		
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6 (not used), psel = 1.15, crossover = 1 (uniform crossover), mutate = 1 (bit-flip mutation) */
/***************************************************** max generation 500 ********************************************************/
		
		startTime = System.currentTimeMillis();
 
		
		pop = new Population(0.05, 0.6, 1.15, 1, 1);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, bit-flip mutation, 500 generations");
		System.out.println("Initial population");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new populatio
			generations++;
		}
		System.out.println("Final population");
		pop.printBest();	// Print the best offspring that occurs
		pop.printAvg();
		t2 += pop.getAvg();
 
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration);
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6, psel = 1.15, crossover = 2 (k-point crossover), mutate = 2 (swap mutation) 	             */
/******************************************** no improvement for 10 generations **************************************************/
		
		startTime = System.currentTimeMillis();
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.05, 0.6, 1.15, 2, 2);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, pcross = 0,6,  psel = 1.15, k-point crossover, swap mutation");
		System.out.println("Initial population");
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
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		t3 += pop.getAvg();
		g2 += generations;
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration);
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6, psel = 1.15, crossover = 2 (k-point crossover), mutate = 2 (swap mutation) 	             */
/***************************************************** max generation 500 ********************************************************/
		
		startTime = System.currentTimeMillis();
		
		pop = new Population(0.05, 0.6, 1.15, 2, 2);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, pcross = 0,6,  psel = 1.15, k-point crossover, swap mutation, 500 generations");
		System.out.println("Initial population");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new populatio
			generations++;
		}
		System.out.println("Final population");
		pop.printBest();
		pop.printAvg();
		t4 += pop.getAvg();
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);

/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6 (not used), psel = 1.15, crossover = 1 (uniform crossover), mutate = 2 (swap mutation)     */
/******************************************** no improvement for 10 generations **************************************************/
		
		startTime = System.currentTimeMillis();
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.05, 0.6, 1.15, 1, 2);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, swap mutation");
		System.out.println("Initial population");
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
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		t5 += pop.getAvg();
		g3 += generations;
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
		
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6 (not used), psel = 1.15, crossover = 1 (uniform crossover), mutate = 2 (swap mutation)     */
/***************************************************** max generation 500 ********************************************************/
		
		startTime = System.currentTimeMillis();
		
		pop = new Population(0.05, 0.6, 1.15, 1, 2);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, psel = 1.15, uniform crossover, swap mutation, 500 generations");
		System.out.println("Initial population");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new populatio
			generations++;
		}
		System.out.println("Final population");
		pop.printBest();
		pop.printAvg();
		t6 += pop.getAvg();
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6, psel = 1.15, crossover = 2 (k-point crossover), mutate = 1 (bit-flip mutation)            */
/******************************************** no improvement for 10 generations **************************************************/
		
		startTime = System.currentTimeMillis();
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.05, 0.6, 1.15, 2, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, pcross = 0.6, psel = 1.15, k-point crossover, bit-flip mutation");
		System.out.println("Initial population");
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
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		t7 += pop.getAvg();
		g4 += generations;
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.05, pcross = 0.6, psel = 1.15, crossover = 2 (k-point crossover), mutate = 1 (bit-flip mutation)            */
/***************************************************** max generation 500 ********************************************************/
		
		startTime = System.currentTimeMillis();
		
		pop = new Population(0.05, 0.6, 1.15, 2, 1);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.05, pcross = 0.6, psel = 1.15, k-point crossover, bit-flip mutation, 500 generations");
		System.out.println("Initial population");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population");
		pop.printBest();
		pop.printAvg();
		t8 += pop.getAvg();
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);

/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 1 (uniform crossover), mutate = 1 (bit-flip mutation)  */
/******************************************** no improvement for 10 generations **************************************************/
		
		startTime = System.currentTimeMillis();
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.01, 0.9, 1.3, 1, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, psel = 1.3, uniform crossover, bit-flip mutation");
		System.out.println("Initial population");
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
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		t9 += pop.getAvg();
		g5 += generations;
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 1 (uniform crossover), mutate = 1 (bit-flip mutation)  */
/***************************************************** max generation 500 ********************************************************/
		
		startTime = System.currentTimeMillis();
		
		pop = new Population(0.01, 0.9, 1.3, 1, 1);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, psel = 1.3, uniform crossover, bit-flip mutation, 500 generations");
		System.out.println("Initial population");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population");
		pop.printBest();
		pop.printAvg();
		t10 += pop.getAvg();
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 2 (k-point crossover), mutate = 2 (swap mutation) 	 */
/******************************************** no improvement for 10 generations **************************************************/
		
		startTime = System.currentTimeMillis();
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.01, 0.9, 1.3, 2, 2);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, swap mutation");
		System.out.println("Initial population");
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
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		t11 += pop.getAvg();
		g6 += generations;
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);

/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 2 (k-point crossover), mutate = 2 (swap mutation) 	 */
/***************************************************** max generation 500 ********************************************************/
		
		startTime = System.currentTimeMillis();
		
		pop = new Population(0.01, 0.9, 1.3, 2, 2);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, swap mutation, 500 generations");
		System.out.println("Initial population");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population");
		pop.printBest();
		pop.printAvg();
		t12 += pop.getAvg();
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
				
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 1 (uniform crossover), mutate = 2 (swap mutation) 	 */
/******************************************** no improvement for 10 generations **************************************************/
		
		startTime = System.currentTimeMillis();
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.01, 0.9, 1.3, 1, 2);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, psel = 1.3, uniform crossover, swap mutation");
		System.out.println("Initial population");
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
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		t13 += pop.getAvg();
		g7 += generations;
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9 (not used), psel = 1.3, crossover = 1 (uniform crossover), mutate = 2 (swap mutation) 	 */
/***************************************************** max generation 500 ********************************************************/
		
		startTime = System.currentTimeMillis();
		
		pop = new Population(0.01, 0.9, 1.3, 1, 2);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, psel = 1.3, uniform crossover, swap mutation, 500 generations");
		System.out.println("Initial population");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population");
		pop.printBest();
		pop.printAvg();
		t14 += pop.getAvg();
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);
		
/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9, psel = 1.3, crossover = 2 (k-point crossover), mutate = 1 (bit-flip mutation)             */
/******************************************** no improvement for 10 generations **************************************************/
		
		startTime = System.currentTimeMillis();
		
		generations = 0;
		no_improvement = 0;
		
		pop = new Population(0.01, 0.9, 1.3, 2, 1);
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, bit-flip mutation");
		System.out.println("Initial population");
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
		System.out.println("Final population");
		System.out.println("Generations passed: "+ generations);
		pop.printBest();
		pop.printAvg();
		t15 += pop.getAvg();
		g8 += generations;
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration/1000);

/*********************************************************************************************************************************/
/* 1st test pmut = 0.01, pcross = 0.9, psel = 1.3, crossover = 2 (k-point crossover), mutate = 1 (bit-flip mutation) 	         */
/***************************************************** max generation 500 ********************************************************/
		
		startTime = System.currentTimeMillis();
		
		pop = new Population(0.01, 0.9, 1.3, 2, 1);
		
		generations = 0;
		
		pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the population
		
		System.out.println("Running for pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, bit-flip mutation, 500 generations");
		System.out.println("Initial population");
		pop.printBest();
		pop.printAvg();
		
		while(generations < 500){
			pop.select();	// Divide the population in pairs of parents
			pop.crossover();	// Produce a new population out of the offsprings
			pop.mutate();	// Mutate some of them
			pop.evaluate();	// Assign fitness values (penalty score) to all individuals of the new population
			generations++;
		}
		System.out.println("Final population");
		pop.printBest();
		pop.printAvg();
		t16 += pop.getAvg();
		
		endTime = System.currentTimeMillis();
 
		duration = (endTime - startTime);  //Total execution time in milli seconds
     
		System.out.println("Time elapsed: "+duration);
		
		long wendTime = System.currentTimeMillis();
 
		long wduration = (wendTime - wstartTime);  //Total execution time in milli seconds
     
		System.out.println("Total Time elapsed: "+wduration/1000);
		
	}
	
	public static void printStats(){
		System.out.println("pmut = 0.05, psel = 1.15, uniform crossover, bit-flip mutation");
		System.out.println("Average of averages: "+ t1/5);
		System.out.println("Average generations: "+ g1/5);
		System.out.println("pmut = 0.05, psel = 1.15, uniform crossover, bit-flip mutation, 500 generations");
		System.out.println("Average of averages: "+ t2/5);
		System.out.println("pmut = 0.05, pcross = 0,6, psel = 1.15, k-point crossover, swap mutation");
		System.out.println("Average of averages: "+ t3/5);
		System.out.println("Average generations: "+ g2/5);
		System.out.println("pmut = 0.05, pcross = 0,6, psel = 1.15, k-point crossover, swap mutation, 500 generations");
		System.out.println("Average of averages: "+ t4/5);
		System.out.println("pmut = 0.05, psel = 1.15, uniform crossover, swap mutation");
		System.out.println("Average of averages: "+ t5/5);
		System.out.println("Average generations: "+ g3/5);
		System.out.println("pmut = 0.05, psel = 1.15, uniform crossover, swap mutation, 500 generations");
		System.out.println("Average of averages: "+ t6/5);
		System.out.println("pmut = 0.05, pcross = 0.6, psel = 1.15, k-point crossover, bit-flip mutation");
		System.out.println("Average of averages: "+ t7/5);
		System.out.println("Average generations: "+ g4/5);
		System.out.println("pmut = 0.05, pcross = 0.6, psel = 1.15, k-point crossover, bit-flip mutation, 500 generations");
		System.out.println("Average of averages: "+ t8/5);
		System.out.println("pmut = 0.01, psel = 1.3, uniform crossover, bit-flip mutation");
		System.out.println("Average of averages: "+ t9/5);
		System.out.println("Average generations: "+ g5/5);
		System.out.println("pmut = 0.01, psel = 1.3, uniform crossover, bit-flip mutation, 500 generations");
		System.out.println("Average of averages: "+ t10/5);
		System.out.println("pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, swap mutation");
		System.out.println("Average of averages: "+ t11/5);
		System.out.println("Average generations: "+ g6/5);
		System.out.println("pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, swap mutation, 500 generations");
		System.out.println("Average of averages: "+ t12/5);
		System.out.println("pmut = 0.01, psel = 1.3, uniform crossover, swap mutation");
		System.out.println("Average of averages: "+ t13/5);
		System.out.println("Average generations: "+ g7/5);
		System.out.println("pmut = 0.01, psel = 1.3, uniform crossover, swap mutation, 500 generations");
		System.out.println("Average of averages: "+ t14/5);
		System.out.println("pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, bit-flip mutation");
		System.out.println("Average of averages: "+ t15/5);
		System.out.println("Average generations: "+ g8/5);
		System.out.println("pmut = 0.01, pcross = 0.9, psel = 1.3, k-point crossover, bit-flip mutation, 500 generations");
		System.out.println("Average of averages: "+ t16/5);
	}
	
	public static void writefile() {
   
		//String strFilePath = "./log/averages.txt";
   
		try
		{
			//create FileOutputStream object
			File aver = new File("averages.txt");
			File b = new File("best.txt");
			aver.createNewFile(); // if file already exists will do nothing 
			b.createNewFile(); // if file already exists will do nothing
			FileOutputStream fos = new FileOutputStream(aver, false);
			FileOutputStream fos_b = new FileOutputStream(b, false);
			//FileOutputStream fos = new FileOutputStream(strFilePath);
     
			/*
			* To create DataOutputStream object from FileOutputStream use,
			* DataOutputStream(OutputStream os) constructor.
			*
			*/
      
			DataOutputStream dos = new DataOutputStream(fos);
			DataOutputStream dos_b = new DataOutputStream(fos_b);
      
      
			/*
			* To write an int value to a file, use
			* void writeInt(int i) method of Java DataOutputStream class.
			*
			* This method writes specified int to output stream as 4 bytes value.
			*/
			int i = 0;
			while(i < averages.length && averages[i] != 0){
				dos.writeInt(averages[i]);
				dos_b.writeInt(be[i]);
				i++;
			}
       
			/*
			* To close DataOutputStream use,
			* void close() method.
			*
			*/
        
			dos.close();
			dos_b.close();
        
		}
		catch (IOException e)
		{
			System.out.println("IOException : " + e);
		}
 
  }
}
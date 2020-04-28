package COMP417.WHPP.GA;

import java.util.Random;
import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Population {
	
	/* Population size, constant throughout the execution,
	higher values result in better diversity and result */
	private final static int POP_SIZE = 15000;
	/* Sum of fitness scores of all individuals */
	private int Sf = 0;	
	/* The rate at which mutations are to be performed, 5% was the optimal experimental value found */
	private double mut_rate; //= 0.05;
	/* The rate at which more genes will inherit from fittest parent (lower score), used on the 2-point crossover
	0,6 was the optimal experimental value found */
	private double cross_rate; //= 0.6;
	/* A coefficient that determines how much we favour individuals of higher rank, used on the rank selection,
	values lower than 1 favour highest score parents (worst), 1 picks random individuals,
	higher than 1 favours lower score (higher rank) individuals but may result in rapid convergence,
	1.15 was the optimal value found for this magnitude of population */
	private double bias; //= 1.15;
	
	private int cross_type;
	
	private int mut_type;
	
	private static Random m_rand = new Random();
	
	/* An array of Schedule objects for the population */
	Schedule pop[] = new Schedule[POP_SIZE];
	/* An array of Schedule objects for the offsprings */
	Schedule new_pop[] = new Schedule[POP_SIZE];
	/* An array of Schedule objects for the 1st parent */
	Schedule s1[] = new Schedule[POP_SIZE/2];
	/* An array of Schedule objects for the 2nd parent */
	Schedule s2[] = new Schedule[POP_SIZE/2];
	
	public Population(double pmut, double pcross, double psel, int cross_type, int mut_type){	// Our constructor
		this.mut_rate = pmut;
		this.cross_rate = pcross;
		this.bias = psel;
		this.cross_type = cross_type;
		this.mut_type = mut_type;
		this.init();
	}
	
	private void init(){	// Initialise our arrays
		for(int iter = 0; iter < POP_SIZE; iter++){
			pop[iter] = new Schedule();	// Random schedules 
			new_pop[iter] = new Schedule(0);	// Filled with 0 (empty) schedules
		}
	}
	
	public void printPop(){	// A function that can be used to print the entire population
		for(int iter = 0; iter < POP_SIZE; iter++){
			System.out.println(pop[iter]);
		}
	}
	
	public void printBest(){	// A function that prints the score of the best individual
		int min = Integer.MAX_VALUE;
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(pop[iter].getFitness() < min)
				min = pop[iter].getFitness();
		}
		/*for(int iter = 0; iter < POP_SIZE; iter++){
			if(pop[iter].getFitness() == min)
				System.out.println(pop[iter]);
		}*/
		System.out.println("Best schedule score: "+min);
	}
	
	public void printAvg(){	// A function that prints the score of the average individual
		int cF = 0;
		for(int iter = 0; iter < POP_SIZE; iter++){
			cF += pop[iter].getFitness();
		}
		int avg = cF/POP_SIZE;
		System.out.println("Average schedule score: "+avg);
	}
	
	public int getAvg(){	// A function that returns the average score of the generation
		int cF = 0;
		for(int iter = 0; iter < POP_SIZE; iter++){
			cF += pop[iter].getFitness();
		}
		int avg = cF/POP_SIZE;
		return avg;
	}
	
	public Schedule getBest(){	// A function that returns the best individual (lowest score)
		int min = Integer.MAX_VALUE;
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(pop[iter].getFitness() < min)
				min = pop[iter].getFitness();
		}
		Schedule gb = null;
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(pop[iter].getFitness() == min)
				gb = pop[iter];
		}
		return gb;
	}
	
	public int getFittest(){	// A function that prints the best individual score (lowest score)
		int min = Integer.MAX_VALUE;
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(pop[iter].getFitness() < min)
				min = pop[iter].getFitness();
		}
		Schedule gb = null;
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(pop[iter].getFitness() == min)
				gb = pop[iter];
		}
		return gb.getFitness();
	}
	
	public void select(){	// Selection loop
		Arrays.sort(pop);	// For the rank selection we need to sort the population by score
		for(int iter = 0; iter < POP_SIZE/2; iter++){
			//s1[iter] = rouletteWheelSelection();	// Roulette wheel selection
			//s2[iter] = rouletteWheelSelection();
			s1[iter] = rankingSelection();	// Rank selection
			s2[iter] = rankingSelection();
		}
	}
	
	/* Return the individual in the index position, 
	population must be sorted by fitness first
	Darrell Whitley's Rank-biased selection algorithm */
	/* Whitley, Darrell. (2000). The GENITOR Algorithm and Selection Pressure: Why Rank-Based Allocation of Reproductive Trials is Best. 89. */
	public Schedule rankingSelection(){
		double rand01 = Math.random();
		int index = (int)(POP_SIZE * (bias - Math.sqrt(bias*bias - 4.0*(bias-1) * rand01)) 
			/ 2.0 / (bias-1));	// Darrell Whitley's bias formula
		return pop[index];
	/* Experiments suggested that this approach for selecting parents,
	results in better solutions to this specific problem */
	}
	
	/* Return an individual with probability randNum,
	individuals with better scores (lower) have a higher probability
	this particular implementation was taken from the github trunk of gflengas,
	as it happens, from a past project of this same course */
	/* https://github.com/gflengas/Genetic-Algorithm-for-WHPP */
	public Schedule rouletteWheelSelection() {
    	//Wheel spin
        double randNum = m_rand.nextDouble() * Sf;
        int idx,wheel=0;
        for (idx=0; idx < POP_SIZE && randNum>0; ++idx) {
        	wheel+= pop[idx].getFitness();
        	//System.out.println(wheel);
			//System.out.println(randNum);
            if(wheel>=randNum) 
            	return pop[idx];
        }
        return pop[POP_SIZE-1];
	/* Experiments showed that this selection method is not oprimal for the particular problem,
	mainly because of the method used to produce the initial population which yields individuals of
	very similar scores */
    }
	
	public void crossover(){	// Wraper function for crossover functions
		if(this.cross_type == 1)
			uniformCrossover();	// Uniform crossover
		else
			kPointCrossover();	// k-point (2 point) crossover
	}

	private void kPointCrossover(){	//2 point crossover
		int k1 = 14*1/3;	// divide the schedule in 3 equal parts vertically (ensure feasibility)
		int k2 = 14*2/3;
		int ndx = 0;
		double p;
		for(int iter = 0; iter < pop.length; iter++){	// Iterate through population
				p = Math.random();
				if(p < cross_rate){	// if p < crossover rate, then we favour the fittest parent (out of the pair)
					if(s1[ndx].getFitness() <= s2[ndx].getFitness()){	// check which one's the fittest
						for(int col = 0; col < k1; col++)	// use two parts from fittest and one from the other 
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
						for(int col = k1; col < k2; col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
						for(int col = k2; col < pop[iter].getSD(); col++)
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
					}
					else{	// same as above if the other one was fitter
						for(int col = 0; col < k1; col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
						for(int col = k1; col < k2; col++)
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
						for(int col = k2; col < pop[iter].getSD(); col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
					}
				}
				else{	// if p < crossover rate, we favour the least fit parent (out of the pair), this is done to ensure some diversity amongst the offsprings
					if(s1[ndx].getFitness() <= s2[ndx].getFitness()){	// same as above
						for(int col = 0; col < k1; col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
						for(int col = k1; col < k2; col++)
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
						for(int col = k2; col < pop[iter].getSD(); col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
					}
					else{
						for(int col = 0; col < k1; col++)
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
						for(int col = k1; col < k2; col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
						for(int col = k2; col < pop[iter].getSD(); col++)
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
					}
				}
				/*if(new_pop[iter].isFeasible())
					System.out.println("OK");*/
			
			if(ndx < pop.length/2 - 1)	// an index used to iterate through parent arrays
				ndx++;
			else
				ndx = 0;
		}
		pop = new_pop;	// set the population to new population
	}
	
	private void uniformCrossover(){	// uniform crossover, selects each day (vertical partition) from either parent randomly
	
		double p;
		int ndx = 0;
		for(int iter = 0; iter < pop.length; iter++){	// essentially the same logic as k-point crossover with k = 14 and crossover rate = 0.5
			for(int col = 0; col < pop[iter].getSD(); col++){
				p = Math.random();
				if(p >= 0.5){
					new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
				}
				else{
					new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
				}
				/*if(new_pop[iter].isFeasible())
					System.out.println("OK");*/
			}
			
			if(ndx < pop.length/2 - 1)
				ndx++;
			else
				ndx = 0;
		}
		pop = new_pop;
	/* Experiments suggested that uniform crossover produces better results,
	mainly because it ensures higher diversity */
	}
	
	public void mutate(){	// mutation algorithm
		double p = Math.random();
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(p <= mut_rate){
				if(mut_type == 1)
					setMutant(iter);	// essentially a bit-flip mutation
				else
					swapMutate(iter);	// a swap mutation
			}
		}
	}
	
	private void swapMutate(int child){	// swap two days of the schedule (ensures feasibility)
		int p1 = (int)(Math.random() * ((pop[child].getSD() - 1) + 1));	// randomly select first day
		int p2 = (int)(Math.random() * ((pop[child].getSD() - 1) + 1));	// randomly select second day
		int[] temp1 = pop[child].getColumn(p1);
		int[] temp2 = pop[child].getColumn(p2);
		pop[child].setColumn(temp1, p2);	// swap the days
		pop[child].setColumn(temp2, p1);
	}
	
	/* Bit-wise flip logic applied to WHPP problem (if I am interprenting it correctly) 
	   just shuffle a day's shifts between the employees, ensures feasibility */
	private void setMutant(int child){
		int p = (int)(Math.random() * ((pop[child].getSD() - 1) + 1));
		Integer[] boxedArray = Arrays.stream(pop[child].getColumn(p)) // IntStream
								.boxed()				// Stream<Integer>
								.toArray(Integer[]::new);	// conversion I did because I am too lazy to write my own shuffle method
		List<Integer> intList = Arrays.asList(boxedArray);
		Collections.shuffle(intList);
		intList.toArray(boxedArray);
		int[] intArray = Arrays.stream(boxedArray).mapToInt(Integer::intValue).toArray();
		pop[child].setColumn(intArray, p);
		/* Experimentally this mutation did not produce very good results because of the limited mutation
		   also the method used to shuffle the day column array makes it much slower */
	}
	
	public void evaluate(){ // for each individual assign a penalty depending on the soft constraints
		//Sf = 0;
		int score = 0;
		for(int iter = 0; iter < pop.length; iter++){
			int f = assignEvaluation(pop[iter].getSchedule(), iter);
			//Sf += f;
			pop[iter].setFitness(f);
		}
	}
	
	private int assignEvaluation(int[][] sched, int number){	//	the actual evaluation function
		
		int penalty = 0;
		
		for(int emp = 0; emp < sched.length; emp++){ // loop through every employees
			int free = 0;	// free day counter
			int conc = 0;	// concurrent work days counter
			boolean needrest = false;	//	a flag used for determining whether the employee is in need of free day
			int hours = 0;	//	a counter for hours worked across the entire schedule
			int nshift = 0;	// a counter for night shifts
			for(int day = 0; day < sched[emp].length; day++){	// loop through every day
				if(sched[emp][day] == 1 || sched[emp][day] == 2){ // morning or afternoon shift
					if(free == 1)	// if the employee rested for just one day, penalty 1
						penalty += 1;
					if(needrest)	// if it's been time to rest already, penalty 100
						penalty += 100;
					hours += 8;	//	8 hour shifts
					free = 0;	// reset free day counter since he's working again
					conc++;	//	increment concurrent work days
					if(conc == 7)	// if this is the 7th day, mark the employee for rest
						needrest = true;
					nshift = 0;	//	reset night shifts since this is a different shift
					if((day - 1) >= 0 && sched[emp][day] == 1 && sched[emp][day - 1] == 3)
						penalty += 1000;	// night shift to morning shift, penalty 1000
					if((day - 1) >= 0 && sched[emp][day] == 1 && sched[emp][day - 1] == 2)
						penalty += 800;	//	afternoon shift to morning shift, penalty 800
					if((day - 1) >= 0 && sched[emp][day] == 2 && sched[emp][day - 1] == 3)
						penalty += 800;	//	night shift to afternoon shift, penalty 800
				}
				else if(sched[emp][day] == 3){	// if this is a night shift
					if(free == 1)	// same as above
						penalty += 1;
					if(needrest)
						penalty += 100;	//	same as above
					hours += 10;	//	10 hour shifts
					free = 0;	// same as above
					conc++;	//	same as above
					if(conc == 7)	//	same as above
						needrest = true;
					nshift++;	//	increment night shifts
					if(nshift == 4)	//	if it's been 4 concurrent, marka the employee for rest
						needrest = true;
					if(nshift > 4)	// if it's been more than 4, penalty 1000
						penalty += 1000;
				}
				else{	// if this is a free day
					conc = 0;	// reset work days
					free++;	//	increment free days
					if((day - 2) >= 0 && free == 1 && sched[emp][day - 2]!= 0)	//	if it's been only one rest day, penalty 1
						penalty += 1;
					if(free >= 2)	// if it's been 2 or more free days, mark the employee as rested
						needrest = false;
				}
			}
			if(hours > 70)	//	more than 70 hours, penalty 1000
				penalty += 1000;
			if(conc > 7)	// more than 7 concurrent days, penalty 1000
				penalty += 1000;
		}
		//System.out.println("Schedule number "+ number +" penalty: "+ penalty);
		return penalty;	// this is the final score for the entire schedule
	}
	
}
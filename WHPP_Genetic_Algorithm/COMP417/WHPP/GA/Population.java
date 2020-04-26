package COMP417.WHPP.GA;

import java.util.Random;
import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Population {
	private final static int POP_SIZE = 15000;
	private int Sf = 0;
	private double mut_rate = 0.05;
	private double cross_rate = 0.6;
	private double bias = 1.15;
	Schedule pop[] = new Schedule[POP_SIZE];
	Schedule new_pop[] = new Schedule[POP_SIZE];
	Schedule s1[] = new Schedule[POP_SIZE/2];
	Schedule s2[] = new Schedule[POP_SIZE/2];
	private static Random m_rand = new Random();
	
	public Population(){
		this.init();
	}
	
	private void init(){
		for(int iter = 0; iter < POP_SIZE; iter++){
			pop[iter] = new Schedule();
			new_pop[iter] = new Schedule(1);
		}
	}
	
	public void printPop(){
		for(int iter = 0; iter < POP_SIZE; iter++){
			System.out.println(pop[iter]);
		}
	}
	
	public void printBest(){
		int min = Integer.MAX_VALUE;
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(pop[iter].getFitness() < min)
				min = pop[iter].getFitness();
		}
		/*for(int iter = 0; iter < POP_SIZE; iter++){
			if(pop[iter].getFitness() == min)
				System.out.println(pop[iter]);
		}*/
		System.out.println(min);
	}
	
	public void printAvg(){
		int cF = 0;
		for(int iter = 0; iter < POP_SIZE; iter++){
			cF += pop[iter].getFitness();
		}
		int avg = cF/POP_SIZE;
		System.out.println("average: "+avg);
	}
	
	public int getAvg(){
		int cF = 0;
		for(int iter = 0; iter < POP_SIZE; iter++){
			cF += pop[iter].getFitness();
		}
		int avg = cF/POP_SIZE;
		return avg;
	}
	
	public Schedule getBest(){
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
	
	public int getFittest(){
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
	
	public void select(){
		Arrays.sort(pop);
		for(int iter = 0; iter < POP_SIZE/2; iter++){
			//s1[iter] = rouletteWheelSelection();
			//s2[iter] = rouletteWheelSelection();
			s1[iter] = rankingSelection();
			s2[iter] = rankingSelection();
		}
	}
	
	// return the index into the population of the selected parent. 
	// population must be sorted by fitness first
	// Darrell Whitley's Rank-biased selection
	public Schedule rankingSelection(){
		double rand01 = Math.random();
		int index = (int)(POP_SIZE * (bias - Math.sqrt(bias*bias - 4.0*(bias-1) * rand01)) 
			/ 2.0 / (bias-1));
		return pop[index]; 
	}
	
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
    }
	
	public void crossbreed(){
		uniformCrossover();
		//kPointCrossover();
	}

	private void kPointCrossover(){//2 point crossover
		int k1 = 14*1/3;
		int k2 = 14*2/3;
		int ndx = 0;
		double p;
		for(int iter = 0; iter < pop.length; iter++){
				p = Math.random();
				if(p < cross_rate){
					if(s1[ndx].getFitness() <= s2[ndx].getFitness()){
						for(int col = 0; col < k1; col++)
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
						for(int col = k1; col < k2; col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
						for(int col = k2; col < pop[iter].getSD(); col++)
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
					}
					else{
						for(int col = 0; col < k1; col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
						for(int col = k1; col < k2; col++)
							new_pop[iter].setColumn(s1[ndx].getColumn(col), col);
						for(int col = k2; col < pop[iter].getSD(); col++)
							new_pop[iter].setColumn(s2[ndx].getColumn(col), col);
					}
				}
				else{
					if(s1[ndx].getFitness() <= s2[ndx].getFitness()){
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
			
			if(ndx < pop.length/2 - 1)
				ndx++;
			else
				ndx = 0;
		}
		pop = new_pop;
	}
	
	private void uniformCrossover(){
		double p;
		int ndx = 0;
		for(int iter = 0; iter < pop.length; iter++){
			//new_pop[iter] = s1[ndx];
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
	}
	
	public void mutate(){
		double p = Math.random();
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(p <= mut_rate){
				//setMutant(iter);
				swapMutate(iter);
			}
		}
	}
	
	private void swapMutate(int child){
		int p1 = (int)(Math.random() * ((pop[child].getSD() - 1) + 1));
		int p2 = (int)(Math.random() * ((pop[child].getSD() - 1) + 1));
		int[] temp1 = pop[child].getColumn(p1);
		int[] temp2 = pop[child].getColumn(p2);
		pop[child].setColumn(temp1, p2);
		pop[child].setColumn(temp2, p1);
	}
	
	//just shuffle a day's shifts between the employees
	private void setMutant(int child){
		int p = (int)(Math.random() * ((pop[child].getSD() - 1) + 1));
		Integer[] boxedArray = Arrays.stream(pop[child].getColumn(p)) // IntStream
								.boxed()				// Stream<Integer>
								.toArray(Integer[]::new);
		List<Integer> intList = Arrays.asList(boxedArray);
		Collections.shuffle(intList);
		intList.toArray(boxedArray);
		int[] intArray = Arrays.stream(boxedArray).mapToInt(Integer::intValue).toArray();
		pop[child].setColumn(intArray, p);
	}
	
	public int evaluate(){
		Sf = 0;
		int score = 0;
		for(int iter = 0; iter < pop.length; iter++){
			int f = assignEvaluation(pop[iter].getSchedule(), iter);
			Sf += f;
			pop[iter].setFitness(f);
			score = pop[iter].getFitness();
		}
		return score;
	}
	
	private int assignEvaluation(int[][] sched, int number){
		int penalty = 0;
		for(int emp = 0; emp < sched.length; emp++){
			int free = 0;
			int conc = 0;
			boolean needrest = false;
			int hours = 0;
			int nshift = 0;
			for(int day = 0; day < sched[emp].length; day++){
				if(sched[emp][day] == 1 || sched[emp][day] == 2){
					if(free == 1)
						penalty += 1;
					if(needrest)
						penalty += 100;
					hours += 8;
					free = 0;
					conc++;
					if(conc == 7)
						needrest = true;
					nshift = 0;
					if((day - 1) >= 0 && sched[emp][day] == 1 && sched[emp][day - 1] == 3)
						penalty += 1000;
					if((day - 1) >= 0 && sched[emp][day] == 1 && sched[emp][day - 1] == 2)
						penalty += 800;
					if((day - 1) >= 0 && sched[emp][day] == 2 && sched[emp][day - 1] == 3)
						penalty += 800;
				}
				else if(sched[emp][day] == 3){
					if(free == 1)
						penalty += 1;
					if(needrest)
						penalty += 100;
					hours += 10;
					free = 0;
					conc++;
					if(conc == 7)
						needrest = true;
					nshift++;
					if(nshift == 4)
						needrest = true;
					if(nshift > 4)
						penalty += 1000;
				}
				else{
					conc = 0;
					free++;
					if((day - 2) >= 0 && free == 1 && sched[emp][day - 2]!= 0)
						penalty += 1;
					if(free >= 2)
						needrest = false;
				}
			}
			if(hours > 70)
				penalty += 1000;
			if(conc > 7)
				penalty += 1000;
		}
		//System.out.println("Schedule number "+ number +" penalty: "+ penalty);
		return penalty;
	}
	
}
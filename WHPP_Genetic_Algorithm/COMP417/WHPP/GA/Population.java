package COMP417.WHPP.GA;

import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Population {
	private final static int POP_SIZE = 500;
	private int Sf = 0;
	private double mut_rate = 0.06;
	Schedule pop[] = new Schedule[POP_SIZE];
	Schedule new_pop[] = new Schedule[POP_SIZE];
	Schedule s1[] = new Schedule[POP_SIZE/2];
	Schedule s2[] = new Schedule[POP_SIZE/2];
	
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
	
	public void select(){
		double pp = 0;
		for(int iter = 0; iter < POP_SIZE; iter++){
			pop[iter].setProbability(Sf,pp);
			pp = pop[iter].getProbability();
			//System.out.println("parent probability: "+ pp);
		}
		for(int pairs = 0; pairs < POP_SIZE/2; pairs++){
			double p = Math.random();
			for(int iter = 0; iter < POP_SIZE; iter++){
				if( pop[iter].getProbability() <= p)
					s1[pairs] = pop[iter];
				if(s1[pairs] == null)
					s1[pairs] = pop[iter];
				s2[pairs] = getBest();
			}
		}
	}
	
	public void crossbreed(){
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
		//Sf = 0;
		//evaluate();
		//for(int iter = 0; iter < pop.length; iter++)
			//System.out.println(pop[iter]);
		//System.out.println(pop.length);
	}
	
	public void mutate(){
		double p = Math.random();
		for(int iter = 0; iter < POP_SIZE; iter++){
			if(p <= mut_rate){
				setMutant(iter);
			}
		}
	}
	
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
package COMP417.WHPP.GA;

import java.lang.Math;
import java.util.Arrays;

public class Population {
	private final static int POP_SIZE = 100;
	private int Sf = 0;
	private int ndx = 0;
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
		for(int iter = 0; iter < POP_SIZE/2; iter++){
			s1[iter] = new Schedule(1);
			s2[iter] = new Schedule(1);
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
	
	public void select(){
		double pp = 0;
		for(int iter = 0; iter < POP_SIZE; iter++){
			pop[iter].setProbability(Sf,pp);
			pp = pop[iter].getProbability();
		}
		for(int pairs = 0; pairs < POP_SIZE/2; pairs++){
			double p1 = Math.random();
			double p2 = Math.random();
			for(int iter = 0; iter < POP_SIZE; iter++){
				if(p1 >= pop[iter].getProbability()){
					s1[pairs] = pop[iter];
				}
				if(p2 >= pop[iter].getProbability()){
					s2[pairs] = pop[iter];
				}
			}
		}
		/*for(int i =0; i<s1.length;i++){
			System.out.println(s1[i]);
			System.out.println("second");
			System.out.println(s2[i]);
		}*/
	}
	
	public void crossbreed(){
		double select;
		int ndx = 0;
		for(int iter = 0; iter < pop.length; iter++){
			//crossover(iter);
		}
		//pop = new_pop;
		//Sf = 0;
		//evaluate();
		//for(int iter = 0; iter < pop.length; iter++)
			//System.out.println(pop[iter]);
		//System.out.println(pop.length);
	}
	
	public void mutate(){
	}
	
	public boolean evaluate(){
		Sf = 0;
		for(int iter = 0; iter < pop.length; iter++){
			int f = assignEvaluation(pop[iter].getSchedule(), iter);
			Sf += f;
			pop[iter].setFitness(f);
		}
		
		return true;
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
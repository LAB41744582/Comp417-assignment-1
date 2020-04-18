package COMP417.WHPP.GA;

public class Population {
	private final static int POP_SIZE = 100;
	private int Sf = 0;
	Schedule pop[] = new Schedule[POP_SIZE];
	
	public Population(){
		this.init();
	}
	
	private void init(){
		for(int iter = 0; iter < POP_SIZE; iter++){
			pop[iter] = new Schedule();
		}
	}
	
	public void printPop(){
		for(int iter = 0; iter < POP_SIZE; iter++){
			System.out.println(pop[iter]);
		}
	}
	
	public void printBest(){}
	
	public void select(){
		for(int iter = 0; iter < POP_SIZE; iter++){
			pop[iter].setProbability(Sf);
			System.out.println(pop[iter].getProbability());
		}
	}
	
	public void crossbreed(){}
	
	public void mutate(){}
	
	public boolean evaluate(){
		for(int iter = 0; iter < POP_SIZE; iter++){
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
		System.out.println("Schedule number "+ number +" penalty: "+ penalty);
		return penalty;
	}
	
}
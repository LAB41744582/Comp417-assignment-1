package COMP417.WHPP.GA;

public class Population {
	private final static int POP_SIZE = 100;
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
	
	public void select(){}
	
	public void crossbreed(){}
	
	public void mutate(){}
	
	public boolean evaluate(){
		assignEvaluation();
		return true;
	}
	
	private void assignEvaluation(Schedule sched){
		for(int emp = 0; emp < sched.length; emp++){
			for(int day = 0; day < sched[emp].length; day++){
				if(sched[emp][day] == 1 || sched[emp][day] == 2)
					hours += 8;
				else if(sched[emp][day] == 3)
					hours += 10;
				if(sched[emp][day] != 0)
					conc++;
				if(sched[emp][day] == 3)
					nshift++;
				
			}
		}
	}
	
}
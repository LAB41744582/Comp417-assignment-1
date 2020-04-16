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
	
}
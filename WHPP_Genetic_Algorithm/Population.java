package COMP417.WHPP.GA;

public class Population {
	private final static POP_SIZE = 1600;
	public Schedule[POP_SIZE] pop;
	
	public Population(){
		this.init();
	}
	
	private void init(){
		for(int iter = 0; iter < POP_SIZE; iter++){
			pop[iter] = new Schedule();
		}
	}
	
}
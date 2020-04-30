package COMP417.WHPP.GA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class Schedule implements Comparable<Schedule>{
	
	/* 30 employees, constant */
	private final static int EMP_NUM = 30;
	/* 14 calendar days, constant */
	private final static int SCHED_DAY_NUM = 14;
	/* an 2d-array with employees as rows and days as columns */
	private int sched[][] = new int[EMP_NUM][SCHED_DAY_NUM];
	/* fitness of the schedule-chromosome, lower -> better */
	private int fitness;
	/* probability to be selected by roulette, not in use */
	private double probability = 0;
	
	/* constant number of each shift every mon-tue, wed-fri, thu-sat-sun,
	   this way feasibility is guaranteed but diversity may suffer */
	private final static Integer[] MON_TUE = new Integer[] {0,0,0,0,0,1,1,1,1,1,1,1,1,1,
										1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3};
	private final static Integer[] WED_FRI = new Integer[] {0,0,0,0,0,0,0,0,0,0,1,1,1,1,
										1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3};
	private final static Integer[] THU_SAT_SUN = new Integer[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,
										0,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3};
	public Schedule(){	//	constructor
		this.init();
	}
	
	public Schedule(int flag){	// a second constructor that initializes to 0
		for(int day = 0; day < SCHED_DAY_NUM; day++){
			for(int emp = 0; emp < EMP_NUM; emp++){
				sched[emp][day] = flag;
			}
		}
	}
	
	private void init() {	//initialize schedule produces feasible chromosomes-schedules
		for(int day = 0; day < SCHED_DAY_NUM; day++){
			Integer[] shifts = getShifts(day);	//	this function is used to differentiate between days
			for(int emp = 0; emp < EMP_NUM; emp++){
				sched[emp][day] = shifts[emp];
			}
		}
	}
	
	private Integer[] getShifts(int day) {	//	returns a day's schedule as array of Integer
		switch(day) {
			case 0:	//	mon-tue
			case 1:
			case 7:
			case 8:
				Integer[] intArray = MON_TUE;
				List<Integer> intList = Arrays.asList(intArray);	// just shufle the array produce random feasible schedules
				Collections.shuffle(intList);	//	again, too lazy to implement my own method but in this case it's not a big problem
				intList.toArray(intArray);
				return intArray;
			case 2:	//	wed-fri
			case 4:
			case 9:
			case 11:
				intArray = WED_FRI;
				intList = Arrays.asList(intArray);
				Collections.shuffle(intList);
				intList.toArray(intArray);
				return intArray;
			default:	//	thu-sat-sun
				intArray = THU_SAT_SUN;
				intList = Arrays.asList(intArray);
				Collections.shuffle(intList);
				intList.toArray(intArray);
				return intArray;
		}
	}
	
	/* check for feasibility by checking each day separately,
	   not really needed in this implementation as all selection,
	   crossover and mutation functions are designed to hold the
	   hard constraint */
	public boolean isFeasible() {
		int feasDays = 0;
		for(int day = 0; day < SCHED_DAY_NUM; day++){
			int numShifts = getNumofShifts(day);
			int shifts = 0;
			for(int emp = 0; emp < EMP_NUM; emp++){
				if(this.sched[emp][day] != 0)
					shifts++;
			}
			if(shifts == numShifts)
				feasDays++;
		}
		//System.out.println(feasDays);
		if(feasDays == SCHED_DAY_NUM)
			return true;
		return false;
	}
	
	private int getNumofShifts(int day){	// returns number of shifts according to day
		switch(day) {
			case 0:
			case 1:
			case 7:
			case 8:
				return 25;
			case 2:
			case 4:
			case 9:
			case 11:
				return 20;
			default:
				return 15;
		}
	}
	
	@Override
	public String toString(){	// a method to print each schedule
		String aString = "";
		for(int row = 0; row < this.sched.length; row++) {
			for(int col = 0; col < this.sched[row].length; col++) {
				aString += " " + this.sched[row][col];
			}
			aString += "\r\n";
		}
		return aString;
	}
	
	/* setters and getters */
	public int[][] getSchedule(){
		return this.sched;
	}

	public void setSchedule(int[][] s){
		this.sched = s;
	}

	public void setFitness(int fitness){
		this.fitness = fitness;
	}
	
	public int getFitness(){
		return this.fitness;
	}
	
	public void setProbability(int totalFitness, double pp){
		this.probability = pp + (1-(double)this.fitness / totalFitness);
	}
	
	public double getProbability(){
		return this.probability;
	}
	
	public int[] getColumn(int col){
		int[] colArray = new int[EMP_NUM];
		for(int row = 0; row < EMP_NUM; row++){
			colArray[row] = this.sched[row][col];
		}
		return colArray;
	}
	
	public void setColumn(int[] colArray, int col){
		for(int row = 0; row < EMP_NUM; row++){
			this.sched[row][col] = colArray[row];
		}
	}
	
	public int getSD(){return SCHED_DAY_NUM;}
	
	public int getSE(){return EMP_NUM;}
	
	@Override
    public int compareTo(Schedule s) {
        return s.getFitness() - this.getFitness() ; // or whatever property you want to sort
		//return this.getFitness() - s.getFitness();
    }
	
}
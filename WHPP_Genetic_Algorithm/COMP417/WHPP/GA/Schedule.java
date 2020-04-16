package COMP417.WHPP.GA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class Schedule {
	private final static int EMP_NUM = 30;
	private final static int SCHED_DAY_NUM = 14;
	public int sched[][] = new int[EMP_NUM][SCHED_DAY_NUM]; 
	
	private final static Integer[] MON_TUE = new Integer[] {0,0,0,0,0,1,1,1,1,1,1,1,1,1,
										1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3};
	private final static Integer[] WED_FRI = new Integer[] {0,0,0,0,0,0,0,0,0,0,1,1,1,1,
										1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3};
	private final static Integer[] THU_SAT_SUN = new Integer[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,
										0,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3};
	public Schedule(){
		this.init();
	}
	
	private void init() {	//initialize schedule produces feasible chromosomes-schedules
		for(int day = 0; day < SCHED_DAY_NUM; day++){
			Integer[] shifts = getShifts(day);
			for(int emp = 0; emp < EMP_NUM; emp++){
				sched[emp][day] = shifts[emp];
			}
		}
	}
	
	private Integer[] getShifts(int day) {
		switch(day) {
			case 0:
			case 1:
			case 7:
			case 8:
				Integer[] intArray = MON_TUE;
				List<Integer> intList = Arrays.asList(intArray);
				Collections.shuffle(intList);
				intList.toArray(intArray);
				return intArray;
			case 2:
			case 4:
			case 9:
			case 11:
				intArray = WED_FRI;
				intList = Arrays.asList(intArray);
				Collections.shuffle(intList);
				intList.toArray(intArray);
				return intArray;
			default:
				intArray = THU_SAT_SUN;
				intList = Arrays.asList(intArray);
				Collections.shuffle(intList);
				intList.toArray(intArray);
				return intArray;
		}
	}
	
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
	
	private int getNumofShifts(int day){
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
	public String toString(){
		String aString = "";
		for(int row = 0; row < this.sched.length; row++) {
			for(int col = 0; col < this.sched[row].length; col++) {
				aString += " " + this.sched[row][col];
			}
			aString += "\r\n";
		}
		return aString;
	}
	
}
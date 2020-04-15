package COMP417.WHPP.GA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class Schedule {
	private final static int EMP_NUM = 30;
	private final static int SCHED_DAY_NUM = 14;
	public int sched[EMP_NUM][SCHED_DAY_NUM]; 
	
	private final static int[] MON_TUE = {0,0,0,0,0,1,1,1,1,1,1,1,1,1,
										1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3};
	private final static int[] WED_FRI = {0,0,0,0,0,0,0,0,0,0,1,1,1,1,
										1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3};
	private final static int[] THU_SAT_SUN = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,
										0,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3};
	public Schedule(){
		this.init();
	}
	
	private void init() {	//initialize schedule produces feasible chromosomes-schedules
		for(int day = 0; day < SCHED_DAY_NUM; day++){
			int[] shifts = getShifts(day);
			for(int emp = 0; emp < EMP_NUM; emp++){
				sched[emp][day] = shifts[emp];
			}
		}
		if(isFeasible(sched))
			System.out.println("this is feasible");
	}
	
	private int[] getShifts(int day) {
		switch(day) {
			case 0,1,7,8:
				int[] intArray = MON_TUE;
				List<Integer> intList = Arrays.asList(intArray);
				Collections.shuffle(intList);
				intList.toArray(intArray);
				return this.intArray;
			case 2,4,9,11:
				int[] intArray = WED_FRI;
				List<Integer> intList = Arrays.asList(intArray);
				Collections.shuffle(intList);
				intList.toArray(intArray);
				return this.intArray;
			default:
				int[] intArray = THU_SAT_SUN;
				List<Integer> intList = Arrays.asList(intArray);
				Collections.shuffle(intList);
				intList.toArray(intArray);
				return this.intArray;
		}
	}
	
	private boolean isFeasible(int [][] chrom) {
		int feasDays = 0;
		for(int day = 0; day < SCHED_DAY_NUM; day++){
			int numShifts = getNumofShifts(day);
			int shifts = 0;
			for(int emp = 0; emp < EMP_NUM; emp++){
				if(sched[day][emp] != 0)
					shifts++;
			}
			if(shifts == numShifts)
				feasDays++;
		}
		if(feasDays == SCHED_DAY_NUM-1)
			return true;
		return false;
	}
	
}
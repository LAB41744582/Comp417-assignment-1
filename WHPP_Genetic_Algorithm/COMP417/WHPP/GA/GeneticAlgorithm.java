package COMP417.WHPP.GA;

public class GeneticAlgorithm{
	
	public static void main(String args[]){
		Population pop = new Population();
		//pop.printPop();
		while(true){
			if(pop.evaluate()){
				pop.printBest();
				break;
			}
			else{
				pop.select();
				pop.crossbreed();
				pop.mutate();
			}
		}
	}
}
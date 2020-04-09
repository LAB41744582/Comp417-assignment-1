package COMP417.PartA;

import java.util.*;

/**
			INTELLIGENCE LAB
	course		: 	COMP 417 - Artificial Intelligence
	authors		:	A. Vogiatzis, N. Trigkas
	excercise	:	1st Programming
	term 		: 	Spring 2019-2020
	date 		:   March 2020
*/
class Cell {
	private int cost=1;
        private int h,f;
        private int x,y;
        private Cell parent;

	private boolean starting_point;
	private boolean terminal_point;
	private char cell_type = 'L'; // l stands for Land

	Cell (){		
		this.starting_point = false;
		this.terminal_point = false;
	}

	Cell(char cell_type, boolean starting_point, boolean terminal_point, int world_cost){
		if(cell_type!= 'L' && cell_type!= 'W' && cell_type!= 'G'){
			System.out.println("Unknown type of cell. This cell is set to Land!");
			cell_type='L';
			world_cost = 1;
		}
		this.cell_type = cell_type;
		this.starting_point = starting_point;
		this.terminal_point = terminal_point;
		this.cost = world_cost;
	}

	public boolean isWall(){return  (this.cell_type=='W');}
	public boolean isGrass(){return  (this.cell_type=='G');}
	public boolean isLand(){return (this.cell_type=='L');}

	public boolean isStart(){return this.starting_point;}
	public boolean isTerminal(){return this.terminal_point;}

	public int getCost(){return this.cost;}

	public void changeCellType(char cell_type, int world_cost){
		if(cell_type!= 'L' && cell_type!= 'W' && cell_type!= 'G'){
			System.out.println("Unknown type of cell. This cell is set to Land!");
			cell_type='L';
			world_cost = 1;
		}
		this.cell_type = cell_type;
		this.cost = world_cost;
	}

	public char getCellType(){return this.cell_type;}
	
	public void setStartingPoint(boolean sp){this.starting_point=sp;}
	public void setTerminalPoint(boolean sp){this.terminal_point=sp;}
        
        public void calculateHeuristic(int[] goal) {
            this.h = Math.abs(goal[0] - this.x) + Math.abs(goal[1] - this.y);
        }
        
        private void calculateFinalCost() {
            int finalCost = getCost() + getH();
            setF(finalCost);
        }
        
        public int getH() {
            return h;
        }
        
        public void setH(int h) {
            this.h = h;
        }
        
        public void setF(int f) {
            this.f = f;
        }
        
        public void setG(int g) {
            this.cost = g;
        }
        
        public int getF() {
            return f;
        }
        
        public void setNodeData(Cell currentNode, int cost) {
            int gCost = currentNode.getCost() + cost;
            setParent(currentNode);
            setG(gCost);
            calculateFinalCost();
        }
        
        public boolean checkBetterPath(Cell currentNode, int cost) {
            int gCost = currentNode.getCost() + cost;
            if (gCost < getCost()) {
                setNodeData(currentNode, cost);
                return true;
            }
            return false;
        }
        
        public void setParent(Cell parent) {
            this.parent = parent;
        }
        
        public Cell getParent() {
            return parent;
        }
        
        public void setXY(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public int getX(){return this.x;}
        public int getY(){return this.y;}
        
        @Override
        public boolean equals(Object arg0) {
            Cell other = (Cell) arg0;
            return this.getX() == other.getX() && this.getY() == other.getY();
        }
        
        @Override
        public String toString() {
            return "Node [row=" + this.x + ", col=" + this.y + "]";
        }
}

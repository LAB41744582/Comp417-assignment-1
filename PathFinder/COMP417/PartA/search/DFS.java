/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA.search;
import COMP417.PartA.base.*;
import COMP417.PartA.util.*;
import COMP417.PartA.test.*;

import java.util.*;

/**
 *
 * @author I-TECH
 */
public class DFS {
    private Cell root;
    private Grid searchArea;
    private int coordx,coordy;
    private Stack<Cell> stack;
    private Set<Cell> closedSet;
	int path_cost;
	int search_cost;
   
     public DFS(Grid World) {
         this.searchArea = World;
         this.stack = new Stack<Cell>();
         this.closedSet = new HashSet<>();
		 this.path_cost = 0;
		 this.search_cost = 0;
     }
     
     public List<Cell> createTreeDFSWay(){    //	same as bfs but we use stack instead as lifo
        root = getInitialNode();
        root.setXY(this.coordx,this.coordy);

        stack.push(root);
        while(!isEmpty(stack)){  
            Cell dequeuedCell = stack.pop();
			search_cost++;
            closedSet.add(dequeuedCell);
            if(isFinalNode(dequeuedCell)){
                return getPath(dequeuedCell);
            }
            else {
                addAdjacentNodes(dequeuedCell);
            }
        }
        return new ArrayList<Cell>();
    }
     
    private List<Cell> getPath(Cell currentNode) {	//	same as bfs
        List<Cell> path = new ArrayList<Cell>();
        path.add(currentNode);
		path_cost += currentNode.getCost();
        Cell parent;
        while ((parent = currentNode.getParent()) != null) {
			path_cost += parent.getCost();
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }
    
    private void addAdjacentNodes(Cell currentNode) {	//	same as bfs
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }
    
    private void addAdjacentLowerRow(Cell currentNode) {	//	same as bfs
        int row = currentNode.getX();
        int col = currentNode.getY();
        int lowerRow = row + 1;
        if (lowerRow < this.searchArea.getNumOfRows()) {
            checkNode(currentNode, col, lowerRow);
        }
    }

    private void addAdjacentMiddleRow(Cell currentNode) {	//	same as bfs
        int row = currentNode.getX();
        int col = currentNode.getY();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow);
        }
        if (col + 1 < this.searchArea.getNumOfColumns()-1) {
            checkNode(currentNode, col + 1, middleRow);
        }
    }

    private void addAdjacentUpperRow(Cell currentNode) {	//	same as bfs
        int row = currentNode.getX();
        int col = currentNode.getY();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkNode(currentNode, col, upperRow);
        }
    }

    private void checkNode(Cell currentNode, int col, int row) {	//	same as bfs 
        Cell adjacentNode = this.searchArea.getCell(row, col);
        adjacentNode.setXY(row, col);
        if (!adjacentNode.isWall()&& !getClosedSet().contains(adjacentNode)) {
            adjacentNode.setParent(currentNode);
            stack.push(adjacentNode);	//	using the stack we go deep in one direction till we find goal or hit dead-end before exploring other direction
        }
    }
    
	//	setters-getters
    public Cell getInitialNode() {
        int[] start = this.searchArea.getStart();
        setCoords(start);
        setInitialNode(start);
        return root;
    }
    
    public void setCoords(int[] initialNode){
        this.coordx = initialNode[0];
        this.coordy = initialNode[1];
    }

    public void setInitialNode(int[] initialNode) {
        this.root = this.searchArea.getCell(initialNode[0], initialNode[1]);
    }
    
    private boolean isEmpty(Stack<Cell> stack) {
        return stack.empty();
    }
    
    private boolean isFinalNode(Cell currentNode) {
        int[] goal = this.searchArea.getTerminal();
        Cell finalNode = this.searchArea.getCell(goal[0], goal[1]);
        finalNode.setXY(goal[0], goal[1]);
        return currentNode.equals(finalNode);
    }
    
    public Set<Cell> getClosedSet() {
        return closedSet;
    }
     
	public int getPcost(){return this.path_cost;}
	
	public int getScost(){return this.search_cost;}
	
}

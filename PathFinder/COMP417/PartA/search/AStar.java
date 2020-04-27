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
 * A Star Algorithm
 *
 * @author Marcelo Surriabre
 * @version 2.1, 2017-02-23
 */
public class AStar {
    private Grid searchArea;
    private Cell initialNode;
    //private Cell finalNode;
    private PriorityQueue<Cell> openList;
    private Set<Cell> closedSet;
    private int coordx,coordy;
	int path_cost;
	int search_cost;

    public AStar(Grid World) {
        this.searchArea = World;
        this.openList = new PriorityQueue<Cell>(new Comparator<Cell>() { // priority queue is used as fifo
            @Override
            public int compare(Cell node0, Cell node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        this.closedSet = new HashSet<>();	// hash set for faster accesses
		this.path_cost = 0;
		this.search_cost = 0;
    }

    public List<Cell> findPath() {	//	application loop
        initialNode = getInitialNode();	//	first we get the initial node
        initialNode.setXY(this.coordx,this.coordy);	//	we set it's coords using our format and not the original one
        initialNode.calculateHeuristic(getFinalNode());	//	we calculate the heuristic cost (an estimate)
        initialNode.setF(initialNode.getCost()+initialNode.getH());	//	we set it's final cost since it's the first node
        openList.add(initialNode);	// add it to the openlist which acts as our border
        while (!isEmpty(openList)) {	//	while there are still nodes in our border
            Cell currentNode = openList.poll();	// get the first one
			search_cost++;
            //System.out.println("Traverse Cost: "+currentNode.getCost());
            //System.out.println("Heuristic Cost: "+currentNode.getH());
            //System.out.println("Total Cost: "+currentNode.getF());
            //System.out.println("Terrain is: "+currentNode.getCellType());
            //System.out.println("start: "+currentNode.getX()+" "+currentNode.getY());
            //System.out.println(currentNode.getF());
            closedSet.add(currentNode);	// add it to our closed set which means we have visited it
            if (isFinalNode(currentNode)) {	// if we reached the final node
                return getPath(currentNode);	//	return the path
            } else {	//	else continue
                addAdjacentNodes(currentNode);	// explore adjacent Nodes
            }
        }
        return new ArrayList<Cell>();	//	in case we didn't reach the goal return empty path
    }

    private List<Cell> getPath(Cell currentNode) {	// traverse from the goal to the initial forming a path and return it
        List<Cell> path = new ArrayList<Cell>();
		path_cost = currentNode.getCost();
        path.add(currentNode);
        Cell parent;
        while ((parent = currentNode.getParent()) != null) {
			path.add(0, parent);
            currentNode = parent;
        }
		
        return path;
    }

    private void addAdjacentNodes(Cell currentNode) {	//	the node expansion function
        addAdjacentUpperRow(currentNode);	//	search up
        addAdjacentMiddleRow(currentNode);	//	search left-right
        addAdjacentLowerRow(currentNode);	//	search down
    }

    private void addAdjacentLowerRow(Cell currentNode) {	//	expand down
        int row = currentNode.getX();
        int col = currentNode.getY();
        int lowerRow = row + 1;
        if (lowerRow < this.searchArea.getNumOfRows()) {	//	if there's a node down
            checkNode(currentNode, col, lowerRow, getHvCost(lowerRow,col));	//	check it
        }
    }

    private void addAdjacentMiddleRow(Cell currentNode) {	//	same for left-right
        int row = currentNode.getX();
        int col = currentNode.getY();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow, getHvCost(middleRow,col - 1));
        }
        if (col + 1 < this.searchArea.getNumOfColumns()-1) {
            checkNode(currentNode, col + 1, middleRow, getHvCost(middleRow,col + 1));
        }
    }

    private void addAdjacentUpperRow(Cell currentNode) {	//	same for up
        int row = currentNode.getX();
        int col = currentNode.getY();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkNode(currentNode, col, upperRow, getHvCost(upperRow,col));
        }
    }

    private void checkNode(Cell currentNode, int col, int row, int cost) {	//	this function assigns the cost of the nodes
        Cell adjacentNode = this.searchArea.getCell(row, col);	//	get the corresponding cell
        adjacentNode.setXY(row, col);
        //System.out.println(adjacentNode);
        adjacentNode.calculateHeuristic(getFinalNode());	//	calculate heuristic
        if (!adjacentNode.isWall() && !getClosedSet().contains(adjacentNode)) {	//	if it's not wall and we've not visited it
            if (!getOpenList().contains(adjacentNode)) {	//	if it's not on our openlist
                adjacentNode.setNodeData(currentNode, cost, 0);	//	set final cost and parent
                getOpenList().add(adjacentNode);	//	add to our openlist
            } else {	//	if it's already in our openlist
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);	//	check if we have found a better path to it
                if (changed) {	//	if we have
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    //System.out.println("been here");
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Cell currentNode) {	//	check if it's the goal node
        int[] goal = this.searchArea.getTerminal();
        Cell finalNode = this.searchArea.getCell(goal[0], goal[1]);
        finalNode.setXY(goal[0], goal[1]);
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Cell> openList) {	//	check if openlist is empty
        return openList.size() == 0;
    }

    public Cell getInitialNode() {	//	get the starting node
        int[] start = this.searchArea.getStart();
        setCoords(start);
        setInitialNode(start);
        return initialNode;
    }
    
	//some setters-getters
    public void setCoords(int[] initialNode){
        this.coordx = initialNode[0];
        this.coordy = initialNode[1];
    }

    public void setInitialNode(int[] initialNode) {
        this.initialNode = this.searchArea.getCell(initialNode[0], initialNode[1]);
    }

    public int[] getFinalNode() {
        int[] goal = this.searchArea.getTerminal();
        return goal;
    }

    public PriorityQueue<Cell> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Cell> openList) {
        this.openList = openList;
    }

    public Set<Cell> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Cell> closedSet) {
        this.closedSet = closedSet;
    }

    public int getHvCost(int row, int col) {	//	this function gets the stepping cost of the cell, Hv stands for horizontal/vertical not to be confused with heuristic
        //System.out.println(row+" "+col);
        //System.out.println("Node expanded: "+this.searchArea.getCell(row, col)+" Expansion Cost: "+this.searchArea.getCell(row, col).getCost());
        return this.searchArea.getCell(row, col).getCost();
    }
	
	public int getPcost(){return this.path_cost;}
	
	public int getScost(){return this.search_cost;}
	
}

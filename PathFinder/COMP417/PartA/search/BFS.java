/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA.search;
import COMP417.PartA.base.*;
import COMP417.PartA.util.*;
import COMP417.PartA.test.*;

/**
 *
 * @author I-TECH
 */
import java.util.*;

/**
 *
 * Created by aelaf on 5/5/18.
 */

public class BFS {
   private Cell root;
   private Grid searchArea;
   private int coordx,coordy;
   private Queue<Cell> queue;
   private Set<Cell> closedSet;
   int path_cost;
   int search_cost;
   
    public BFS(Grid World) {
        this.searchArea = World;
        this.queue = new LinkedList<Cell>(); //can't instantiate a Queue since abstract, so use LLQueue
        this.closedSet = new HashSet<>();
		this.path_cost = 0;
		this.search_cost = 0;
    }

    public List<Cell> createTreeBFSWay(){    //	simple implementation, basically the same as the 1st exercise set
        root = getInitialNode();	//	we get the initial node
        root.setXY(this.coordx,this.coordy);	//	set it's coords

        queue.add(root);	//	add to queue
        while(!isEmpty(queue)){  //	while queue not empty
            Cell dequeuedCell = queue.poll();	//	check the top node
			search_cost++;
            closedSet.add(dequeuedCell);	//	add it to closed set
            if(isFinalNode(dequeuedCell)){	//	if it's the goal return this path
                return getPath(dequeuedCell);
            }
            else {	//	else explore adjacent Nodes
                addAdjacentNodes(dequeuedCell);
            }
        }
        return new ArrayList<Cell>();
    }
    
    private List<Cell> getPath(Cell currentNode) {	//	same as A* star
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

    private void addAdjacentNodes(Cell currentNode) {	//	same as A*
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Cell currentNode) {	//	same as A*
        int row = currentNode.getX();
        int col = currentNode.getY();
        int lowerRow = row + 1;
        if (lowerRow < this.searchArea.getNumOfRows()) {
            checkNode(currentNode, col, lowerRow);
        }
    }

    private void addAdjacentMiddleRow(Cell currentNode) {	//	same as A*
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

    private void addAdjacentUpperRow(Cell currentNode) {	//	same as A*
        int row = currentNode.getX();
        int col = currentNode.getY();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkNode(currentNode, col, upperRow);
        }
    }

    private void checkNode(Cell currentNode, int col, int row) {	// simpler than A*, as we don't take into consideration better costs but only shorter distances
        Cell adjacentNode = this.searchArea.getCell(row, col);
        adjacentNode.setXY(row, col);
        //System.out.println(adjacentNode);
        if (!adjacentNode.isWall()&& !getClosedSet().contains(adjacentNode)) {
            adjacentNode.setParent(currentNode);
            queue.add(adjacentNode);
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
    
    private boolean isEmpty(Queue<Cell> queue) {
        return queue.size() == 0;
    }
    
    private boolean isFinalNode(Cell currentNode) {
        int[] goal = this.searchArea.getTerminal();
        Cell finalNode = this.searchArea.getCell(goal[0], goal[1]);
        finalNode.setXY(goal[0], goal[1]);
        return currentNode.equals(finalNode);
    }
    
    public Queue<Cell> getQueue() {
        return queue;
    }
    
    public void setQueue(Queue<Cell> queue) {
        this.queue = queue;
    }

    public Set<Cell> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Cell> closedSet) {
        this.closedSet = closedSet;
    }
    
    /* public static void main(String[] args) {
        MyGenericNonBinaryTree<String> myTree = new MyGenericNonBinaryTree<>();
        myTree.createTreeBFSWay();
        //System.out.println(Arrays.toString(myTree.readTreeBFSWay()));
        myTree.printPath();
    } */
	
	public int getPcost(){return this.path_cost;}
	
	public int getScost(){return this.search_cost;}
	
}

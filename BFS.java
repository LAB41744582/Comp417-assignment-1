/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA;

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
   
    public BFS(Grid World) {
        this.searchArea = World;
        this.queue = new LinkedList<Cell>(); //can't instantiate a Queue since abstract, so use LLQueue
        this.closedSet = new HashSet<>();
    }

    public List<Cell> createTreeBFSWay(){    
        root = getInitialNode();
        root.setXY(this.coordx,this.coordy);

        queue.add(root);
        while(!isEmpty(queue)){  
            Cell dequeuedCell = queue.poll();
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
    
    private List<Cell> getPath(Cell currentNode) {
        List<Cell> path = new ArrayList<Cell>();
        path.add(currentNode);
        Cell parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Cell currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Cell currentNode) {
        int row = currentNode.getX();
        int col = currentNode.getY();
        int lowerRow = row + 1;
        if (lowerRow < this.searchArea.getNumOfRows()) {
            checkNode(currentNode, col, lowerRow);
        }
    }

    private void addAdjacentMiddleRow(Cell currentNode) {
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

    private void addAdjacentUpperRow(Cell currentNode) {
        int row = currentNode.getX();
        int col = currentNode.getY();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkNode(currentNode, col, upperRow);
        }
    }

    private void checkNode(Cell currentNode, int col, int row) {
        Cell adjacentNode = this.searchArea.getCell(row, col);
        adjacentNode.setXY(row, col);
        //System.out.println(adjacentNode);
        if (!adjacentNode.isWall()&& !getClosedSet().contains(adjacentNode)) {
            adjacentNode.setParent(currentNode);
            queue.add(adjacentNode);
        }
    }
    
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
}

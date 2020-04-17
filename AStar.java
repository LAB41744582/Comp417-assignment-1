/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA;

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

    public AStar(Grid World) {
        this.searchArea = World;
        this.openList = new PriorityQueue<Cell>(new Comparator<Cell>() {
            @Override
            public int compare(Cell node0, Cell node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        this.closedSet = new HashSet<>();
    }

    public List<Cell> findPath() {
        initialNode = getInitialNode();
        initialNode.setXY(this.coordx,this.coordy);
        initialNode.calculateHeuristic(getFinalNode());
        initialNode.setF(initialNode.getCost()+initialNode.getH());
        openList.add(initialNode);
        while (!isEmpty(openList)) {
            Cell currentNode = openList.poll();
            //System.out.println("Traverse Cost: "+currentNode.getCost());
            //System.out.println("Heuristic Cost: "+currentNode.getH());
            //System.out.println("Total Cost: "+currentNode.getF());
            //System.out.println("Terrain is: "+currentNode.getCellType());
            //System.out.println("start: "+currentNode.getX()+" "+currentNode.getY());
            //System.out.println(currentNode.getF());
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
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
            checkNode(currentNode, col, lowerRow, getHvCost(lowerRow,col));
        }
    }

    private void addAdjacentMiddleRow(Cell currentNode) {
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

    private void addAdjacentUpperRow(Cell currentNode) {
        int row = currentNode.getX();
        int col = currentNode.getY();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            checkNode(currentNode, col, upperRow, getHvCost(upperRow,col));
        }
    }

    private void checkNode(Cell currentNode, int col, int row, int cost) {
        Cell adjacentNode = this.searchArea.getCell(row, col);
        adjacentNode.setXY(row, col);
        //System.out.println(adjacentNode);
        adjacentNode.calculateHeuristic(getFinalNode());
        if (!adjacentNode.isWall() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    //System.out.println("been here");
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Cell currentNode) {
        int[] goal = this.searchArea.getTerminal();
        Cell finalNode = this.searchArea.getCell(goal[0], goal[1]);
        finalNode.setXY(goal[0], goal[1]);
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Cell> openList) {
        return openList.size() == 0;
    }

    public Cell getInitialNode() {
        int[] start = this.searchArea.getStart();
        setCoords(start);
        setInitialNode(start);
        return initialNode;
    }
    
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

    public int getHvCost(int row, int col) {
        //System.out.println(row+" "+col);
        //System.out.println("Node expanded: "+this.searchArea.getCell(row, col)+" Expansion Cost: "+this.searchArea.getCell(row, col).getCost());
        return this.searchArea.getCell(row, col).getCost();
    }
}

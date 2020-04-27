/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA.search;
import COMP417.PartA.base.*;
import COMP417.PartA.util.*;
import COMP417.PartA.test.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// taken from https://github.com/gnufs/aima-java/blob/master/aima-core/src/main/java/aima/core/search/online/LRTAStarAgent.java and adjusted to fit this implementation
/**
 * Artificial Intelligence A Modern Approach 3rdd Edition): Figure 4.24, page 152.<br>
 * <code>
 * function LRTA*-AGENT(s') returns an action
 *   inputs: s', a percept that identifies the current state
 *   persistent: result, a table, indexed by state and action, initially empty
 *               H, a table of cost estimates indexed by state, initially empty
 *               s, a, the previous state and action, initially null
 *           
 *   if GOAL-TEST(s') then return stop
 *   if s' is a new state (not in H) then H[s'] <- h(s')
 *   if s is not null
 *     result[s, a] <- s'
 *     H[s] <-        min LRTA*-COST(s, b, result[s, b], H)
 *             b (element of) ACTIONS(s)
 *   a <- an action b in ACTIONS(s') that minimizes LRTA*-COST(s', b, result[s', b], H)
 *   s <- s'
 *   return a
 *   
 * function LRTA*-COST(s, a, s', H) returns a cost estimate
 *   if s' is undefined then return h(s)
 *   else return c(s, a, s') + H[s']
 * </code>
 * 
 * Figure 4.24 LRTA*-AGENT selects an action according to the value of
 * neighboring states, which are updated as the agent moves about the state
 * space.<br>
 * Note: This algorithm fails to exit if the goal does not exist (e.g. A<->B Goal=X),
 * this could be an issue with the implementation. Comments welcome.
 */

/**
 * @author Ciaran O'Reilly
 * 
 */
/**
 *
 * @author I-TECH
 */
public class LRTA {
    private final Grid searchArea;
    private Cell initialNode;
    private Cell currentNode;
    private Cell finalNode;
    private final HashMap<Cell, Integer> H = new HashMap<Cell, Integer>();
    private final TwoKeyHashMap<Cell, Character, Cell> result = new TwoKeyHashMap<Cell, Character, Cell>();	//	custom implementation of hashmap with two keys
    List<Cell> path = new ArrayList<Cell>();
    private int coordx,coordy;
    char[] actions;
	int path_cost;
	int search_cost;
    
     public LRTA(Grid World){
         this.searchArea = World;
         initialNode = getInitialNode();
         initialNode.setXY(this.coordx,this.coordy);
         finalNode = getFinalNode();
         finalNode.setXY(this.coordx,this.coordy);
		 this.path_cost = 0;
		 this.search_cost = 0;
     }
	 
    public List<Cell> findPath() {	//	same application logic as other methods
        currentNode = initialNode;
        //System.out.println(currentNode);
        //System.out.println(finalNode);
        path.add(currentNode);
		
        char action = 'r';
        char stop = 's';
        while(action != stop){	// while action != 's'
            action = lrtaStar(currentNode,action);	//	get an action from the lrtaStar function
            if(action != stop){	//	if it's not stop
                Cell tempNode = currentNode;	//	we store current node
				//path_cost += currentNode.getCost();
                currentNode = moveTo(currentNode, action);	//	we move to the direction of action
				search_cost++;
				//path_cost += currentNode.getCost();
                currentNode.setParent(tempNode);	//	we set the parent of the resulting node to our previous current
            }
            /*if(currentNode.getParent().getX() == 1 && currentNode.getParent().getY() == 1
                    && currentNode.getX() == 0 && currentNode.getY() == 0){
                System.out.println(currentNode+ " but why?!");
                return path;
            }*/
                
            //System.out.println("Begin!");
            //System.out.println(currentNode.getParent());
            //System.out.println(action);
            //System.out.println(currentNode);
        }
        path.add(currentNode);	//	we add the last current node to the path and return it
        return path;
        //return new ArrayList<Cell>();
    }
    
    private char lrtaStar(Cell currentNode, char a){	//	explores neighbours and returns action with shortest cost
        int min;
        if (isFinalNode(currentNode)) {	//	if it's the last node stop
            return 's';
        }
        else{
            if(!H.containsKey(currentNode)){	//	if the heuristic cost of this node hasn't been calculated before
                int[] goal = this.searchArea.getTerminal();	//	calculate it and add it to the hashmap of heuristic cost values
                currentNode.calculateHeuristic(goal);
                H.put(currentNode,currentNode.getH());
				//path_cost += currentNode.getH();
            }
            if (currentNode.getParent() != null) {	//	if it's not the starting node, calculate cost for it's parent
                // result[s, a] <- s'
                result.put(currentNode.getParent(), a, currentNode);

                // H[s] <- min LRTA*-COST(s, b, result[s, b], H)
                // b (element of) ACTIONS(s)
                min = Integer.MAX_VALUE;
                actions = getActions(currentNode.getParent());	//	a function that get's the available moves from this node
                for (char b : actions) {	//	for every available move
                    int cost = Integer.MAX_VALUE;
                    if(b != '\u0000')	//	if this is not an empty move (meaning we cant move there)
                        cost = lrtaCost(currentNode.getParent(), result.get(currentNode.getParent(),b));	//	calculate cost
                    if (cost < min) {	//	if it's lower than previous minimum, set as minimum
                        min = cost;
                    }
                }
				//if(H.containsKey(currentNode.getParent()))
					//path_cost -= H.get(currentNode.getParent());
                H.put(currentNode.getParent(), min);	//	add	this to the heuristic hashmap
				//path_cost += min;
            }
            // a <- an action b in ACTIONS(s') that minimizes LRTA*-COST(s', b,
            // result[s', b], H)
            min = Integer.MAX_VALUE;
            a = 's';
            actions = getActions(currentNode);
            for (char b : actions) {
                //System.out.println(b);
                int cost = Integer.MAX_VALUE;
                if(b != '\u0000')
                    cost = lrtaCost(currentNode, result.get(currentNode, b));
                if (cost < min) {
                    min = cost;
                    a = b;
                }
                if(cost == min && min < Integer.MAX_VALUE){
                    if (Math.random() > 0.5){	//	if two moves have the same cost then randomly resolve which way we go, performs better than going always the first way we found
                        min = cost;
                        a = b;
                    }
                }
            }
        }
        //System.out.println("Minimum is: "+min);
        return a;
    }
    
    private int lrtaCost(Cell currentNode, Cell nextNode){	//	calculates the cost for a move
        if(nextNode == null){	//	if we haven't yet been to the next node
            int[] goal = this.searchArea.getTerminal();	//	calculare and return heuristic cost
            currentNode.calculateHeuristic(goal);
            return currentNode.getH();
        }	//	else return the true cost (step cost and heuristic)
        return nextNode.getCost()+H.get(nextNode);
    }
    
	//	utility functions and setters-getters similar to previous methods
    private boolean isFinalNode(Cell currentNode) {
        int[] goal = this.searchArea.getTerminal();
        Cell finalNode = this.searchArea.getCell(goal[0], goal[1]);
        finalNode.setXY(goal[0], goal[1]);
        return currentNode.equals(finalNode);
    }
    
    public Cell getInitialNode() {
        int[] start = this.searchArea.getStart();
        setCoords(start);
        setInitialNode(start);
        return initialNode;
    }
    
    public Cell getFinalNode() {
        int[] goal = this.searchArea.getTerminal();
        setCoords(goal);
        setFinalNode(goal);
        return finalNode;
    }
    
    public void setCoords(int[] initialNode){
        this.coordx = initialNode[0];
        this.coordy = initialNode[1];
    }

    public void setInitialNode(int[] initialNode) {
        this.initialNode = this.searchArea.getCell(initialNode[0], initialNode[1]);
    }
    
    public void setFinalNode(int[] finalNode) {
        this.finalNode = this.searchArea.getCell(finalNode[0], finalNode[1]);
    }
    
	//	get available moves
    public char[] getActions(Cell currentNode){
        actions = new char[4];
        if(currentNode.getX()-1 >= 0){
            if(!this.searchArea.getCell(currentNode.getX()-1, currentNode.getY()).isWall()){
                actions[0] = 'u';
                int[] goal = this.searchArea.getTerminal();
                this.searchArea.getCell(currentNode.getX()-1, currentNode.getY()).calculateHeuristic(goal);
                //System.out.println("Up Cost: "+this.searchArea.getCell(currentNode.getX()-1, currentNode.getY()).getH());
            }
                
        }
        if(currentNode.getX()+1 < this.searchArea.getNumOfRows()){
            if(!this.searchArea.getCell(currentNode.getX()+1, currentNode.getY()).isWall()){
                actions[1] = 'd';
                int[] goal = this.searchArea.getTerminal();
                this.searchArea.getCell(currentNode.getX()+1, currentNode.getY()).calculateHeuristic(goal);
                //System.out.println("Down Cost: "+this.searchArea.getCell(currentNode.getX()+1, currentNode.getY()).getH());
            }
                
        }
        if(currentNode.getY()-1 >= 0){
            if(!this.searchArea.getCell(currentNode.getX(), currentNode.getY()-1).isWall()){
                actions[2] = 'l';
                int[] goal = this.searchArea.getTerminal();
                this.searchArea.getCell(currentNode.getX(), currentNode.getY()-1).calculateHeuristic(goal);
                //System.out.println("Left Cost: "+this.searchArea.getCell(currentNode.getX(), currentNode.getY()-1).getH());
            }
                
        }
        if(currentNode.getY()+1 < this.searchArea.getNumOfColumns()){
            if(!this.searchArea.getCell(currentNode.getX(), currentNode.getY()+1).isWall()){
                actions[3] = 'r';
                int[] goal = this.searchArea.getTerminal();
                this.searchArea.getCell(currentNode.getX(), currentNode.getY()+1).calculateHeuristic(goal);
                //System.out.println("Right Cost: "+this.searchArea.getCell(currentNode.getX(), currentNode.getY()+1).getH());
            }
                
        }
        //System.out.println("Node: "+currentNode+" moves available: "+new String(actions));
        return actions;
    }
    
	//	move towards direction 'action'
    private Cell moveTo(Cell currentNode, char action){
        Cell nextNode;
        if(action == 'u'){
            //System.out.println("UP!");
            nextNode = this.searchArea.getCell(currentNode.getX()-1, currentNode.getY());
            nextNode.setXY(currentNode.getX()-1, currentNode.getY());
            //System.out.println((currentNode.getX()-1)+","+currentNode.getY());
            //nextNode.setParent(currentNode);
            path.add(nextNode);
            return nextNode;
        }
        else if(action == 'd'){
            //System.out.println("DOWN!");
            nextNode = this.searchArea.getCell(currentNode.getX()+1, currentNode.getY());
            nextNode.setXY(currentNode.getX()+1, currentNode.getY());
            //System.out.println((currentNode.getX()+1)+","+currentNode.getY());
            //nextNode.setParent(currentNode);
            path.add(nextNode);
            return nextNode;
        }
        else if(action == 'l'){
            //System.out.println("LEFT!");
            nextNode = this.searchArea.getCell(currentNode.getX(), currentNode.getY()-1);
            nextNode.setXY(currentNode.getX(), currentNode.getY()-1);
            //System.out.println(currentNode.getX()+","+(currentNode.getY()-1));
            //nextNode.setParent(currentNode);
            path.add(nextNode);
            return nextNode;
        }
        else{
            //System.out.println("RIGHT!");
            nextNode = this.searchArea.getCell(currentNode.getX(), currentNode.getY()+1);
            nextNode.setXY(currentNode.getX(), currentNode.getY()+1);
            //System.out.println(currentNode.getX()+","+(currentNode.getY()+1));
            //nextNode.setParent(currentNode);
            path.add(nextNode);
            return nextNode;
        }
    }
	
	public int getPcost(){return this.path_cost;}
	
	public int getScost(){return this.search_cost;}
    
}

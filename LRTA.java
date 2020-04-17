/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final TwoKeyHashMap<Cell, Character, Cell> result = new TwoKeyHashMap<Cell, Character, Cell>();
    List<Cell> path = new ArrayList<Cell>();
    private int coordx,coordy;
    char[] actions;
    
     public LRTA(Grid World){
         this.searchArea = World;
         initialNode = getInitialNode();
         initialNode.setXY(this.coordx,this.coordy);
         finalNode = getFinalNode();
         finalNode.setXY(this.coordx,this.coordy);
     }
    public List<Cell> findPath() {
        currentNode = initialNode;
        //System.out.println(currentNode);
        //System.out.println(finalNode);
        path.add(currentNode);
        char action = 'r';
        char stop = 's';
        while(action != stop){
            action = lrtaStar(currentNode,action);
            if(action != stop){
                Cell tempNode = currentNode;
                currentNode = moveTo(currentNode, action);
                currentNode.setParent(tempNode);
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
        path.add(currentNode);
        return path;
        //return new ArrayList<Cell>();
    }
    
    private char lrtaStar(Cell currentNode, char a){
        int min;
        if (isFinalNode(currentNode)) {
            return 's';
        }
        else{
            if(!H.containsKey(currentNode)){
                int[] goal = this.searchArea.getTerminal();
                currentNode.calculateHeuristic(goal);
                H.put(currentNode,currentNode.getH());
            }
            if (currentNode.getParent() != null) {
                // result[s, a] <- s'
                result.put(currentNode.getParent(), a, currentNode);

                // H[s] <- min LRTA*-COST(s, b, result[s, b], H)
                // b (element of) ACTIONS(s)
                min = Integer.MAX_VALUE;
                actions = getActions(currentNode.getParent());
                for (char b : actions) {
                    int cost = Integer.MAX_VALUE;
                    if(b != '\u0000')
                        cost = lrtaCost(currentNode.getParent(), result.get(currentNode.getParent(),b));
                    if (cost < min) {
                        min = cost;
                    }
                }
                H.put(currentNode.getParent(), min);
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
                    if (Math.random() > 0.5){
                        min = cost;
                        a = b;
                    }
                }
            }
        }
        //System.out.println("Minimum is: "+min);
        return a;
    }
    
    private int lrtaCost(Cell currentNode, Cell nextNode){
        if(nextNode == null){
            int[] goal = this.searchArea.getTerminal();
            currentNode.calculateHeuristic(goal);
            return currentNode.getH();
        }
        return nextNode.getCost()+H.get(nextNode);
    }
    
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
    
}

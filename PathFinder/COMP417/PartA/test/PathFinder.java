/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA.test;
import COMP417.PartA.base.*;
import COMP417.PartA.util.*;
import COMP417.PartA.search.*;

import java.util.List;
import java.util.Scanner;
import javax.swing.*;

/**
 *
 * @author I-TECH
 */
public class PathFinder {
    AStar A;
    BFS bfs;
    DFS dfs;
    LRTA lrta;
    List<Cell> pathA,pathB,pathC,pathD;
    int N,M;
    String frame;
    Grid mygrid;

    public PathFinder(Grid mygrid, int N, int M, String frame){
        this.mygrid = mygrid;
        this.N = N;
        this.M = M;
        this.frame = frame;
    }
    
    public void exec(){
        int i,opt = 1;
        int[] steps;
        while(opt !=0){
            Scanner in = new Scanner(System.in);
            System.out.println("Choose:");
			System.out.println("(1) -LRTA*");
			System.out.println("(2) -A*");
			System.out.println("(3) -DFS");
			System.out.println("(4) -BFS");
			System.out.println("(0) -exit");
            opt = in.nextInt();
            switch(opt){
                case 1:
					this.lrta = new LRTA(this.mygrid);
					this.pathD = lrta.findPath();
                    i = 0;
                    steps = new int[pathD.size()];
                    for (Cell node : pathD) {
                        steps[i] = node.getX()*M + node.getY();
                        i++;
                    }
					i = removeDuplicateElements(steps,steps.length);	//	don't count repeated visits in path cost, they are accounted for in search cost
					int rc = 0;
					while(i >= 0){
						rc += mygrid.getCell(steps[i]/M, steps[i]%M).getCost();
						i--;
					}
					int p_cost = rc;
					int s_cost = lrta.getScost();
					System.out.println("LRTA* path cost: "+ p_cost);
					System.out.println("LRTA* search cost: "+ s_cost);
                    GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
					clear_data();
                    continue;
                case 2:
					this.A = new AStar(this.mygrid);
					this.pathA = A.findPath();
                    i = 0;
                    steps = new int[pathA.size()];
                    for (Cell node : pathA) {
                        steps[i] = node.getX()*M + node.getY();
                        i++;
                    }
					p_cost = A.getPcost();
					s_cost = A.getScost();
					System.out.println("A* path cost: "+ p_cost);
					System.out.println("A* search cost: "+ s_cost);
                    GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
					clear_data();
                    continue;
                case 3:
					this.dfs = new DFS(this.mygrid);
					this.pathC = dfs.createTreeDFSWay();
                    i = 0;
                    steps = new int[pathC.size()];
                    for (Cell node : pathC) {
                        steps[i] = node.getX()*M + node.getY();
                        i++;
                    }
					p_cost = dfs.getPcost();
					s_cost = dfs.getScost();
					System.out.println("DFS path cost: "+ p_cost);
					System.out.println("DFS search cost: "+ s_cost);
                    GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
					clear_data();
                    continue;
                case 4:
					this.bfs = new BFS(this.mygrid);
					this.pathB = bfs.createTreeBFSWay();
                    i = 0;
                    steps = new int[pathB.size()];
                    for (Cell node : pathB) {
                        steps[i] = node.getX()*M + node.getY();
                        i++;
                    }
					p_cost = bfs.getPcost();
					s_cost = bfs.getScost();
					System.out.println("BFS path cost: "+ p_cost);
					System.out.println("BFS search cost: "+ s_cost);
                    GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
					clear_data();
                    continue;
                case 0:
                    System.out.println("Exiting!");
                    break;
                default:
                    System.out.println("Not a valid option!");
                    continue;
            }
        }
    }
	
    private void clear_data(){
		for (int i = 0; i<this.N; i++){
			for(int j = 0; j<this.M; j++){
				this.mygrid.getCell(i,j).setNodeData(null,-1,this.mygrid.get_grass_cost());
			}
		}
	}
	
	public static int removeDuplicateElements(int arr[], int n){  
        if (n==0 || n==1){  
            return n;  
        }    
        int j = 0;//for next element  
        for (int i=0; i < n-1; i++){  
            if (arr[i] != arr[i+1]){  
                arr[j++] = arr[i];  
            }  
        }  
        arr[j++] = arr[n-1];  
        return j;  
    }
}

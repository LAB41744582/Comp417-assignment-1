/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA;

import static COMP417.PartA.GridGenerator.VisualizeGrid;
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
        this.A = new AStar(this.mygrid);
        this.bfs = new BFS(this.mygrid);
        this.dfs = new DFS(this.mygrid);
        this.lrta = new LRTA(this.mygrid);
        this.pathA = A.findPath();
        this.pathB = bfs.createTreeBFSWay();
        this.pathC = dfs.createTreeDFSWay();
        this.pathD = lrta.findPath();
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
            opt = in.nextInt();
            switch(opt){
                case 1:
                    i = 0;
                    steps = new int[pathD.size()];
                    for (Cell node : pathD) {
                        steps[i] = node.getX()*M + node.getY();
                        i++;
                    }
                    VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
                    continue;
                case 2:
                    i = 0;
                    steps = new int[pathA.size()];
                    for (Cell node : pathA) {
                        steps[i] = node.getX()*M + node.getY();
                        i++;
                    }
                    VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
                    continue;
                case 3:
                    i = 0;
                    steps = new int[pathC.size()];
                    for (Cell node : pathC) {
                        steps[i] = node.getX()*M + node.getY();
                        i++;
                    }
                    VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
                    continue;
                case 4:
                    i = 0;
                    steps = new int[pathB.size()];
                    for (Cell node : pathB) {
                        steps[i] = node.getX()*M + node.getY();
                        i++;
                    }
                    VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
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
    
}

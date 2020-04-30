package SimplePlot;
//package COMP417.WHPP.GA;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.math.plot.Plot2DPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.math.plot.Plot2DPanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MyPlot {

    private JFrame frame = new JFrame("JMathPlot library in a swing application.");
    private JPanel panel = new JPanel();
	
	private static double[] avg = new double[500];
	private static double[] bst = new double[500];
	private static double[] gen = new double[500];
	
	static int j;
	static int a;
	static int b;
	
    public MyPlot(int flag) {
        //double[] x = new double[]{0, 1, 2, 3, 4, 5};
        //double[] y = new double[]{10, 11, 12, 14, 15, 16};
        Plot2DPanel plot = new Plot2DPanel() {
            @Override
            public Dimension getPreferredSize() {
                //return new Dimension(400, 200);
				return new Dimension(800, 500);
            }
        };
		double[] gen2 = new double[j];
		
		for(int i = 0;i < j;i++){
			gen2[i] = gen[i];
		}
		if(flag == 0)
			plot.addLinePlot("my plot", gen2, avg); // add a line plot to the PlotPanel
		else
			plot.addLinePlot("my plot", gen2, bst); // add a line plot to the PlotPanel
		//plot.setFixedBounds(1,30000, 90000);
		plot.setFixedBounds(0,0, j);
        panel.setLayout(new BorderLayout());
        panel.add(plot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocation(150, 150);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
		
		int[] averages = new int[500];
		int[] best = new int[500];
		int[] generations = new int[500];
		
		InputStream is = null;
		DataInputStream dis = null;
		InputStream is_b = null;
		DataInputStream dis_b = null;
      
		try {
         
			// create file input stream
			is = new FileInputStream("averages.txt");
			is_b = new FileInputStream("best.txt");
         
			// create new data input stream
			dis = new DataInputStream(is);
			dis_b = new DataInputStream(is_b);
			
			int i = 0;
			// available stream to be read
			while(dis.available() > 0) {
         
				// read four bytes from data input, return int
				int k = dis.readInt();
				int l = dis_b.readInt();
				averages[i] = k;
				best[i] = l;
				generations[i] = i;
				i++;
			}
         
		} catch(Exception e) {
			// if any error occurs
			e.printStackTrace();
		} /*finally {
			// releases all system resources from the streams
			if(is!=null)
				is.close();
			if(dis!=null)
				dis.close();
		}*/
		
		//for(j = 0; j < averages.length; j++){
		while(averages[j] > 0){
			avg[j] = averages[j];
			bst[j] = best[j];
			gen[j] = generations[j];
			b = best[j];
			a = averages[j];
			j++;
		}
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyPlot(0);
				new MyPlot(1);
            }
        });
    }
}
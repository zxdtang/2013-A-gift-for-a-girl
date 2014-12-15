package forLXF;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit.CutAction;


public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static boolean isBlocks = false;
	public static Blocks curBlocks;
	public static BlocksState blocksState = BlocksState.READY;
	
	@SuppressWarnings("unused")
	private static MainFrame mainFrame;
	public static StatusBar statusBar;
	public static DrawJPanel drawArea;
	
	public MainFrame(int width, int height) {
		super("Happy Birthday to U");
		
		statusBar = new StatusBar();
		drawArea = new DrawJPanel();
		drawArea.setBackground(Color.BLACK);
		drawArea.setLayout(null);
		
		add(BorderLayout.CENTER, drawArea);
		add(BorderLayout.SOUTH, statusBar);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		//setResizable(false);
		setVisible(true);
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		TimeUnit.SECONDS.sleep(4);
		curBlocks = new Blocks(30, 40, 10, 3, "Are U ready???", new Color(174, 251, 15));

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mainFrame = new MainFrame(800, 600);
			}
		});
		
		TimeUnit.SECONDS.sleep(4);
		blocksState = BlocksState.INIT;
		drawArea.repaint();
		
		//计算那些diamond需要被crash掉
		int left = curBlocks.col/3;
		int right = curBlocks.col-left;
		List<ArrayList<Diamond>> blocks = curBlocks.blocks;
		
		ListIterator<ArrayList<Diamond>> itRow = blocks.listIterator();
		while(itRow.hasNext()) {
			ArrayList<Diamond> row = itRow.next();
		
			if (left > right) {
				break;
			}
			
			for (int i=left; i<=right; i++) {
				row.get(i-1).isCrash = true;
			}
			
			left++;
			right--;
		}
		
		left=curBlocks.col/2-1;
		right=curBlocks.col/2+1;
		Random rand = new Random(47); 
		
		itRow = blocks.listIterator(blocks.size());
		while(itRow.hasPrevious()) {
			if (left<0 | right>curBlocks.col) {
				break;
			}
			
			ArrayList<Diamond> row = itRow.previous();
			
			for (int i=left; i>0; i--) {
				row.get(i-1).isCrash = true;
			}
			for (int i=right; i<= row.size(); i++) {
				row.get(i-1).isCrash = true;
			}
			
			left--;
			right++;
			
		}
		
		/*//输出到文件中
		PrintWriter out = new PrintWriter(
				new BufferedWriter(new FileWriter("./image/blocks.txt")));
	
		for(ArrayList<Diamond> row: curBlocks.blocks) {
			for (Diamond d: row) {
				out.print(d.toString()+'\t');
			}
			out.println("");
		}*/
		
		
		TimeUnit.SECONDS.sleep(3);
		//显示颜色变化
		blocksState = BlocksState.TURNRED;
		java.util.Timer redTimer = new java.util.Timer();
		
		for(int i=0; i<mainFrame.curBlocks.row; i++) {
			redTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					drawArea.repaint();
				}
			}, 0+300*i);
		}
		
		//System.out.println(drawArea.getWidth()+", "+drawArea.getHeight());
		
		TimeUnit.SECONDS.sleep(10);
		blocksState = BlocksState.CRASH;
		for(int i=0; i<mainFrame.drawArea.getHeight()/5; i++) {
			redTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					drawArea.repaint();
				}
			},  0+100*i);
		}
		//System.out.println(drawArea.getWidth()+", "+drawArea.getHeight());
		
		
		TimeUnit.SECONDS.sleep(4);
		blocksState = BlocksState.CAKE;
		for(int i=0; i<curBlocks.startY/20; i++) {
				redTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					drawArea.repaint();
				}
			},  0+100*i);
		}
		//System.out.println(drawArea.getWidth()+", "+drawArea.getHeight());
		
		
		TimeUnit.SECONDS.sleep(3);
		blocksState = BlocksState.CAKEARISE;
		drawArea.repaint();
		
		TimeUnit.SECONDS.sleep(3);
		blocksState = BlocksState.LIGHT;
		drawArea.repaint();
		
		//System.out.println(drawArea.getWidth()+", "+drawArea.getHeight());
	}
}

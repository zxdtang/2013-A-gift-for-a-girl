package forLXF;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

class testPanel extends JPanel {
	static int step =1 ;
	public static boolean isRotate = false;
	public static boolean isMove = false;
	JPanel ziPanel;
	
	List<JPanel> ziPanels = new ArrayList<JPanel>();
	
	public  testPanel() {
		// TODO Auto-generated constructor stub
		for (int i=1; i<10; i++) {
			JPanel tempJPanel = new JPanel();
			tempJPanel.setBackground(Color.YELLOW);
			tempJPanel.setBounds(10+20*i, 10, 15, 15);
			//add(tempJPanel);
		}
		
		
		setLayout(null);
		ziPanel = new JPanel();
		ziPanel.setBackground(Color.YELLOW);
		ziPanel.setBounds(10, 10, 20, 20);
		add(ziPanel);
		
	}
		
	
	@Override
	protected void paintComponent(Graphics g) {
		
		/*if (isRotate) {
			((Graphics2D) g).rotate(Math.toRadians(45),25, ziPanel.getY());
		}*/
		
		if (isMove) {
			((Graphics2D)g).setBackground(Color.GREEN);
			((Graphics2D) g).translate(0, 10);
		}
		super.paintComponent(g);
		
	}
}//public void paintComponent(Graphics g)

public class TestJLable extends JFrame implements ComponentListener{

	private static testPanel colorJPanel;
	public TestJLable() {
		
		colorJPanel = new testPanel();
		colorJPanel.setBackground(Color.RED);
		setLayout(null);
		add(colorJPanel);
		colorJPanel.setBounds(20, 20, 500, 450);
		setBackground(Color.BLACK);
		setSize(400, 400);
		setVisible(true);
		
		
		colorJPanel.addComponentListener(this);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new TestJLable();
			}
		});
		
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Graphics2D g = (Graphics2D) colorJPanel.getGraphics();
				
				g.setBackground(Color.GREEN);
				
				testPanel.isRotate = true;
				//colorJPanel.getParent().repaint();
				colorJPanel.repaint();
			}
		});

try {
	TimeUnit.SECONDS.sleep(1);
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

java.util.Timer redTimer = new java.util.Timer();


colorJPanel.isMove = true;

for(int i=0; i<20; i++) {
	//colorJPanel.isRotate = false;
	colorJPanel.repaint();
	try {
		TimeUnit.MILLISECONDS.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/*redTimer.schedule(new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			testPanel.isRotate = false;
			colorJPanel.isMove = true;
			
			colorJPanel.repaint();
		}
	}, 100);*/
}


		//colorJPanel.setBackground(Color.blue);
	}
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		((JPanel)e.getSource()).repaint();
	}
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}

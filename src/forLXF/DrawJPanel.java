package forLXF;

import java.awt.*;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawJPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static int heartStep=1;
	
	private String[] message = {
			"Are U ready???",
			"How do U do? This is a gift for U...",
			"Wish U happiness from the bottom of my heart",
			"a little magic, must be brave...",
			"Happy birthday to U!!! LXF",
			"It's a true cake, do't U think???",
			"Again, happy birthday to U, Lxf!!!"
	};	
	
	private static List<Image> images = new ArrayList<Image>();
	
	static {
		try {
			images.addAll(Arrays.asList(
					ImageIO.read(new File("./image/lxf0.jpg")),
					ImageIO.read(new File("./image/lxf1.jpg")),
					ImageIO.read(new File("./image/lxf2.jpg")),
					ImageIO.read(new File("./image/lxf3.jpg")),
					ImageIO.read(new File("./image/lxf4.jpg")),
					ImageIO.read(new File("./image/lxf5.jpg")),
					ImageIO.read(new File("./image/lxf6.jpg"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DrawJPanel() {
		
	}
	
	private boolean isInit = false;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		MainFrame.statusBar.changeMessage(message[MainFrame.blocksState.ordinal()]);
		
		Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
			       .4f);
		//((Graphics2D)g).setComposite(c);		
			
		
		g.drawImage(images.get(MainFrame.blocksState.ordinal()), 0, 0, getWidth(), getHeight(), null);
		
		if ((!isInit) && MainFrame.blocksState == BlocksState.INIT) {
			//对blocks中diamond的坐标进行修正,并设置panel，将其添入当前draw area。
			MainFrame.curBlocks.startX = (getWidth() - MainFrame.curBlocks.blocksWidth)/2;
			MainFrame.curBlocks.startY = (getHeight() - MainFrame.curBlocks.blocksHeight)/2;
			
			for (ArrayList<Diamond> panelList: MainFrame.curBlocks.blocks) {
				for (Diamond d: panelList) {
					d.x += MainFrame.curBlocks.startX;
					d.y += MainFrame.curBlocks.startY;
					d.panel.setBounds(d.x, d.y, MainFrame.curBlocks.edge, MainFrame.curBlocks.edge);
					add(d.panel);
				}
			}
			
			isInit = true;
		}
		
		if (MainFrame.blocksState == BlocksState.TURNRED) {
			new ShowBlocks(BlocksState.TURNRED).execute();
		}
		
		if (MainFrame.blocksState == BlocksState.CRASH) {
			new ShowBlocks(BlocksState.CRASH).execute();
		}
		
		if (MainFrame.blocksState == BlocksState.CAKE) {
			
			//g.clearRect(0, MainFrame.curBlocks.startY+MainFrame.curBlocks.blocksHeight+1, getWidth(), getHeight());
			new ShowBlocks(BlocksState.CAKE).execute();
		}
		
		if (MainFrame.blocksState == BlocksState.CAKEARISE||MainFrame.blocksState == BlocksState.LIGHT) {
			Graphics gBase = g.create();
			gBase.setColor(Color.PINK.darker());
			gBase.fillRect(0, getHeight()-80, getWidth(), 80);
			gBase.dispose();
			
			Graphics gTop = g.create();
			gTop.setColor(Color.PINK);
			gTop.fillRect(40, getHeight()-80-80, getWidth()-80, 80);
			gTop.dispose();
		}
		
		if (MainFrame.blocksState == BlocksState.LIGHT) {
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawImage(images.get(MainFrame.blocksState.ordinal()), 0, 0, getWidth(), getHeight(), null);
			((Graphics2D)g).scale(0.5, 0.5);
		}
		
		/*if (MainFrame.blocksState == BlocksState.LIGHT) {
			Graphics gCandle = g.create();
			gCandle.setColor(Color.RED.brighter());
			gCandle.fillRect(70, getHeight()-80-80-100, 20, 100);
			gCandle.fillRect(getWidth()-70, getHeight()-80-80-100, 20, 100);
			gCandle.dispose();
			
			Graphics gLight = g.create();
			gLight.setColor(Color.YELLOW.brighter());
			gLight.fillOval(70, getHeight()-80-80-100-40, 20, 40);
			gLight.fillOval(getWidth()-70, getHeight()-80-80-100-40, 20, 40);
			gLight.dispose();
		}*/
	}
	
	@Override
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		
		Graphics2D gFont = (Graphics2D) g.create();
		Font newFont = gFont.getFont().deriveFont(Font.BOLD, 30f);
		gFont.setFont(newFont);
		gFont.setColor(Color.ORANGE);
		if (MainFrame.blocksState == BlocksState.LIGHT) {
			gFont.scale(2, 2);
			gFont.setColor(Color.RED.brighter());
		}
		
		//if (MainFrame.blocksState == BlocksState.LIGHT) gFont.setColor(Color.BLUE);
		
		FontMetrics fm = g.getFontMetrics();
		
		if (MainFrame.blocksState == BlocksState.READY) {
			gFont.setFont(newFont.deriveFont(60f));
			gFont.drawString(message[MainFrame.blocksState.ordinal()], 200, getHeight()/2);
			gFont.dispose();
			return;
		}
		
		int sY;
		if (MainFrame.blocksState.ordinal() < 4) {
			sY = fm.getDescent()+28;
		} else {
			sY = getHeight() - fm.getAscent();
		}
		
		gFont.drawString(message[MainFrame.blocksState.ordinal()], 20, sY);
		gFont.dispose();
	}
}
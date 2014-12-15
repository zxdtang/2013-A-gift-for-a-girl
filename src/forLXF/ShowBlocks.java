package forLXF;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class ShowBlocks extends SwingWorker<Blocks, String>{
	private BlocksState blocksState;
	
	public ShowBlocks(BlocksState bs) {
		blocksState = bs;
	}
	
	public void setState(BlocksState bs) {
		blocksState = bs;
	}
	
	static private int curRow = 1;
	static private int curCol =1;
	static private boolean isCakeStop = false;
	
	@Override
	protected Blocks doInBackground() throws Exception {
		// TODO Auto-generated method stub
		switch (blocksState) {
		case TURNRED:
			ArrayList<Diamond> rowList = MainFrame.curBlocks.blocks.get(curRow-1);
			
			for(Diamond d: rowList) {
				if (d.isCrash) {
					d.color = Color.WHITE;
					continue;
				}
				d.color = Color.RED;
			}
			
			curRow++;
			break;
		
		case CRASH:
			for (ArrayList<Diamond> rCrash: MainFrame.curBlocks.blocks) {
				for(Diamond d: rCrash) {
					if (d.isCrash) {
						d.color = Color.ORANGE;
						d.y +=5;
					}
				}
			}
			
			break;
			
		case CAKE:
			
			if (isCakeStop) break;
			
			int step = 10;
			for (ArrayList<Diamond> rCake: MainFrame.curBlocks.blocks) {
				for(Diamond d: rCake) {
					if (d.y < step) {
						step = d.y;
						d.y = 0;
						isCakeStop = true;
					}
					d.y -=step;
				}
			}
			
			break;
			
		default:
			break;
		}
		return MainFrame.curBlocks;
	}

	@Override
	protected void done() {
		try {
			Blocks curBlocks = get();
			List<ArrayList<Diamond>> blocks = curBlocks.blocks;
			
			if (MainFrame.blocksState == BlocksState.CRASH) {
				
				//改变颜色
				for(ArrayList<Diamond> row: blocks) {
					for (Diamond d: row) {
						
						if (d.isCrash) {
							d.panel.repaint();
							d.panel.setBounds(d.x, d.y, d.panel.getWidth(), d.panel.getHeight());
						}
					}
				}
				
				MainFrame.drawArea.repaint();
				return;
			}
			
			if (MainFrame.blocksState == BlocksState.CAKE) {
				
				//改变颜色
				for(ArrayList<Diamond> row: blocks) {
					for (Diamond d: row) {
						if (d.isCrash) {
							MainFrame.drawArea.remove(d.panel);
							continue;
						}
						d.panel.setBounds(d.x, d.y, d.panel.getWidth(), d.panel.getHeight());
					}
				}
				
				MainFrame.drawArea.repaint();
				return;
			}

			//改变颜色
			for(ArrayList<Diamond> row: blocks) {
				for (Diamond d: row) {
					d.panel.repaint();
				}
			}
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

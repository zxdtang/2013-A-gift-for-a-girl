package forLXF;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

//С���飬��꣬��ת�ǶȺ���ɫ
class Diamond {
	public int x;
	public int y;

	public double radians;
	public Color color;
	public int scale;

	public boolean isCrash = false;

	public MyJpanel panel = new MyJpanel();

	class MyJpanel extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					.8f);
			((Graphics2D) g).setComposite(c);

			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	public Diamond(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		radians = 0;
		scale = 1;
	}

	/*
	 * public void setPosition(int x, int y) { this.x = x; this.y = y; }
	 * 
	 * public void setRadians(double radians) { this.radians = radians; }
	 * 
	 * public void setColor(Color color) { this.color = color; }
	 * 
	 * public void setScale(int scale) { this.scale = scale; }
	 * 
	 * public void setIsCrash(Boolean isCrash) { this.isCrash = isCrash; }
	 */

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")" + '\t' + "isCrash:" + isCrash;

	}
}

public class Blocks {
	@SuppressWarnings("unused")
	public String message;
	public Color initColor;

	public int row;
	public int col;
	public int gap;
	public int edge;

	public int startX;
	public int startY;

	public int blocksWidth;
	public int blocksHeight;

	public List<ArrayList<Diamond>> blocks = new ArrayList<ArrayList<Diamond>>();

	public Blocks(int row, int col, int edge, int gap, String message,
			Color initColor) {
		this.row = row;
		this.col = col;
		this.edge = edge;
		this.gap = gap;
		this.message = message;
		this.initColor = initColor;

		blocksWidth = col * (edge + gap);
		blocksHeight = row * (edge + gap);

		generateDiamonds();
	}

	public void generateDiamonds() {
		int rowGap = edge + gap;

		/*
		 * int areaWidth = MainFrame.drawArea.getWidth(); int areaHeight =
		 * MainFrame.drawArea.getHeight();
		 */

		/*
		 * //������ܵ�ͼ��Խ�� if ((startX+blocksWidth) > areaWidth) { edge =
		 * 5/6*areaWidth/col; gap = edge/5; } blocksWidth = col*(edge+gap);
		 * blocksHeight = row*(edge+gap);
		 * 
		 * if ((startX+blocksHeight) > areaHeight) { edge = 5/6*areaWidth/col;
		 * gap = edge/5; } blocksWidth = col*(edge+gap); blocksHeight =
		 * row*(edge+gap);
		 */

		/*
		 * startX = (areaWidth - blocksWidth)/2; startY = (areaHeight -
		 * blocksHeight)/2;
		 */

		int rowY = -rowGap;
		for (int r = 0; r < row; r++) {
			rowY += rowGap;
			ArrayList<Diamond> curList = new ArrayList<Diamond>();
			blocks.add(curList);

			Diamond tempDiamond;
			int colX = -rowGap;
			for (int c = 0; c < col; c++) {
				colX += rowGap;
				tempDiamond = new Diamond(colX, rowY, initColor);
				curList.add(tempDiamond);

				// tempDiamond.panel.setBounds(colX, rowY, edge, edge);
			}
		}

		/*
		 * SwingUtilities.invokeLater(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * MainFrame.statusBar.changeMessage(message); for (ArrayList<Diamond>
		 * panelList: blocks) { for (Diamond d: panelList) {
		 * MainFrame.drawArea.add(d.panel); } }
		 * 
		 * MainFrame.drawArea.repaint(); } });
		 */
	}

	static public void main(String... args) throws IOException {
		Blocks testBlocks = new Blocks(30, 20, 15, 5, "test", new Color(174,
				251, 15));
		System.out.println(testBlocks.blocks);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"./image/generateBlocks.txt")));

		for (ArrayList<Diamond> row : testBlocks.blocks) {
			for (Diamond d : row) {
				out.print(d.toString() + '\t');
			}
			out.println("");
		}

		out.close();
		/*
		 * ArrayList<Diamond> testList = new ArrayList<Diamond>(); for (int x=1,
		 * y=1; x<3;x++,y++) { testList.add(new Diamond(x, y, null)); }
		 * 
		 * System.out.println(testList);
		 * 
		 * for (int x=1, y=1; x<3;x++,y++) { testList.get(x-1).isCrash = true; }
		 * System.out.println(testList);
		 * 
		 * for (Diamond diamond : testList) { diamond.isCrash = false; }
		 * System.out.println(testList);
		 */

	}

}

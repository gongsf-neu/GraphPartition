package partitionFennel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import util.WeightVertex;

public class Util {

	public static HashSet<Integer> getUnstable(WeightVertex[] vertices,
			List<WeightVertex> top, List<WeightVertex> remain) {

		int sampleNum = 2000;
		WeightVertex[] samples = new WeightVertex[sampleNum];
		for (int i = 0; i < sampleNum; i++) {
			samples[i] = vertices[i];
		}
		Random random = new Random();
		for (int i = sampleNum; i < vertices.length; i++) {
			int r = Math.abs(random.nextInt(i));
			if (r < sampleNum) {
				samples[r] = vertices[i];
			}
		}
		
		Arrays.sort(samples, new Util().new MyCompartor());
		HashSet<Integer> set = new HashSet<Integer>();
		int index = (int) (0.1 * sampleNum);
		double bound = samples[index].weight;
		System.out.println(samples[0].weight + " " + bound);
		for(WeightVertex v : vertices){
			if(v.weight > bound){
				top.add(v);
				set.add(v.id);
			}else{
				remain.add(v);
			}
		}
		return set;
		/**
		 * 
		 // draw decision graph for (int i = 0; i < sampleNum; i++) { if
		 * (minContribution > samples[i].weight) { minContribution =
		 * samples[i].weight; } if (maxContribution < samples[i].weight) {
		 * maxContribution = samples[i].weight; } }
		 * 
		 * double range = maxContribution - minContribution; int groupNum = 20;
		 * int[] count = new int[groupNum]; double gap = range / groupNum; for
		 * (int i = 0; i < samples.length; i++) { int index = -1; if
		 * (samples[i].weight == maxContribution) { index = groupNum - 1; } else
		 * { index = (int) (samples[i].weight / gap); } count[index]++; }
		 * 
		 * int max = 0; for (int i = 0; i < groupNum; i++) { if (max < count[i])
		 * { max = count[i]; } }
		 * 
		 * = new MyFrame(y, maxy);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * frame.setVisible(true); System.out.println(boundry);
		 */
	}
	
	class MyCompartor implements Comparator<WeightVertex> {
		@Override
		public int compare(WeightVertex v1, WeightVertex v2) {
			return (int) (v2.weight - v1.weight);
		}
	}

	public static class MyFrame extends JFrame {
		public static final String TITLE = "Contribution Distribution";
		public static final int WIDTH = 500;
		public static final int HEIGHT = 300;
		public static int[] y;
		public static int maxy;

		public MyFrame(final int[] y, final int maxy) {
			super();
			this.y = y;
			this.maxy = maxy;
			initFrame();
		}

		private void initFrame() {
			
			setTitle(TITLE);
			setSize(WIDTH, HEIGHT);

			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			setLocationRelativeTo(null);

			MyPanel panel = new MyPanel(this);
			setContentPane(panel);
			addMouseListener(new MouseListener() { 
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					if (e.getButton() == e.BUTTON3) { 
//						boundry = e.getX(); 
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});

			addWindowListener(new WindowAdapter() {

				public void windowClosing(WindowEvent e) {
					super.windowClosing(e);
//					System.out.print(boundry);
				}
			});
		}
	}

	public static class MyPanel extends JPanel {

		private MyFrame frame;

		public MyPanel(MyFrame frame) {
			super();
			this.frame = frame;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawPoint(g);
		}

		private void drawPoint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			BasicStroke bs1 = new BasicStroke(3); 
			g2d.setStroke(bs1);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.RED);
			g2d.drawLine(10, 280, 480, 280);
			g2d.drawLine(20, 290, 20, 30);
			int[] y = new int[frame.y.length];
			System.arraycopy(frame.y, 0, y, 0, y.length);
			int rate = 260 / frame.maxy;
			for (int i = 0; i < y.length; i++) {
				y[i] = y[i] * rate;
				y[i] = 290 - y[i];
			}
			
			int[] x = new int[y.length];
			int xgap = 460 / y.length;
			g2d.setColor(Color.BLACK);
			for (int i = 0; i < y.length; i++) {
				x[i] = i * xgap + 20;
				g2d.drawOval(x[i], y[i], 4, 4);
			}
			// int[] yPoints = new int[] { 100, 120, 80, 100 };
			int nPoints = y.length;
			g2d.setColor(Color.GREEN);
			g2d.drawPolyline(x, y, nPoints);

			// draw coordinate

			g2d.dispose();
		}
	}

}

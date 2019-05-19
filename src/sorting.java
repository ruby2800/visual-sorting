import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import turtle.timerTask;

public class sorting extends JFrame {

	private showpanel visual;
	private Dimension visualview;
	private JButton run, stop, back, reset, exit;
	public boolean Run = true;
	int buttonselect = 0;
	// ユ传
	int count = 0;
	private JLabel pro, al;
	private JComboBox prodemo, aldemo;
	private int proselect, alselect;
	private static final String[] algorithm = { "selection sort", "bubble sort", "quicksort" };
	private static final String[] problem = { "sorting", "other" };
	public static Point points[] = new Point[100];
	public static Point store[] = new Point[100];
	int x, y;
	static int vwidth, vheight;

	public int panelx, panely;

	private int pointcount = 100;
	Random rand = new Random();
	// Timer timer = new Timer();
	selectionSort s = new selectionSort();
	Thread sthread = new Thread(s);

	bubblesort b = new bubblesort();
	Thread bthread = new Thread(b);

	public sorting() {
		super("sorting");

		JPanel selectplace = new JPanel();
		selectplace.setLayout(new GridLayout(1, 4));

		pro = new JLabel("problem");
		selectplace.add(pro);
		prodemo = new JComboBox(problem);
		selectplace.add(prodemo);

		al = new JLabel("algorithm");
		selectplace.add(al);
		aldemo = new JComboBox(algorithm);
		aldemo.addItemListener(new itemhandler());
		selectplace.add(aldemo);

		JPanel buttonplace = new JPanel();
		buttonplace.setLayout(new GridLayout(1, 5));

		// Ω穦sleep
		run = new JButton("run");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonselect = 1;
				Run = true;
				if (alselect == 0) {
					selectionSort s = new selectionSort();
					Thread sthread = new Thread(s);
					sthread.start();
				} else if (alselect == 1) {

					bubblesort b = new bubblesort();
					Thread bthread = new Thread(b);
					bthread.start();
				}

			}
		});
		stop = new JButton("stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (alselect == 0) {
					s.stoprun();
					sthread.interrupt();
				} else if (alselect == 1) {
					b.stoprun();
					bthread.interrupt();
				}

			}
		});

		back = new JButton("back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 璶盢祘氨ゎ
				if (alselect == 0) {
					s.stoprun();
					sthread.interrupt();
				} else if (alselect == 1) {
					b.stoprun();
					bthread.interrupt();
				}

				buttonselect = 2;
				// 璶˙て
				points[count] = store[count];
				visual.repaint();
				count--;

			}
		});
		reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				points = new Point[100];
				buttonselect = 0;
				visual.repaint();

			}
		});
		exit = new JButton("exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonplace.add(run);
		buttonplace.add(stop);
		buttonplace.add(back);
		buttonplace.add(reset);
		buttonplace.add(exit);

		visual = new showpanel();

		visual.setBackground(Color.gray);
		add(visual, BorderLayout.CENTER);

		add(selectplace, BorderLayout.NORTH);
		add(buttonplace, BorderLayout.SOUTH);

	}

	// 传璶穝ざ
	private class itemhandler implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			alselect = aldemo.getSelectedIndex();
			proselect = prodemo.getSelectedIndex();

		}
	}

	public class showpanel extends JPanel {
		public showpanel() {

		}

		// –Ω礶э跑常穦砆ㄓ
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// ъ瞷跌怠へ
			visualview = this.getSize();
			vwidth = (int) (visualview.width);
			vheight = (int) (visualview.height);
			visual.setBounds(5, 25, ((int) vwidth), ((int) vheight));

			// 睹翴
			if (buttonselect == 0) {
				for (int i = 0; i < pointcount; i++) {
					x = rand.nextInt((int) (vwidth)) + 10;
					// 磷砆button籠奔
					y = rand.nextInt((int) (vheight - 35)) + 20;
					Point p = new Point(x, y);
					points[i] = p;
					// 
					store[i] = p;
					if (points[i] != null) {
						g.fillOval(points[i].x, points[i].y, 10, 10);
					}

				}
			}

			if (buttonselect == 1) {
				for (int i = 0; i < points.length; i++) {

					if (points[i] != null) {
						g.fillOval(points[i].x, points[i].y, 10, 10);
					}

				}

			}
			if (buttonselect == 2) {
				for (int i = 0; i < count; i++) {

					if (points[i] != null) {
						g.fillOval(points[i].x, points[i].y, 10, 10);

					}

				}
				for (int j = count; j < store.length; j++) {
					if (store[j] != null) {
						g.fillOval(store[j].x, store[j].y, 10, 10);

					}
				}

			}

		}

	}

	public class bubblesort implements Runnable {
		public bubblesort() {

		}

		public void run() {
			sort();

		}

		public void stoprun() {
			Run = false;
		}

		public void sort() {
			for (int i = 0; i < points.length - 1; i++) {

				// int min = i;
				int width = (int) ((((vwidth / 100) + 1)) * (i + 1));
				int height = (int) ((((vheight / 100) + 1)) * (i + 1));
				Point swapepoint = new Point(width, height);

				// 或㎝量竡??
				for (int j = 0; j < points.length - 1; j++) {
					int first = points[j].x + points[j].y;
					int second = points[j + 1].x + points[j + 1].y;
					// 衡xy竚逼
					if (first > second) {

						if (Run) {
							count = i;
							points[i] = swapaction(points[j], points[j + 1], swapepoint);

						}

					}
				}

			}

			Point special = new Point(800, 400);
			points[99] = special;
			// 癚菇程

		}

		public Point swapaction(Point a, Point b, Point c) {

			try {
				// 何1
				Thread.sleep(20);

				Point temp = a;
				// ヘ玡程
				a = b;
				// ユ传
				b = temp;
				a = c;
				/*
				 * 或ㄓ琌B System.out.println(a); System.out.println(b);
				 * System.out.println(c);
				 */

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			visual.repaint();

			return a;
		}
	}

	public class selectionSort implements Runnable {

		public selectionSort() {

		}

		public void run() {

			sort();

		}

		public void stoprun() {
			Run = false;
		}

		public void sort() {
			for (int i = 0; i < points.length - 1; i++) {

				int min = i;

				for (int j = i + 1; j < points.length; j++) {
					int minall = points[min].x + points[min].y;
					int jall = points[j].x + points[j].y;
					// 衡xy竚逼
					if (minall > jall) {

						min = j; // тヘ玡程

					}
				}

				// ノ4彼5そΑ or 郡场穦繦へ禲
				int width = (int) ((((vwidth / 100) + 1)) * (i + 1));
				int height = (int) ((((vheight / 100) + 1)) * (i + 1));
				Point swapepoint = new Point(width, height);

				// repaint 琌裕edm╰参┮碞穦freeze
				// рwhile传Θif碞и茅
				// 璶衡ユ传材碭翴
				if (Run) {
					count = i;

					points[i] = swapaction(points[i], points[min], swapepoint);

					// visual.repaint();
				}

			}
			Point special = new Point(800, 400);
			points[99] = special;
			// 癚菇程

		}

		public Point swapaction(Point a, Point b, Point c) {

			try {
				// 何1
				Thread.sleep(20);

				Point temp = a;
				// ヘ玡程
				a = b;
				// ユ传
				b = temp;
				a = c;
				/*
				 * 或ㄓ琌B System.out.println(a); System.out.println(b);
				 * System.out.println(c);
				 */

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			visual.repaint();

			return a;
		}

	}

}

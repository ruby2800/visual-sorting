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
	//交換到哪
	int count=0;
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

		//再按一次會先sleep
		run = new JButton("run");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonselect = 1;
				Run = true;
				selectionSort s = new selectionSort();
				Thread sthread = new Thread(s);

				sthread.start();

			}
		});
		stop = new JButton("stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				s.stoprun();
				sthread.interrupt();

			}
		});
		//要存之前的點點八幹那我幹嘛把放排到同一個陣列李幹
		back = new JButton("back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s.stoprun();
				sthread.interrupt();
				buttonselect = 2;
				points[count]=store[count];
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

	private class itemhandler implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			alselect = aldemo.getSelectedIndex();
			proselect = prodemo.getSelectedIndex();

		}
	}

	public class showpanel extends JPanel {
		public showpanel() {

		}

		// 每次畫面改變都會被叫出來
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// 抓現在視窗尺寸
			visualview = this.getSize();
			vwidth = (int) (visualview.width);
			vheight = (int) (visualview.height);
			visual.setBounds(5, 25, ((int) vwidth), ((int) vheight));

			// 印出亂點
			if (buttonselect == 0) {
				for (int i = 0; i < pointcount; i++) {
					x = rand.nextInt((int) (vwidth)) + 10;
					// 避免被button蓋掉
					y = rand.nextInt((int) (vheight - 35)) + 20;
					Point p = new Point(x, y);
					points[i] = p;
					//存
					store[i]=p;
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
				for (int i = 0; i <count; i++) {

					if (points[i] != null) {
						g.fillOval(points[i].x, points[i].y, 10, 10);
						
					}

				}
				for (int j=count;j<store.length;j++){
					if (store[j] != null) {
						g.fillOval(store[j].x, store[j].y, 10, 10);
						
					}
				}

			}

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
					// 算他們的xy位置大小排列
					if (minall > jall) {

						min = j; // 找出目前最小的了

					}
				}

				// 可以用4捨5入的公式 or 他的縣部會隨者尺寸跑
				int width = (int) ((((vwidth / 100) + 1)) * (i + 1));
				int height = (int) ((((vheight / 100) + 1)) * (i + 1));
				Point swapepoint = new Point(width, height);

				// repaint 是啥edm系統，所以叫了就會freeze
				// 把while換成if就好了我矇
				//要算交換到第幾個點
				if (Run) {
					count=i;
					
					points[i] = swapaction(points[i], points[min], swapepoint);
					// visual.repaint();
				}
				

			}
			Point special = new Point(800, 400);
			points[99] = special;
			// 很討厭的最後一個值

		}
		
		
	
		public Point swapaction(Point a, Point b, Point c) {

			try {
				// 先睡1秒
				Thread.sleep(100);

				Point temp = a;
				// 目前最小的
				a = b;
				// 交換
				b = temp;
				a = c;
				/*
				 * 為甚麼印出來的是B System.out.println(a); System.out.println(b);
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

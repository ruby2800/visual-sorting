import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	// 抓目前介面大小
	private Dimension visualview;
	private JButton run, stop, back, reset, exit;
	public boolean Run = true;
	int buttonselect = 0;
	// 交換到第幾個點
	int count = 0;
	private JLabel pro, al;
	private JComboBox prodemo, aldemo;
	// 初始是第一個
	int change = 0;
	private int proselect, alselect;
	private static final String[] algorithm = { "selection sort", "bubble sort", "insertsort", "quick sort" };
	private static final String[] problem = { "sorting", "other" };
	public static Point points[] = new Point[100];
	public static Point store[] = new Point[100];
	int x, y;
	static int vwidth, vheight;

	public int panelx, panely;

	private int pointcount = 100;
	Random rand = new Random();

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
				} else if (alselect == 2) {

					insertsort i = new insertsort();
					Thread ithread = new Thread(i);
					ithread.start();
				} else if (alselect == 3) {

					quicksort q = new quicksort();
					Thread qthread = new Thread(q);
					qthread.start();
				}
				

			}
		});

		stop = new JButton("stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (alselect == 0) {
					// 先停止程式
					s.stoprun();
					// 阻斷執行序
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
				// 要先將程序停止再back
				if (alselect == 0) {
					s.stoprun();
					sthread.interrupt();
				} else if (alselect == 1) {
					b.stoprun();
					bthread.interrupt();
				}

				buttonselect = 2;
				// 要同步化
				points[count] = store[count];
				visual.repaint();
				// back的次數
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

			change = alselect;
			alselect = aldemo.getSelectedIndex();

			// 防止沒按stop就按換演算法
			if (alselect != change) {

				if (alselect == 0) {
					s.stoprun();
					sthread.interrupt();
				} else if (alselect == 1) {
					b.stoprun();
					bthread.interrupt();
				}

				points = new Point[100];
				buttonselect = 0;
				visual.repaint();

			}
			proselect = prodemo.getSelectedIndex();

		}
	}

	public class showpanel extends JPanel {
		// 紀錄交換的point
		private Point mark1;
		private Point mark2;

		public showpanel() {

		}

		// 每次畫面改變都會被叫出來
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			// 抓現在視窗尺寸(但要是換reset才會變)
			visualview = this.getSize();
			vwidth = (int) (visualview.width);
			vheight = (int) (visualview.height);
			visual.setBounds(5, 25, ((int) vwidth), ((int) vheight));

			// 印出初始亂點
			if (buttonselect == 0) {
				for (int i = 0; i < pointcount; i++) {
					x = rand.nextInt((int) (vwidth)) + 10;
					// 避免被button蓋掉
					y = rand.nextInt((int) (vheight - 35)) + 20;
					Point p = new Point(x, y);
					points[i] = p;
					// 存
					store[i] = p;
					if (points[i] != null) {

						g.fillOval(points[i].x, points[i].y, 10, 10);
					}

				}
			}

			// 開始run
			if (buttonselect == 1) {
				for (int i = 0; i < points.length; i++) {

					if (points[i] != null) {
						if (points[i] == mark1) {
							g2.setColor(Color.RED);
						} else if (points[i] == mark2) {
							g2.setColor(Color.GREEN);
						} else {
							g2.setColor(Color.BLACK);
						}

						g.fillOval(points[i].x, points[i].y, 10, 10);
					}

				}

			}

			// 畫back
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

		public synchronized void setmark(Point m1, Point m2) {
			this.mark1 = m1;
			this.mark2 = m2;
		}

	}

	public class insertsort implements Runnable {
		public insertsort() {

		}

		public void run() {
			sort();

		}

		public void stoprun() {
			Run = false;
		}

		int count = 0;

		public void insert(int input, int ok) {
			int okvalue = points[ok].x + points[ok].y;
			int inputvalue = points[input].x + points[input].y;

			while (okvalue > inputvalue) {
				points[ok++] = points[ok];
				ok--;
				okvalue = points[ok].x + points[ok].y;
			}

			int width = (int) ((((vwidth / 100) + 1)) * (count + 1));
			int height = (int) ((((vheight / 100) + 1)) * (count + 1));
			Point swapepoint = new Point(width, height);
			System.out.println(count);
			points[count] = swapaction(points[ok++], points[input], swapepoint);
			count++;
		}

		public void sort() {
			for (int i = 2; i < points.length; i++) {

				insert(i, i--);

			}
			Point special = new Point(800, 400);
			points[99] = special; // 很討厭的最後一個值

		}

		public Point swapaction(Point a, Point b, Point c) {

			try {
				Thread.sleep(100);
				Point temp = a;
				a = b;
				b = temp;
				a = c;

			} catch (InterruptedException e) { // TODO Auto-generated catch
												// block
				e.printStackTrace();
			}
			visual.repaint();

			return a;
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

				int width = (int) ((((vwidth / 100) + 1)) * (i + 1));
				int height = (int) ((((vheight / 100) + 1)) * (i + 1));
				Point swapepoint = new Point(width, height);

			
				for (int j = 0; j < points.length - 1 - i; j++) {
					int first = points[j].x + points[j].y;
					int second = points[j + 1].x + points[j + 1].y;
					// 算他們的xy位置大小排列
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

		}

		public Point swapaction(Point a, Point b, Point c) {
			visual.setmark(a, c);

			try {
				Thread.sleep(15);
				Point temp = a;
				a = b;
				b = temp;
				a = c;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			visual.repaint();

			return a;
		}
	}

	public class quicksort implements Runnable {
		public quicksort() {

		}

		public void run() {
			sort(0, points.length - 1);
		}

		public void stoprun() {
			Run = false;
		}

		int count = 0;

		public void sort(int left, int right) {

			if (left < right) {

				int i = left;
				int j = right;
				int pivot = points[j].x + points[j].y;
				do {
					do {
						i++;
					} while ((points[i].x + points[i].y) < pivot);
					do {
						j--;
					} while ((points[j].x + points[j].y) > pivot);
					if (i < j) {

						int width = (int) ((((vwidth / 100) + 1)) * (count + 1));
						int height = (int) ((((vheight / 100) + 1)) * (count + 1));
						Point swapepoint = new Point(width, height);
						points[count] = swapaction(points[j], points[j + 1], swapepoint);
						count++;
						System.out.println(count);
					}
				} while (i < j);

				if (Run) {
					int width = (int) ((((vwidth / 100) + 1)) * (count + 1));
					int height = (int) ((((vheight / 100) + 1)) * (count + 1));
					Point swapepoint = new Point(width, height);
					points[count] = swapaction(points[j], points[left], swapepoint);
					count++;
					System.out.println(count);
					sort(left, j - 1);
					sort(j + 1, right);

				}

			}

			Point special = new Point(800, 400);
			points[99] = special;

		}

		public Point swapaction(Point a, Point b, Point c) {
			visual.setmark(a, c);

			try {
				Thread.sleep(20);
				Point temp = a;
				a = b;
				b = temp;
				a = c;

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
					// 算他們的xy位置大小排列
					if (minall > jall) {

						min = j; // 找出目前最小的了

					}
				}

				int width = (int) ((((vwidth / 100) + 1)) * (i + 1));
				int height = (int) ((((vheight / 100) + 1)) * (i + 1));
				Point swapepoint = new Point(width, height);

				// repaint 是啥edm系統，所以叫了就會freeze
				// 把while換成if就好了
				// 要算交換到第幾個點
				if (Run) {
					count = i;
					if (i != min) {
						points[i] = swapaction(points[i], points[min], swapepoint);
					}

				}

			}
			Point special = new Point(800, 400);
			points[99] = special;
			// 很討厭的最後一個值

		}

		public Point swapaction(Point a, Point b, Point c) {
			visual.setmark(a, c);
			try {
				// 先睡1秒
				Thread.sleep(100);

				Point temp = a;
				// 目前最小的
				a = b;
				// 交換
				b = temp;
				a = c;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			visual.repaint();

			return a;
		}

	}



}

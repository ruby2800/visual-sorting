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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class sorting extends JFrame {
	
	private JPanel visual;
	private Dimension visualview;
	private JButton run, stop, back, reset, exit;
	private int buttonselect;
	private JLabel pro, al;
	private JComboBox prodemo,aldemo;
	private int proselect,alselect;
	private static final String[] algorithm={"selection sort","bubble sort","quicksort"};
	private static final String[] problem={"sorting","other"};
	public Point points[] = new Point[100];
	private int x,y;
	public int panelx,panely;
	
	private int pointcount=100;
	Random rand = new Random();

	
	public sorting(){
		super("sorting");
		
		JPanel selectplace = new JPanel();
		selectplace.setLayout(new GridLayout(1,4));
		
		pro= new JLabel("problem");
		selectplace.add(pro);
		prodemo= new JComboBox(problem);
		selectplace.add(prodemo);
		
		al=new JLabel("algorithm");
		selectplace.add(al);
		aldemo= new JComboBox(algorithm);
		aldemo.addItemListener(new itemhandler());
		selectplace.add(aldemo);
		
		JPanel buttonplace = new JPanel();
		buttonplace.setLayout(new GridLayout(1,5));
		
		run =new JButton("run");
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				buttonselect=0;
			}
			});
		stop =new JButton("stop");
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				buttonselect=1;
			}
			});
		back =new JButton("back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				buttonselect=2;
			}
			});
		reset =new JButton("reset");
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//重畫
				points= new Point[100];
				repaint();
				
			}
			});
		exit =new JButton("exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			System.exit(0);
			}
			});
		buttonplace.add(run);
		buttonplace.add(stop);
		buttonplace.add(back);
		buttonplace.add(reset);
		buttonplace.add(exit);
		
		
		visual= new showpanel();
		
		
		visual.setBackground(Color.gray);
		add(visual,BorderLayout.CENTER);
		
		add(selectplace,BorderLayout.NORTH);
		add(buttonplace,BorderLayout.SOUTH);
		
		
		
		
	}
	private class itemhandler implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			alselect=aldemo.getSelectedIndex();
			proselect=prodemo.getSelectedIndex();
			
		}
     }
	private class showpanel extends JPanel{
		public showpanel(){
			
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			//抓現在視窗尺寸
			visualview = this.getSize();
			int vwidth = (int) (visualview.width);
			int vheight = (int) (visualview.height);
			visual.setBounds(5, 25, ((int)vwidth),  ((int)vheight));
			
			for (int i = 0; i < pointcount; i++){
				
				x = rand.nextInt((int)(vwidth))+10;
				//避免被button蓋掉
	            y = rand.nextInt((int)(vheight-35))+20;
	            Point p = new Point(x, y);
	            points[i]=p;
				if(points[i]!=null){
					
					g.fillOval(points[i].x, points[i].y,10,10);
				}
			}	
			
			
			}
			
		}
	
	
}

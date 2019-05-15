import java.awt.BorderLayout;
import java.awt.Color;
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
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				buttonselect=2;
			}
			});
		reset =new JButton("reset");
		run.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				buttonselect=3;
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
		visual.setBounds(80, 30, 700, 400);
		panely=visual.getHeight()-100;
		panelx=visual.getWidth()-100;
		System.out.print(panely);
		
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
			for (int i = 0; i < pointcount; i++){
				x = rand.nextInt(panelx)+50;
	            y = rand.nextInt(panely)+50;
	            Point p = new Point(x, y);
	            points[i]=p;
				if(points[i]!=null){
					g.fillOval(points[i].x, points[i].y,10,10);
				}
			}	
			
			switch(buttonselect){
				case 0:
				case 1:
				case 2:
				case 3:
					if(buttonselect==3){
						System.out.print("test");
						repaint();
					for (int i = 0; i < pointcount; i++){
						x = rand.nextInt(600)+50;
			            y = rand.nextInt(300)+50;
			            Point p = new Point(x, y);
			            points[i]=p;
						if(points[i]!=null){
							g.fillOval(points[i].x, points[i].y,10,10);
						}
					}
					
					}
			}
			//repaint();
		}
	}
	
}

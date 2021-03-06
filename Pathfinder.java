//package live.xjames.framew;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Pathfinder extends JFrame implements ActionListener {

	private static String windowName = "Pathfinder";
	private JButton[][] b = new JButton[25][25]; 
	private int[][] ctr = new int[25][25];
	private int[][] endStart = new int[2][2];
	ArrayList<Integer> wallsX = new ArrayList<Integer>();
	ArrayList<Integer> wallsY = new ArrayList<Integer>();
	
	private JButton startButton = new JButton("Start Pathfinder!");
	
	public static void main(String[] args) {
		Pathfinder m = new Pathfinder();
	}
	
	public Pathfinder() {
		super(windowName);
		init();
	}
	
	public void init() {
		super.setBounds(50, 50, 775, 625);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(null);
		initButtons();
		startButton.setBounds(625, 0, 150, 50);
		startButton.addActionListener(this);
		super.getContentPane().add(startButton);
		appear();
	}
	
	public void initButtons() {
		int x = 0;
		int y = 0;
		
		
		for (int row = 0; row < b.length; row++){ 
			for (int col = 0; col < b[0].length; col++) {
				b[row][col] = new JButton();
				b[row][col].setBounds(x, y, 25, 25);
				b[row][col].addActionListener(this);
				b[row][col].setBackground(Color.WHITE);
				ctr[row][col] = 0;
				super.getContentPane().add(b[row][col]);
				x += 25;
			}
			y += 25;
			x = 0;
		}
	}
	
	public void appear() {
		super.setVisible(true);
	}
	
	public void disappear() {
		super.setVisible(false);
	}
	
	public void makeWall(JButton e) {
		e.setBackground(Color.DARK_GRAY);
	}
	
	public void makeStart(JButton e) {
		e.setBackground(Color.GREEN);
	}
	
	public void makeEnd(JButton e) {
		e.setBackground(Color.ORANGE);
	}
	
	public void makeReg(JButton e, int row, int col) {
		e.setBackground(Color.WHITE);
		ctr[row][col] = 0;
	}

	public void calc() {
		long startTime = System.currentTimeMillis();
		int currentButton[][] = new int[1][2];
		currentButton[0][0] = endStart[0][0]; //Y
		currentButton[0][1] = endStart[0][1]; //X
		boolean fin = false;
		while (!fin)
		{
			// TODO : ADD DIAGANOLS
			
		double distanceRight = Math.sqrt(((currentButton[0][1] + 1 - endStart[1][1]) * (currentButton[0][1] + 1 - endStart[1][1])) + ((currentButton[0][0] - endStart[1][0]) * (currentButton[0][0] - endStart[1][0])));
		double distanceLeft =  Math.sqrt(((currentButton[0][1] - 1 - endStart[1][1]) * (currentButton[0][1] - 1 - endStart[1][1])) + ((currentButton[0][0] - endStart[1][0]) * (currentButton[0][0] - endStart[1][0])));
		double distanceTop =  Math.sqrt(((currentButton[0][1] - endStart[1][1]) * (currentButton[0][1] + 1 - endStart[1][1])) + ((currentButton[0][0] + 1 - endStart[1][0]) * (currentButton[0][0] - endStart[1][0])));
		double distanceBottom =  Math.sqrt(((currentButton[0][1] - endStart[1][1]) * (currentButton[0][1] + 1 - endStart[1][1])) + ((currentButton[0][0] - 1 - endStart[1][0]) * (currentButton[0][0] - endStart[1][0])));
		System.out.println("DIS Right : " + distanceRight + "\n" + "DIS Left : " + distanceLeft + "\n" + "DIS Top : " + distanceTop + "\n" + "DIS Bottom : " + distanceBottom + "\n");
		ArrayList<Double> dis = new ArrayList<Double>();
		dis.add(distanceRight);
		dis.add(distanceTop);
		dis.add(distanceBottom);
		dis.add(distanceLeft);
		switch (getLowest(dis, currentButton[0][1], currentButton[0][0])) {
		case 0:
			currentButton[0][1]++;
			break;
		case 1:
			currentButton[0][0]++;
			break;
		case 2:
			currentButton[0][0]--;
			break;
		case 3:
			currentButton[0][1]--;
			break;
		}
		
						
			if (currentButton [0][0] == endStart[1][0] && currentButton [0][1] == endStart[1][1]){
				fin = true;
				continue;
			}
			
		System.out.println(currentButton[0][1] + "," + currentButton[0][0]);
		b[currentButton[0][0]][currentButton[0][1]].setBackground(Color.BLUE);
				}
		System.out.println("FINISHED IN : " + (System.currentTimeMillis() - startTime) + "ms!");
	}
	
	public int getLowest(ArrayList<Double> a, int x, int y) {
		
		for (int z = 0; z < 10; z++) {
			switch (a.indexOf(Collections.min(a))) {
			case 0:
				if (!checkWall(x + 1, y)){
					return 0;
				}else{
					a.set(0, 999.0);
				}
				break;
			case 1:
				if (!checkWall(x, y + 1)){
					return 1;
				}else{
					a.set(1, 999.0);
				}
				break;
			case 2:
				if (!checkWall(x, y - 1)){
					return 2;
				}else{
					a.set(2, 999.0);
				}
				break;
			case 3:
				if (!checkWall(x - 1, y)){
					return 3;
				}else{
					a.set(3, 999.0);
				}
				break;
			}
		}
		return 0;

	}
	
	public boolean checkWall(int x, int y) {
		boolean hold = false;
		
		for (int xs : wallsX)
			for (int ys : wallsY){
				if (xs == x && ys == y)
					hold = true;
			}
		return hold;
	}
	
	public void removeWall(int x, int y) {
		for (int xs = 0; xs < wallsX.size(); xs++) {
			for (int ys = 0; ys < wallsY.size(); ys++) {
				if (wallsX.get(xs) == x && wallsY.get(ys) == y){
					wallsX.remove(xs);
					wallsY.remove(ys);
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(startButton)) {
			for (int xx : wallsX) {
				for (int yy : wallsY){
					System.out.println("Wall Found : " + xx + "," + yy);
				}
			}
			System.out.println("Starting Sim:\nStart : " + endStart[0][0] + " , " + endStart[0][1] + "\nEnd : " + endStart[1][0] + " , " + endStart[1][1]);
			calc();
		}
		else
		for (int row = 0; row < b.length; row++) {
			for (int col = 0; col < b[0].length; col++) {
				if (e.getSource().equals(b[row][col])) {
					ctr[row][col]++;
					switch(ctr[row][col]) {
						case 1:
							makeWall(b[row][col]);
							wallsX.add(col);
							wallsY.add(row);
							break;
						case 2:
							makeStart(b[row][col]);
							endStart[0][0] = row;
							endStart[0][1] = col;
							if (checkWall(col, row)) removeWall(col, row);
							break;
						case 3:
							makeEnd(b[row][col]);
							endStart[1][0] = row;
							endStart[1][1] = col;
							if (checkWall(col, row)) removeWall(col, row);
							break;
						case 4:
							makeReg(b[row][col], row, col);
							if (checkWall(col, row)) removeWall(col, row);
							break;
					}
				}
			}
		}
	}

}

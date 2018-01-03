// draw random squares and write out primes

import java.util.*;

// for just one exception
import java.io.*;

// comfy gui
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// for images
import java.awt.image.*;
import javax.imageio.*;

public class Useful extends JFrame implements ActionListener {
	int WIDTH = 1000;
	int HEIGHT = 1000;

	// prime number stuff
	JTextArea primesfield;
	PrimesCalc pc;

	// random rectangle stuff
	Drawer rectChaos;

	public Useful() {
		getContentPane().setLayout(new GridLayout(2, 1));

		// make square drawer
		rectChaos = new Drawer(this);
		getContentPane().add(rectChaos);
		// run thread
		Thread rt = new Thread(rectChaos);
		rt.start();

		// make text field
		primesfield = new JTextArea();
		primesfield.setLineWrap(true);
		getContentPane().add(primesfield);
		// primes calculator
		pc = new PrimesCalc(this);
		Thread pt = new Thread(pc);
		pt.start();
		
	} // constructor

	public void actionPerformed(ActionEvent e) {
		// NOTHING
	} // eventhandler

	public static void main(String[] args) {
		Useful useful = new Useful();

		// GO GO GUI
		useful.setTitle("Where's MY nobel prize?");
		useful.setSize(useful.WIDTH, useful.HEIGHT);
		useful.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		useful.setVisible(true);
	} // main
} // Useful


// draw random squares
class Drawer extends JPanel implements Runnable {
	Useful parent; // lol
	Random rand;
	BufferedImage steel;

	public Drawer(Useful parent) {
		this.parent = parent;
		rand = new Random();

		try {
			steel = ImageIO.read(new File("steel"));
		} catch (IOException ex) {
			// DO YOU THINK I LIKE ERROR HANDLING
			// NO 
			// NO I DONT
		}

	} // constructor

	public Dimension getPreferredSize() {
		// take up a vertical half of window
		return new Dimension(parent.WIDTH, parent.HEIGHT/2);
	}

	// draw the component
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		randRect();
	} // paintcomponent

	public void randRect() {
		if (isShowing()) {
			// draw random rectangle here
			// random position
			int posx = rand.nextInt(parent.WIDTH);
			int posy = rand.nextInt(parent.HEIGHT/2);
			// random size
			int width = rand.nextInt(100);
			int height = rand.nextInt(100);
			// random color
			Color randomcolor = new Color(rand.nextInt(255), 
																		rand.nextInt(255), 
																		rand.nextInt(255));
			Graphics g = getGraphics();
			g.setColor(randomcolor);
			g.fillRect(posx, posy, width, height);
			if (rand.nextInt(255) == 0) {
				g.drawImage(steel, posx, 0, this);
			}

		}
	}

	public void run() {
		while (true) {
			randRect();
			System.out.println("MAGIC PRINTLINE. DO NOT REMOVE.");
		}
	} // run
}


// prime calculator thread
class PrimesCalc implements Runnable {
	Useful parent; // oxymoron amirite
	JTextArea field;
	
	public PrimesCalc(Useful parent) {
		this.parent = parent;
		this.field = parent.primesfield;
	} // constructor

	// contains the main thread loop
	public void run() {
		boolean thread_active = true;
		int number = 3; // start at known small prime
		while (thread_active) {

			// put primes textfield
			if (isPrime(number)) {
				this.field.setText(this.field.getText()+number+", ");
			}
			number++;

			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				// YOU DUMBASS. I WAS CALCULATING PRIMES FOR YOU.
				// WHAT WILL YOU DO NOW?!
			}

		} // thread loop
			// NEVER QUIT
			// ONLY PRIMES 
	} // run

	private boolean isPrime(int number) {
		// assume prime until factor found
		boolean factorfound = false;

		// search for factors
		int testfactor = 2;
		while (testfactor < number) {
			if (number % testfactor == 0) {
				factorfound = true;
				break;
			} 
			testfactor++;
		}
		
		boolean isPrime = !factorfound;
		return isPrime;

	} // isPrime()
}	// PrimesCalc

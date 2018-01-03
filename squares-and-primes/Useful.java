// draw random squares and write out primes

import java.util.*;

// comfy gui
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Useful extends JFrame implements ActionListener {
	JTextArea primesfield;
	PrimesCalc pc;

	public Useful() {
		getContentPane().setLayout(new GridLayout(2, 1));

		// make text field
		primesfield = new JTextArea();
		getContentPane().add(primesfield);
		// start calculator and thread
		pc = new PrimesCalc(this);
		Thread pt = new Thread(pc);
		pt.start();

		// make square drawer
		
	} // constructor

	public void actionPerformed(ActionEvent e) {
		// NOTHING
	} // eventhandler

	public static void main(String[] args) {
		Useful useful = new Useful();

		// GO GO GUI
		useful.setTitle("Where's MY nobel prize?");
		useful.setSize(500, 500);
		useful.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		useful.setVisible(true);
	} // main
} // Useful

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
		int number = 3;
		while (thread_active) {
			if (isPrime(number)) {
				// put into textfield
				this.field.setText(this.field.getText()+number);
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
		int testfactor = 2;

		// assume prime until factor found
		boolean factorfound = false;
		System.out.println("NUM: "+number);
		while (testfactor < number) {
			if (number % testfactor == 0) {
				factorfound = true;
			} 
			System.out.println("MOD"+testfactor+": "+(number % testfactor));
			testfactor++;
		}
		boolean isPrime = ! factorfound;
		System.out.println("PRIME:"+isPrime);
		return isPrime;
	}

}	// PrimesCalc

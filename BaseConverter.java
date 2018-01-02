// a GUI to convert between binary, decimal, and hexadecimal
// Written by Asbjørn Olling on 2017/01/02
// made for DTU Course 34302 - Java GUI Tutorial 1

import java.util.*;

// lars anbefalede minimumsimports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BaseConverter extends JFrame implements ActionListener {
	private int width = 700;
	private int height = 150;
	HashMap<JButton, String> buttons;
	HashMap<String, JTextField> fields;

	public BaseConverter() { // constructor

		// info for each of the three
		String[] bases = {"bin", "dec", "hex"};

		HashMap<String, String> labels = new HashMap<String, String>();
		labels.put("bin", "Binary");
		labels.put("dec", "Decimal");
		labels.put("hex", "Hexadecimal");

		HashMap<String, String> btntxt = new HashMap<String, String>();
		btntxt.put("bin", "Convert from binary");
		btntxt.put("dec", "Convert from decimal");
		btntxt.put("hex", "Convert from hexadecimal");

		// keep track of buttons and textfields
		buttons = new HashMap<JButton, String>();
		fields = new HashMap<String, JTextField>();

		// content pane
		getContentPane().setLayout(new GridLayout(bases.length, 1, 0, 20));

		// general layout params
		Dimension btnsize = new Dimension(100, 30);
		int txtfldchars = 80;

		// make the three lines
		for ( String base : bases ) {
			// the panel
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 3)); //*/

			// label
			JLabel label = new JLabel(labels.get(base), JLabel.CENTER);

			// text field
			JTextField txt = new JTextField(txtfldchars);
			fields.put(base, txt);

			// button
			JButton btn = new JButton(btntxt.get(base));
			btn.addActionListener(this);
			btn.setMaximumSize(btnsize);
			btn.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttons.put(btn, base);

			// put elements in panel
			panel.add(label);
			panel.add(txt);
			panel.add(btn);

			// put panel i content pane
			getContentPane().add(panel);
		} // loop

	} // constr

	// main event listener
	public void actionPerformed(ActionEvent e) {
		String cstring = e.getSource().getClass().getName();
		System.out.println("EVENT CLASS: " + cstring);

		// buttons
		if (cstring == "javax.swing.JButton") {
			JButton ebtn = (JButton) e.getSource();
			String ebase = buttons.get(ebtn);
			JTextField efield = fields.get(ebase);
			String etext = efield.getText();
			if (ebase == "bin") {
				convertFromBin(etext);
			} else if (ebase == "dec") {
				convertFromDec(etext);
			} else if (ebase == "hex") {
				convertFromHex(etext);
			}
		} else {
			System.out.println("UNKNOWN EVENT");
		}
	} // event listener

	// convert from binary, and paste into fields
	public void convertFromBin(String binstring) {
		// to decimal
		Integer decimal = Integer.parseInt(binstring, 2);
		System.out.println("DECIMAL: " + decimal);
		fields.get("dec").setText(decimal.toString());

		// to hex
		String hexadecimal = Integer.toString(decimal, 16);
		System.out.println("HEXADECIMAL: " + hexadecimal);
		fields.get("hex").setText(hexadecimal);
	}

	// convert from decimal, and paste into fields
	public void convertFromDec(String decstring) {
		int decimal = Integer.parseInt(decstring, 10);

		// to binary
		String binary = Integer.toString(decimal, 2);
		System.out.println("BINARY: " + binary);
		fields.get("bin").setText(binary);

		// to hex
		String hexadecimal = Integer.toString(decimal, 16);
		System.out.println("HEXADECIMAL: " + hexadecimal);
		fields.get("hex").setText(hexadecimal);
	}

	// convert from hexadecimal, and paste into fields
	public void convertFromHex(String hexstring) {
		// to decimal
		Integer decimal = Integer.parseInt(hexstring, 16);
		System.out.println("DECIMAL: " + decimal);
		fields.get("dec").setText(decimal.toString());

		// to binary
		String binary = Integer.toString(decimal, 2);
		System.out.println("BINARY: " + binary);
		fields.get("bin").setText(binary);
	}

	public static void main(String[] args) {
		// main object
		BaseConverter bc = new BaseConverter();

		// lars boilerplate	
		bc.setTitle("WOW SUCH WINDOWS");
		bc.setSize(bc.width, bc.height);
		bc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bc.setVisible(true);
	}
}

// fjoller bare lidt med java swing

import java.util.*;

// lars anbefalede minimumsimports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BaseConverter extends JFrame implements ActionListener {
	private int width = 700;
	private int height = 150;

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

		// content pane
		getContentPane().setLayout(new GridLayout(3, 1, 5, 20)); //*/

		// general layout params
		Dimension btnsize = new Dimension(100, 30);
		Component spacer = Box.createRigidArea(new Dimension(110, 50));
		int txtfldchars = 40;
		Dimension txtfldsize = new Dimension(15, 300);

		for ( String base : bases ) {
			// the panel
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 3)); //*/

			// label
			JLabel label = new JLabel(labels.get(base), JLabel.CENTER);

			// text field
			JTextField txt = new JTextField(txtfldchars);

			// button
			JButton btn = new JButton(btntxt.get(base));
			btn.addActionListener(this);
			btn.setMaximumSize(btnsize);
			btn.setAlignmentX(Component.CENTER_ALIGNMENT);

			// put elements in panel
			panel.add(label);
			panel.add(txt);
			panel.add(btn);

			// put panel i content pane
			getContentPane().add(panel);
		}

	} // constr


	// main event listener
	public void actionPerformed(ActionEvent e) {
		String eventclass = e.getSource().getClass().getName();
		System.out.println("EVENT CLASS: " + eventclass);

	} // event listener

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

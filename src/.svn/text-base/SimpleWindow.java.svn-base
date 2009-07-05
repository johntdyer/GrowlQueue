import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class SimpleWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton button1;
	private JPanel panel;

	public SimpleWindow() {

		final int WINDOW_HEIGHT = 100;
		final int WINDOW_WIDTH = 200;

		setTitle("test");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		button1 = new JButton("Button");
		button1.addActionListener(new ButtonListener());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.add(button1);
add(panel);
		setVisible(true);
		
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {


		}

	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		SimpleWindow myWindows = new SimpleWindow();
	}
}

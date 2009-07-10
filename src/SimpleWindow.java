import java.awt.event.*;

import javax.swing.*;

public class SimpleWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton quitButton;
	private JPanel panel;

	public SimpleWindow() {

		final int WINDOW_HEIGHT = 70;
		final int WINDOW_WIDTH = 70;

		setTitle("Voxeo Support Queue");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		quitButton = new JButton("Quit");
		quitButton.addActionListener(new QuitButtonListener());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.add(quitButton);
		add(panel);
		setVisible(true);

	}

	private class QuitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);

		}
	}

	public static void main(String[] args) {
		SimpleWindow myWindows = new SimpleWindow();
	}
}

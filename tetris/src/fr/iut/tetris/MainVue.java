package fr.iut.tetris;

import javax.swing.*;

public class MainVue extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JPanel currentVue;

	public MainVue() {
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		ImageIcon img = new ImageIcon(Main.class.getResource("/res/icon.png"));
		setIconImage(img.getImage());
	}

	public void setCurrentVue(JPanel p) {
		this.currentVue = p;

		getContentPane().removeAll();
		setContentPane(this.currentVue);
		validate();
		pack();
		setLocationRelativeTo(null);
	}

	public void display() {
		setVisible(true);
	}
	public void undisplay() {
		setVisible(false);
	}
}
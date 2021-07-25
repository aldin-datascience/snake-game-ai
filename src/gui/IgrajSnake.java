package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class IgrajSnake {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame jf = new JFrame("Snake");
		Font myFont = new Font("Arial", Font.BOLD, 25);

		JButton klasicna = new JButton("Klasična Igra - Igra Čovjek");
		JButton aiSa = new JButton("AI (A* Sa Preprekama)");
		JButton aiBez = new JButton("AI (A* Bez Prepreka)");
		JButton aiHam = new JButton("AI (Hamiltonov Ciklus)");

		jf.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		klasicna.setFont(myFont);
		klasicna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MojPanel mojPanel = new MojPanel(30, false, false, true);
				jf.setContentPane(mojPanel);

				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jf.setSize(900, 950);
				jf.setLocationRelativeTo(null);
				jf.setVisible(true);
			}

		});

		jf.add(klasicna, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		aiSa.setFont(myFont);
		aiSa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MojPanel mojPanel = new MojPanel(30, true, false, false);
				jf.setContentPane(mojPanel);

				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jf.setSize(900, 900);
				jf.setLocationRelativeTo(null);
				jf.setVisible(true);
			}

		});
		jf.add(aiSa, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		aiBez.setFont(myFont);
		aiBez.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MojPanel mojPanel = new MojPanel(30, false, false, false);
				jf.setContentPane(mojPanel);

				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jf.setSize(900, 950);
				jf.setLocationRelativeTo(null);
				jf.setVisible(true);

			}

		});
		jf.add(aiBez, c);

		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		aiHam.setFont(myFont);
		aiHam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MojPanel mojPanel = new MojPanel(30, false, true, false);
				jf.setContentPane(mojPanel);

				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jf.setSize(900, 950);
				jf.setLocationRelativeTo(null);
				jf.setVisible(true);
			}

		});
		jf.add(aiHam, c);

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// MojPanel mojPanel = new MojPanel(30);
		jf.setSize(600, 600);
		// jf.setContentPane(mojPanel);
		// jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);

	}

}

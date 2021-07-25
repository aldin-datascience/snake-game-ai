package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Traka extends JPanel {
	private JTextField tekst;
	private JTextField bodovi;
	private Font font;

	/**
	 * Postavlja panel i dodaje tekstualna polja za bodove i tekst
	 */
	private void build() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;

		font = new Font("TimesRoman", Font.BOLD, 30);
		tekst = new JTextField("Bodovi: ");
		tekst.setEditable(false);
		tekst.setHorizontalAlignment(JTextField.CENTER);
		tekst.setFont(font);

		// tekst.setPreferredSize(new Dimension(80,80));
		add(tekst, c);

		bodovi = new JTextField("0");
		bodovi.setEditable(false);
		bodovi.setHorizontalAlignment(JTextField.CENTER);
		bodovi.setFont(font);
		bodovi.setBackground(Color.CYAN);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;

		add(bodovi, c);
	}

	public void dodajBodove(int b) {
		bodovi.setText(String.valueOf(b));
	}

	public void promjeniTekst(String txt) {
		tekst.setText(txt);
	}

	public Traka() {
		build();
	}
}

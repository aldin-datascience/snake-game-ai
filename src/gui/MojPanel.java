package gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import logika.Cvor;
import logika.Pozicija;
import logika.Snake;

public class MojPanel extends JPanel {
	Snake snake;
	JButton[][] tabelaDugmadi;
	JPanel prikazTabele;
	String smjer;
	Timer timer;
	int brojac;
	MojTimerTask mtt;
	MojTimerCovjek mtc;
	LinkedList<String> komande;
	LinkedList<Cvor> rez;
	LinkedList<Cvor> pomRez;
	boolean zauzeto;
	boolean hamiltonov;
	Traka traka;

	public MojPanel(int n, boolean saPreprekama, boolean hamiltonov, boolean igraCovjek) {
		super();
		komande = new LinkedList<String>();
		brojac = 0;
		traka = new Traka();

		if (saPreprekama)
			snake = new Snake(n, true);
		else
			snake = new Snake(n, false);
		if (hamiltonov)
			this.hamiltonov = true;

		zauzeto = false;

		setLayout(new GridBagLayout());
		prikazTabele = new JPanel();

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.05;
		c.weighty = 0.05;
		c.fill = GridBagConstraints.BOTH;
		add(traka, c);
		traka.dodajBodove(snake.vratiBodove());

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		prikazTabele.setLayout(
				new GridLayout(snake.vratiTrenutnoStanje()[0].length, snake.vratiTrenutnoStanje()[0].length));
		tabelaDugmadi = new JButton[snake.vratiTrenutnoStanje()[0].length][snake.vratiTrenutnoStanje()[0].length];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tabelaDugmadi[i][j] = new JButton();
				tabelaDugmadi[i][j].setName(i + " " + j);
				prikazTabele.add(tabelaDugmadi[i][j]);
				tabelaDugmadi[i][j].setBackground(getBoja(snake.vratiTrenutnoStanje()[i][j]));

				tabelaDugmadi[i][j].setBorder(BorderFactory.createEmptyBorder());
				tabelaDugmadi[i][j].addKeyListener(new MojKeyListener());
			}
		}
		add(prikazTabele, c);

		if (!igraCovjek) {
			mtt = new MojTimerTask();
			timer = new Timer(true);
			timer.scheduleAtFixedRate(mtt, 3000, 50); // bilo 5
		} else {
			smjer = "dole";
			mtc = new MojTimerCovjek();
			timer = new Timer(true);
			timer.scheduleAtFixedRate(mtc, 3000, 50); // bilo 5
		}
	}

	private Color getBoja(int boja) {
		if (boja == 0) {
			return Color.BLACK;
		}
		if (boja == 1) {
			return Color.WHITE;
		}
		if (boja == 2) {
			return Color.RED;
		}
		if (boja == 3) {
			return Color.ORANGE;
		}
		return Color.BLACK;
	}

	public void osvjeziStanjeTabele() {
		int n = tabelaDugmadi[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tabelaDugmadi[i][j].setBackground(getBoja(snake.vratiTrenutnoStanje()[i][j]));
			}
		}
		traka.dodajBodove(snake.vratiBodove());
	}

	class MojKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				smjer = "gore";
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				smjer = "dole";
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				smjer = "lijevo";
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				smjer = "desno";
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	class MojTimerTask extends TimerTask {
		@Override
		public void run() {
			Toolkit.getDefaultToolkit().sync();
			/*
			 * Komande izvrsavati redom i izbacivati iz liste Kad postane prazna, pokrenuti
			 * novi aStar sa novom putanjom
			 */
			Cvor p = new Cvor(snake.getGlavaZmije());
			Cvor k = new Cvor(snake.getHrana());

			if (hamiltonov && komande.isEmpty())
				brojac = 0;

			if (hamiltonov && brojac == 0) {
				rez = snake.hamiltonovCiklus(snake.vratiTrenutnoStanje());
				komande = snake.pretvoriUKomande(rez);
				brojac++;
			} else {
				rez = snake.aStar(snake.vratiTrenutnoStanje(), p, k);
				if (rez != null)
					zauzeto = false;
			}

			if ((rez == null && !zauzeto) || snake.opasnaHrana(snake.getHrana())) {
				String potez = snake.najboljiPotez(snake.vratiTrenutnoStanje(), p);
				if (potez == "VD")
					pomRez = snake.vertikalnoDesno(snake.vratiTrenutnoStanje(), p);
				else if (potez == "VL")
					pomRez = snake.vertikalnoLijevo(snake.vratiTrenutnoStanje(), p);
				else if (potez == "HD")
					pomRez = snake.horizontalnoDole(snake.vratiTrenutnoStanje(), p);
				else if (potez == "HG")
					pomRez = snake.horizontalnoGore(snake.vratiTrenutnoStanje(), p);

				komande = snake.pretvoriUKomande(pomRez);
				zauzeto = true;
			} else {
				if (!hamiltonov && rez != null && !snake.opasnaHrana(snake.getHrana())) {
					komande = snake.pretvoriUKomande(rez);
				}
			}

			String zadnja = komande.pop();

			if (zadnja == "gore") {
				snake.pomjeriZmiju("gore");
				osvjeziStanjeTabele();
				Toolkit.getDefaultToolkit().sync();
			} else if (zadnja == "dole") {
				snake.pomjeriZmiju("dole");
				osvjeziStanjeTabele();
				Toolkit.getDefaultToolkit().sync();

			} else if (zadnja == "lijevo") {
				snake.pomjeriZmiju("lijevo");
				osvjeziStanjeTabele();
				Toolkit.getDefaultToolkit().sync();

			} else if (zadnja == "desno") {
				snake.pomjeriZmiju("desno");
				osvjeziStanjeTabele();
				Toolkit.getDefaultToolkit().sync();
			}

			Toolkit.getDefaultToolkit().sync();

			if (snake.krajIgre()) {
				traka.promjeniTekst("Igra završena! Bodovi:");
				timer.cancel();
				timer.purge();
				return;
			}
		}

	}

	class MojTimerCovjek extends TimerTask {

		@Override
		public void run() {

			if (smjer == "gore") {
				snake.pomjeriZmiju("gore");
				osvjeziStanjeTabele();
				Toolkit.getDefaultToolkit().sync();
			} else if (smjer == "dole") {
				snake.pomjeriZmiju("dole");
				osvjeziStanjeTabele();
				Toolkit.getDefaultToolkit().sync();
			} else if (smjer == "lijevo") {
				snake.pomjeriZmiju("lijevo");
				osvjeziStanjeTabele();
				Toolkit.getDefaultToolkit().sync();

			} else if (smjer == "desno") {
				snake.pomjeriZmiju("desno");
				osvjeziStanjeTabele();
				Toolkit.getDefaultToolkit().sync();
			}

			if (snake.krajIgre()) {
				traka.promjeniTekst("Igra završena! Bodovi:");
				timer.cancel();
				timer.purge();
				return;
			}
		}

	}

}

package logika;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Snake {
	private int n; // dimenzija

	private int tabelaStanja[][];
	private boolean kraj;

	private LinkedList<Pozicija> zmija;
	private Pozicija hrana;

	private LinkedList<Pozicija> prepreke;

	private int bodovi;

	public Snake(int n, boolean saPreprekama) {
		this.n = n;
		kraj = false;
		bodovi = 0;

		zmija = new LinkedList<Pozicija>();
		zmija.addLast(new Pozicija(0, 0));
		prepreke = new LinkedList<Pozicija>();
		
		// Generisi prepreke
		if (saPreprekama) {
			prepreke.add(new Pozicija(n / 2, n / 2));
			prepreke.add(new Pozicija(n / 2, (n / 2) + 1));
			prepreke.add(new Pozicija(n / 2, (n / 2) + 2));
			prepreke.add(new Pozicija(n / 2, (n / 2) + 3));

			prepreke.add(new Pozicija(n / 4, n / 4));
			prepreke.add(new Pozicija((n / 4) + 1, n / 4));
			prepreke.add(new Pozicija((n / 4) + 2, n / 4));
			prepreke.add(new Pozicija((n / 4) + 3, n / 4));

			prepreke.add(new Pozicija(n - n / 4, n - n / 4));
			prepreke.add(new Pozicija((n - n / 4) + 1, n - n / 4));
			prepreke.add(new Pozicija((n - n / 4) + 2, n - n / 4));
			prepreke.add(new Pozicija((n - n / 4) + 3, n - n / 4));

			prepreke.add(new Pozicija(n - n / 5, n / 4));
			prepreke.add(new Pozicija((n - n / 5), (n / 4) + 1));
			prepreke.add(new Pozicija((n - n / 5), (n / 4) + 2));
			prepreke.add(new Pozicija((n - n / 5), (n / 4) + 3));

			prepreke.add(new Pozicija(n / 5, n - n / 4));
			prepreke.add(new Pozicija((n / 5), (n - n / 4) + 1));
			prepreke.add(new Pozicija((n / 5), (n - n / 4) + 2));
			prepreke.add(new Pozicija((n / 5), (n - n / 4) + 3));
		}

		hrana = new Pozicija(getRandom(0, n), getRandom(0, n));

		tabelaStanja = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == zmija.getLast().getX() && j == zmija.getLast().getY()) {
					tabelaStanja[i][j] = 1;
				} else if (i == hrana.getX() && j == hrana.getY()) {
					tabelaStanja[i][j] = 2;
				} else if (postojiUListiPrepreka(i, j)) {
					tabelaStanja[i][j] = 3;
				} else {
					tabelaStanja[i][j] = 0;
				}
			}
		}
	}

	public Pozicija getHrana() {
		return hrana;
	}

	public Pozicija getGlavaZmije() {
		return zmija.get(zmija.size() - 1);
	}

	public boolean krajIgre() {
		if (kraj) {
			return true;
		}
		return false;
	}

	public int vratiBodove() {
		return bodovi;
	}

	private boolean zauzeto(int x, int y) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tabelaStanja[x][y] != 0) {
					return true;
				}
			}
		}
		return false;
	}

	private int getRandom(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	public int[][] vratiTrenutnoStanje() {
		return tabelaStanja;
	}

	private boolean postojiUListi(int x, int y) {
		for (int i = 0; i < zmija.size(); i++) {
			if (x == zmija.get(i).getX() && y == zmija.get(i).getY()) {
				return true;
			}
		}
		return false;
	}

	private boolean postojiUListiPrepreka(int x, int y) {
		for (int i = 0; i < prepreke.size(); i++) {
			if (x == prepreke.get(i).getX() && y == prepreke.get(i).getY()) {
				return true;
			}
		}
		return false;
	}

	private void promjeniStanje() {
		for (int i = 0; i < zmija.size(); i++) {
			tabelaStanja[zmija.get(i).getX()][zmija.get(i).getY()] = 1;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (postojiUListi(i, j)) {
					continue;
				} else if (i == hrana.getX() && j == hrana.getY()) {
					tabelaStanja[i][j] = 2;
				} else if (postojiUListiPrepreka(i, j)) {
					tabelaStanja[i][j] = 3;
				} else {
					tabelaStanja[i][j] = 0;
				}
			}
		}
	}

	public void pomjeriZmiju(String smjer) {
		if (smjer.equals("gore")) {
			int x = zmija.getLast().getX();
			int y = zmija.getLast().getY();

			if (x - 1 < 0 || postojiUListi(x - 1, y) || postojiUListiPrepreka(x - 1, y)) {
				kraj = true;
				return;
			}

			// Ako je igrac pokupio hranu
			if (x - 1 == hrana.getX() && y == hrana.getY()) {
				zmija.addLast(new Pozicija(x - 1, y));
				while (true) {
					int i = getRandom(0, n);
					int j = getRandom(0, n);
					if (zauzeto(i, j)) {
						continue;
					}
					hrana = new Pozicija(i, j);
					break;
				}
				promjeniStanje();
				bodovi++;
				return;
			}

			zmija.removeFirst();
			zmija.addLast(new Pozicija(x - 1, y));

			promjeniStanje();
		}

		if (smjer.equals("dole")) {
			int x = zmija.getLast().getX();
			int y = zmija.getLast().getY();

			if (x + 1 >= n || postojiUListi(x + 1, y) || postojiUListiPrepreka(x + 1, y)) {
				kraj = true;
				return;
			}

			// Ako je igrac pokupio hranu
			if (x + 1 == hrana.getX() && y == hrana.getY()) {
				zmija.addLast(new Pozicija(x + 1, y));
				while (true) {
					int i = getRandom(0, n);
					int j = getRandom(0, n);
					if (zauzeto(i, j)) {
						continue;
					}
					hrana = new Pozicija(i, j);
					break;
				}
				promjeniStanje();
				bodovi++;
				return;
			}

			zmija.removeFirst();
			zmija.addLast(new Pozicija(x + 1, y));
			promjeniStanje();
		}

		if (smjer.equals("lijevo")) {
			int x = zmija.getLast().getX();
			int y = zmija.getLast().getY();

			if (y - 1 < 0 || postojiUListi(x, y - 1) || postojiUListiPrepreka(x, y - 1)) {
				kraj = true;
				return;
			}

			// Ako je igrac pokupio hranu
			if (x == hrana.getX() && y - 1 == hrana.getY()) {
				zmija.addLast(new Pozicija(x, y - 1));
				while (true) {
					int i = getRandom(0, n);
					int j = getRandom(0, n);
					if (zauzeto(i, j)) {
						continue;
					}
					hrana = new Pozicija(i, j);
					break;
				}
				promjeniStanje();
				bodovi++;
				return;
			}

			zmija.removeFirst();
			zmija.addLast(new Pozicija(x, y - 1));
			promjeniStanje();
		}

		if (smjer.equals("desno")) {
			int x = zmija.getLast().getX();
			int y = zmija.getLast().getY();

			if (y + 1 >= n || postojiUListi(x, y + 1) || postojiUListiPrepreka(x, y + 1)) {
				kraj = true;
				return;
			}

			// Ako je igrac pokupio hranu
			if (x == hrana.getX() && y + 1 == hrana.getY()) {

				zmija.addLast(new Pozicija(x, y + 1));
				while (true) {
					int i = getRandom(0, n);
					int j = getRandom(0, n);
					if (zauzeto(i, j)) {
						continue;
					}
					hrana = new Pozicija(i, j);
					break;
				}
				promjeniStanje();
				bodovi++;
				return;
			}

			zmija.removeFirst();
			zmija.addLast(new Pozicija(x, y + 1));
			promjeniStanje();
		}
	}

	public boolean nalaziSe(Cvor cvor, LinkedList<Cvor> cvorovi) {
		for (int i = 0; i < cvorovi.size(); i++) {
			if (cvor.getX() == cvorovi.get(i).getX() && cvor.getY() == cvorovi.get(i).getY()) {
				return true;
			}
		}
		return false;
	}

	// A* algoritam za pronalazenje najkraceg puta od glave zmije do hrane,
	// pazeci na prepreke

	public LinkedList<Cvor> aStar(int[][] stanje, Cvor pocetak, Cvor kraj) {
		Cvor pocetniCvor = new Cvor(null, pocetak, pocetak.getKoordinate());
		Cvor krajnjiCvor = new Cvor(null, kraj, kraj.getKoordinate());

		LinkedList<Cvor> otvorenaLista = new LinkedList<Cvor>();
		LinkedList<Cvor> zatvorenaLista = new LinkedList<Cvor>();

		otvorenaLista.add(pocetniCvor);

		int brojac = 0;
		// Sve dok ne dođemo do krajnjeg cvora
		while (otvorenaLista.size() > 0) {
			brojac++;
			if (brojac > 100)
				return null;

			Cvor trenutniCvor = otvorenaLista.get(0);
			int trenutniIndeks = 0;

			for (int i = 0; i < otvorenaLista.size(); i++) {
				if (otvorenaLista.get(i).getF() < trenutniCvor.getF()) {
					trenutniCvor = otvorenaLista.get(i);
					trenutniIndeks = i;
				}
			}

			// Izbaci trenutni iz otvorene i dodaj u zatvorenu listu
			otvorenaLista.remove(trenutniIndeks);
			zatvorenaLista.add(trenutniCvor);

			// Dosli do cilja
			if (trenutniCvor.getPozicija().getX() == krajnjiCvor.getPozicija().getX()
					&& trenutniCvor.getPozicija().getY() == krajnjiCvor.getPozicija().getY()) {
				LinkedList<Cvor> put = new LinkedList<Cvor>();
				Cvor trenutni = trenutniCvor;
				while (trenutni != null) {
					put.push(trenutni.getPozicija());
					trenutni = trenutni.getRoditelj();
				}
				// Collections.reverse(put);
				// if (put == null)
					// System.out.println("Put je null");
				return put;
			}

			// Generisi djecu
			LinkedList<Cvor> djeca = new LinkedList<Cvor>();
			LinkedList<Pozicija> pozicije = new LinkedList<Pozicija>();
			Pozicija p1 = new Pozicija(0, -1);
			Pozicija p2 = new Pozicija(0, 1);
			Pozicija p3 = new Pozicija(-1, 0);
			Pozicija p4 = new Pozicija(1, 0);

			pozicije.add(p1);
			pozicije.add(p2);
			pozicije.add(p3);
			pozicije.add(p4);

			for (Pozicija p : pozicije) {

				// Uzmi poziciju
				Pozicija pozicijaCvora = new Pozicija(trenutniCvor.getX() + p.getX(), trenutniCvor.getY() + p.getY());

				// Ako pozicija nije validna
				if (pozicijaCvora.getX() > n - 1 || pozicijaCvora.getX() < 0 || pozicijaCvora.getY() > n - 1
						|| pozicijaCvora.getY() < 0) {
					continue;
				}

				Cvor pomCvor = new Cvor(pozicijaCvora);
				Cvor noviCvor = new Cvor(trenutniCvor, pomCvor, pomCvor.getKoordinate());
				if (nalaziSe(noviCvor, zatvorenaLista)) {
					continue;
				}

				// Ako ima prepreka
				if (tabelaStanja[pozicijaCvora.getX()][pozicijaCvora.getY()] != 0
						&& tabelaStanja[pozicijaCvora.getX()][pozicijaCvora.getY()] != 2) {
					continue;
				}
				// pomCvor = new Cvor(pozicijaCvora);
				// noviCvor = new Cvor(trenutniCvor, pomCvor, pomCvor.getKoordinate());

				djeca.add(noviCvor);
			}

			for (Cvor d : djeca) {
				for (Cvor zd : zatvorenaLista) {
					if (d.getX() == zd.getX() && d.getY() == zd.getY())
						continue;
				}

				d.setG(trenutniCvor.getG() + 1);
				d.setH(Math.pow(d.getX() - krajnjiCvor.getX(), 2) + Math.pow(d.getY() - krajnjiCvor.getY(), 2));
				d.setF(d.getG() + d.getH());

				// Dijete vec u otvorenoj listi
				for (Cvor od : otvorenaLista) {
					if (d.getX() == od.getX() && d.getY() == od.getY() && d.getG() > od.getG())
						continue;
				}

				otvorenaLista.add(d);
			}

		}
		return null; // Formalno
	}

	public LinkedList<String> pretvoriUKomande(LinkedList<Cvor> put) {
		LinkedList<String> komande = new LinkedList<String>();
		for (int i = 1; i < put.size(); i++) {
			if (put.get(i).getX() == put.get(i - 1).getX() + 1) {
				komande.addLast("dole");
			} else if (put.get(i).getX() == put.get(i - 1).getX() - 1) {
				komande.addLast("gore");
			} else if (put.get(i).getY() == put.get(i - 1).getY() + 1) {
				komande.addLast("desno");
			} else if (put.get(i).getY() == put.get(i - 1).getY() - 1) {
				komande.addLast("lijevo");
			}
		}
		return komande;
	}

	// Algoritam koji prati hamiltonov ciklus
	public LinkedList<Cvor> hamiltonovCiklus(int[][] stanje) {
		LinkedList<Cvor> put = new LinkedList<Cvor>();

		int i, j;
		i = 0;
		j = 0;

		String smjer = "dole";

		while (true) {
			put.add(new Cvor(new Pozicija(i, j)));

			if (i == 1 && j == n - 1) {
				i = 0;
				for (int k = 0; k < n; k++) {
					put.add(new Cvor(new Pozicija(i, j)));
					j--;
				}
				break;
			}

			if (smjer == "dole") {
				i++;
			}

			if (smjer == "gore") {
				i--;
			}

			if (i == n) {
				i = n - 1;
				j++;
				smjer = "gore";
			}

			if (i == 0) {
				i = 1;
				j++;
				smjer = "dole";
			}
		}
		return put;
	}

	// Algoritam za kretanje slobodnim mjestima
	public LinkedList<Cvor> vertikalnoDesno(int[][] stanje, Cvor pocetak) {
		int i = pocetak.getX();
		int j = pocetak.getY();
		String smjer = "dole";
		LinkedList<Cvor> put = new LinkedList<Cvor>();
		put.add(pocetak);

		while (true) {
			if (smjer == "dole") {
				if (i + 1 < n && stanje[i + 1][j] != 1 && stanje[i + 1][j] != 3) {
					i++;
					put.add(new Cvor(new Pozicija(i, j)));
				} else {
					if (j + 1 == n)
						break;
					j++;
					put.add(new Cvor(new Pozicija(i, j)));
					smjer = "gore";
				}
			} else if (smjer == "gore") {
				if (i - 1 >= 0 && stanje[i - 1][j] != 1 && stanje[i - 1][j] != 3) {
					i--;
					put.add(new Cvor(new Pozicija(i, j)));
				} else {
					if (j + 1 == n)
						break;
					j++;
					put.add(new Cvor(new Pozicija(i, j)));
					smjer = "dole";
				}
			}
		}
		return put;
	}

	public LinkedList<Cvor> vertikalnoLijevo(int[][] stanje, Cvor pocetak) {
		int i = pocetak.getX();
		int j = pocetak.getY();
		String smjer = "dole";
		LinkedList<Cvor> put = new LinkedList<Cvor>();
		put.add(pocetak);

		// Dodati da pazi kad ulazi u polje okruzeno sa druga tri polja
		while (true) {
			if (smjer == "dole") {
				if (i + 1 < n && stanje[i + 1][j] != 1 && stanje[i + 1][j] != 3) {
					i++;
					put.add(new Cvor(new Pozicija(i, j)));
				} else {
					if (j - 1 == -1)
						break;
					j--;
					put.add(new Cvor(new Pozicija(i, j)));
					smjer = "gore";
				}
			} else if (smjer == "gore") {
				if (i - 1 >= 0 && stanje[i - 1][j] != 1 && stanje[i - 1][j] != 3) {
					i--;
					put.add(new Cvor(new Pozicija(i, j)));
				} else {
					if (j - 1 == 1)
						break;
					j--;
					put.add(new Cvor(new Pozicija(i, j)));
					smjer = "dole";
				}
			}
		}
		return put;
	}

	public LinkedList<Cvor> horizontalnoDole(int[][] stanje, Cvor pocetak) {
		int i = pocetak.getX();
		int j = pocetak.getY();
		String smjer = "desno";
		LinkedList<Cvor> put = new LinkedList<Cvor>();
		put.add(pocetak);

		while (true) {
			if (smjer == "desno") {
				if (j + 1 < n && stanje[i][j + 1] != 1 && stanje[i][j + 1] != 3) { // OK - možemo ići desno
					j++;
					put.add(new Cvor(new Pozicija(i, j)));
				}
				// Ako ne možemo ići desno, idemo dole i mijenjamo smjer
				else {
					if (i + 1 == n)
						break;
					i++;
					put.add(new Cvor(new Pozicija(i, j)));
					smjer = "lijevo";
				}
			}

			else if (smjer == "lijevo") {
				if (j - 1 >= 0 && stanje[i][j - 1] != 1 && stanje[i][j - 1] != 3) {
					j--;
					put.add(new Cvor(new Pozicija(i, j)));
				} else {
					if (i + 1 == n)
						break;
					i++;
					put.add(new Cvor(new Pozicija(i, j)));
					smjer = "desno";
				}
			}
		}
		return put;
	}

	public LinkedList<Cvor> horizontalnoGore(int[][] stanje, Cvor pocetak) {
		int i = pocetak.getX();
		int j = pocetak.getY();
		String smjer = "desno";
		LinkedList<Cvor> put = new LinkedList<Cvor>();
		put.add(pocetak);

		while (true) {
			if (smjer == "desno") {
				if (j + 1 < n && stanje[i][j + 1] != 1 && stanje[i][j + 1] != 3) {
					j++;
					put.add(new Cvor(new Pozicija(i, j)));
				} else {
					if (i - 1 == -1)
						break;
					i--;
					put.add(new Cvor(new Pozicija(i, j)));
					smjer = "lijevo";
				}
			} else if (smjer == "lijevo") {
				if (j - 1 >= 0 && stanje[i][j - 1] != 1 && stanje[i][j - 1] != 3) {
					j--;
					put.add(new Cvor(new Pozicija(i, j)));
				} else {
					if (i - 1 == -1)
						break;
					i--;
					put.add(new Cvor(new Pozicija(i, j)));
					smjer = "desno";
				}
			}
		}
		return put;
	}

	public String najboljiPotez(int[][] stanje, Cvor pocetak) {
		int i = pocetak.getX();
		int j = pocetak.getY();
		LinkedList<Integer> slobodni = new LinkedList<Integer>();

		// Petlja koja racuna broj slobodnih lijevo
		int slobodnihLijevo = 0;
		int jl = j;
		while (true) {
			if (jl - 1 >= 0 && stanje[i][jl - 1] != 1 && stanje[i][jl - 1] != 3) {
				slobodnihLijevo++;
				jl--;
			} else
				break;
		}

		int slobodnihDesno = 0;
		int jd = j;
		while (true) {
			if (jd + 1 < n && stanje[i][jd + 1] != 1 && stanje[i][jd + 1] != 3) {
				slobodnihDesno++;
				jd++;
			} else
				break;
		}

		int slobodnihDole = 0;
		int id = i;
		while (true) {
			if (id + 1 < n && stanje[id + 1][j] != 1 && stanje[id + 1][j] != 3) {
				slobodnihDole++;
				id++;
			} else
				break;
		}

		int slobodnihGore = 0;
		int ig = i;
		while (true) {
			if (ig - 1 >= 0 && stanje[ig - 1][j] != 1 && stanje[ig - 1][j] != 3) {
				slobodnihGore++;
				ig--;
			} else
				break;
		}

		slobodni.add(slobodnihLijevo);
		slobodni.add(slobodnihDesno);
		slobodni.add(slobodnihDole);
		slobodni.add(slobodnihGore);

		int najviseSlobodnih = Collections.max(slobodni);

		if (najviseSlobodnih == slobodnihLijevo || najviseSlobodnih == slobodnihDesno) {
			slobodni.clear();

			slobodnihDole = 0;
			slobodnihGore = 0;

			id = i;
			j = jl;
			while (true) {
				if (id + 1 < n && stanje[id + 1][j] != 1 && stanje[id + 1][j] != 3) {
					slobodnihDole++;
					id++;
				} else
					break;
			}

			id = i;
			j = jl;
			while (true) {
				if (id - 1 >= 0 && stanje[id - 1][j] != 1 && stanje[id - 1][j] != 3) {
					slobodnihGore++;
					id--;
				} else
					break;
			}

			slobodni.add(slobodnihDole);
			slobodni.add(slobodnihGore);

			najviseSlobodnih = Collections.max(slobodni);
			if (najviseSlobodnih == slobodnihDole) {
				return "HD";
			}
			if (najviseSlobodnih == slobodnihGore) {
				return "HG";
			}

		}

		if (najviseSlobodnih == slobodnihDole || najviseSlobodnih == slobodnihGore) {
			slobodni.clear();

			slobodnihLijevo = 0;
			slobodnihDesno = 0;

			jl = j;
			i = id;
			while (true) {
				if (jl - 1 >= 0 && stanje[i][jl - 1] != 1 && stanje[i][jl - 1] != 3) {
					slobodnihLijevo++;
					jl--;
				} else
					break;
			}

			jd = j;
			i = id;
			while (true) {
				if (jd + 1 < n && stanje[i][jd + 1] != 1 && stanje[i][jd + 1] != 3) {
					slobodnihDesno++;
					jd++;
				} else
					break;
			}

			slobodni.add(slobodnihLijevo);
			slobodni.add(slobodnihDesno);

			najviseSlobodnih = Collections.max(slobodni);
			if (najviseSlobodnih == slobodnihLijevo) {
				return "VL";
			}
			if (najviseSlobodnih == slobodnihDesno) {
				return "VD";
			}

		}
		return null;
	}

	public boolean opasnaHrana(Pozicija hrana) {
		int brojac = 0;
		int i = hrana.getX();
		int j = hrana.getY();

		if (j + 1 < n && (tabelaStanja[i][j + 1] == 1 || tabelaStanja[i][j + 1] == 3))
			brojac++;

		if (j - 1 >= 0 && (tabelaStanja[i][j - 1] == 1 || tabelaStanja[i][j - 1] == 3))
			brojac++;

		if (i + 1 < n && (tabelaStanja[i + 1][j] == 1 || tabelaStanja[i + 1][j] == 3))
			brojac++;

		if (i - 1 >= 0 && (tabelaStanja[i - 1][j] == 1 || tabelaStanja[i - 1][j] == 3))
			brojac++;

		if (brojac >= 3) {
			return true;
		}

		return false;
	}

}

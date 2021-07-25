package konzola;

import java.util.LinkedList;
import java.util.Scanner;

import logika.Cvor;
import logika.Pozicija;
import logika.Snake;

public class IgrajSnake {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Unesite veličinu polja: ");
		int n = sc.nextInt();

		Snake snake = new Snake(n, false);

		System.out.println(pripremiTabeluStanja(snake.vratiTrenutnoStanje()));
		Cvor pocetak = new Cvor(new Pozicija(19, 10));
		// Cvor kraj = new Cvor(snake.getHrana());
		Cvor kraj = new Cvor(new Pozicija(0, 0));

		LinkedList<Cvor> rez = snake.hamiltonovCiklus(snake.vratiTrenutnoStanje());
		LinkedList<String> strRez = snake.pretvoriUKomande(rez);

		for (int i = 0; i < rez.size(); i++) {
			System.out.println(rez.get(i).getX() + " " + rez.get(i).getY());
		}

		for (int i = 0; i < strRez.size(); i++) {
			System.out.println(strRez.get(i));
		}
		/*
		 * // treba while nije kraj igre...samo testiram while(!snake.krajIgre()) {
		 * String potez = ucitajPotez(); odigrajPotez(potez,snake);
		 * System.out.println(pripremiTabeluStanja(snake.vratiTrenutnoStanje())); }
		 * 
		 * System.out.println("Kraj Igre!");
		 */

	}

	private static void odigrajPotez(String smjer, Snake snake) {
		// TODO Auto-generated method stub
		snake.pomjeriZmiju(smjer);
	}

	/**
	 * Učitavanje poteza igrača
	 */
	private static String ucitajPotez() {
		System.out.println("Unesite smjer(gore,dole,lijevo,desno):\n");
		Scanner sc = new Scanner(System.in);
		String smjer = sc.next();
		return smjer;
	}

	private static String pripremiTabeluStanja(int[][] vratiTrenutnoStanje) {
		String stanje = "";
		int n = vratiTrenutnoStanje[0].length;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				stanje += vratiTrenutnoStanje[i][j] + " ";
			}
			stanje += "\n";
		}

		return stanje;
	}

}

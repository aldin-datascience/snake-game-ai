package logika;

public class Cvor {
	private Cvor roditelj;
	private Cvor pozicija;
	private Pozicija koordinate;

	private double F, G, H;

	public Cvor(Pozicija k) {
		roditelj = null;
		pozicija = null;
		koordinate = k;
		F = G = H = 0;
	}

	public Cvor(Cvor r, Cvor p, Pozicija k) {
		roditelj = r;
		pozicija = p;
		koordinate = k;
		F = G = H = 0;
	}

	public Pozicija getKoordinate() {
		return koordinate;
	}

	public void setKoordinate(Pozicija k) {
		koordinate = k;
	}

	public int getX() {
		return koordinate.getX();
	}

	public int getY() {
		return koordinate.getY();
	}

	public void setX(int x) {
		koordinate.setX(x);
	}

	public void setY(int y) {
		koordinate.setY(y);
	}

	public void setF(double f) {
		F = f;
	}

	public double getF() {
		return F;
	}

	public void setG(double g) {
		G = g;
	}

	public double getG() {
		return G;
	}

	public void setH(double h) {
		H = h;
	}

	public double getH() {
		return H;
	}

	public void setRoditelj(Cvor r) {
		roditelj = r;
	}

	public Cvor getRoditelj() {
		return roditelj;
	}

	public void setPozicija(Cvor p) {
		pozicija = p;
	}

	public Cvor getPozicija() {
		return pozicija;
	}
}

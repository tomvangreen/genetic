package geneticships.ships;

public class Ship {
	public final static int WIDTH = 9;
	public final static int HEIGHT = 9;
	private final StringBuilder builder = new StringBuilder();

	public float fitness;
	public int generation;
	public int nr;
	public ShipConfiguration[][] configuration = new ShipConfiguration[WIDTH][HEIGHT];

	@Override
	public String toString() {
		return "Ship: " + nr + ", Fitness: " + fitness + " , Generation " + generation;
	}

	public int getMass() {
		return (WIDTH * HEIGHT) - getMass(ShipConfiguration.None);
	}

	public Ship c(int x, int y, ShipConfiguration configuration) {
		this.configuration[x][y] = configuration;
		return this;
	}

	public Ship clear() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				configuration[x][y] = ShipConfiguration.None;
			}
		}
		return this;
	}

	public int getMass(ShipConfiguration configuration) {
		int count = 0;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (this.configuration[x][y] == configuration) {
					count++;
				}
			}
		}
		return count;
	}

	public String getLayout() {

		for (int y = 0; y < HEIGHT; y++) {
			// if (y > 0) {
			// builder.append("-----------");
			// builder.append("\n");
			// }
			for (int x = 0; x < WIDTH; x++) {
				if (x == 0) {
					// builder.append("|");
				}
				builder.append(configuration[x][y].symbol);
				// builder.append("|");

			}
			builder.append("\n");
		}

		String layout = builder.toString();
		builder.setLength(0);
		return layout;
	}

	public static enum ShipConfiguration {

		None(" ", 2.5f), Engine("E", 0.1f), WeaponBay("W", 0.1f), Shields("S", 0.1f), Structure("X", 0.05f);

		public final String symbol;
		public final float chance;

		ShipConfiguration(String symbol, float chance) {
			this.symbol = symbol;
			this.chance = chance;
		}
	}
}

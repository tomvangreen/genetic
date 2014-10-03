package geneticships.ships;

import geneticships.FitnessFunction;
import geneticships.Genome;
import geneticships.ships.Ship.ShipConfiguration;

public class SymmetryFitness implements FitnessFunction<Ship> {
	private final Ship ship = new Ship();

	@Override
	public float calculate(Genome<Ship> genome) {
		genome.apply(ship);
		ShipConfiguration[][] c = ship.configuration;
		for (int y = 0; y < Ship.HEIGHT; y++) {
			for (int x = 0; x < Ship.WIDTH / 2; x++) {
				if (c[x][y] == c[Ship.WIDTH - 1 - x][y]) {
					return 1;
				}
			}
		}
		return 64f;
	}

}

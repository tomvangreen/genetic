package geneticships.ships;

import geneticships.Chromosome;
import geneticships.ships.Ship.ShipConfiguration;

public class ShipChromosome extends Chromosome<Ship> {
	public final int x;
	public final int y;
	public final ShipConfiguration configuration;

	public ShipChromosome(int x, int y, ShipConfiguration configuration) {
		super(configuration.chance);
		this.x = x;
		this.y = y;
		this.configuration = configuration;
	}

	@Override
	public void apply(Ship data) {
		data.configuration[x][y] = configuration;
		// HACK: to do symmetry
		data.configuration[Ship.WIDTH - 1 - x][y] = configuration;
	}

	@Override
	public boolean isChromosomeOf(Ship data) {
		return data.configuration[x][y] == configuration;
	}
}

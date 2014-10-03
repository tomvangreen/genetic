package geneticships.ships;

import geneticships.GenomeBuilder;
import geneticships.ships.Ship.ShipConfiguration;

public class ShipGenomeBuilder extends GenomeBuilder<Ship> {
	public ShipGenomeBuilder() {
		for (int y = 0; y < Ship.HEIGHT; y++) {
			for (int x = 0; x < Ship.WIDTH; x++) {
				addTrait();
				for (ShipConfiguration c : ShipConfiguration.values()) {
					addChromosome(new ShipChromosome(x, y, c));
				}
			}
		}
	}
}

package geneticships;

import geneticships.ships.ConnectionValidator;
import geneticships.ships.Ship;
import geneticships.ships.Ship.ShipConfiguration;
import geneticships.ships.ShipFitness;
import geneticships.ships.ShipGenomeBuilder;

public class Main {
	public static void main(String[] args) {
		Ship ship = new Ship();
		int showBest = 15;
		Runner<Ship> runner = new Runner<Ship>(new ShipGenomeBuilder(), new ShipFitness(), 1000);
		runner.validator = new ConnectionValidator();
		runner.mutationRate = 0.05f;
		runner.eliteRate = 0.01f;
		runner.crossoverRate = 0.7f;
		runner.builder.templateRate = 0.1f;
		int runs = 100;

		addTemplates(runner);

		while (runs-- > 0) {
			runner.stepGeneration();
			// printScore(ship, showBest, runner);
		}
		printScore(ship, showBest, runner);
	}

	private static void addTemplates(Runner<Ship> runner) {
		Ship ship = new Ship();
		final ShipConfiguration e = ShipConfiguration.Engine;
		final ShipConfiguration w = ShipConfiguration.WeaponBay;
		final ShipConfiguration s = ShipConfiguration.Shields;
		final ShipConfiguration n = ShipConfiguration.None;
		final ShipConfiguration x = ShipConfiguration.Structure;
		// @formatter:off
		ship.clear();
		ship
			.c(4, 2, w).c(5, 2, x).c(6, 2, w)
			.c(4, 3, x).c(5, 3, x).c(6, 3, x)
			.c(4, 4, s).c(5, 4, s).c(6, 4, x)
			.c(4, 5, e).c(5, 5, e).c(6, 5, e)
			
		;
		runner.builder.addTemplate(ship);
		ship
			
			.c(4, 2, w).c(5, 2, n).c(6, 2, n)
			.c(6, 3, n)
			.c(4, 5, n).c(5, 5, n).c(7, 5, e).c(8, 5, e);
		runner.builder.addTemplate(ship);
		
		// @formatter:on
	}

	private static void printScore(Ship ship, int showBest, Runner<Ship> runner) {
		runner.calcScore();
		int elements = Math.min(showBest, runner.population.size());
		for (int index = 0; index < elements; index++) {
			Genome<Ship> genome = runner.population.get(index);
			genome.apply(ship);
			ship.fitness = genome.fitness;
			ship.generation = genome.generation;
			ship.nr = genome.nr;
			System.out.println("  Rank: " + (index + 1));
			System.out.println("    " + ship);
			System.out.println(ship.getLayout());
		}
	}
}

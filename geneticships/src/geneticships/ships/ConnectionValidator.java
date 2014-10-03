package geneticships.ships;

import geneticships.Genome;
import geneticships.Validator;
import geneticships.ships.Ship.ShipConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConnectionValidator implements Validator<Ship> {
	private final Ship ship = new Ship();
	private final List<Point> points = new ArrayList<Point>();
	private final Point point = new Point(0, 0);

	@Override
	public boolean isValid(Genome<Ship> genome) {
		genome.apply(ship);
		for (int y = 0; y < Ship.HEIGHT; y++) {
			for (int x = 0; x < Ship.WIDTH; x++) {
				ShipConfiguration c = ship.configuration[x][y];
				if (c != ShipConfiguration.None) {
					points.add(new Point(x, y));
				}
			}
		}
		if (points.size() == 0) {
			return false;
		}
		int engines = ship.getMass(ShipConfiguration.Engine);
		int weapons = ship.getMass(ShipConfiguration.WeaponBay);
		if (engines == 0 || weapons == 0) {
			return false;
		}

		floodRemove(points.get(0));
		return points.size() == 0;
	}

	private void floodRemove(Point point) {
		if (points.contains(point)) {
			points.remove(point);
			int x = point.x;
			int y = point.y;
			floodRemove(point.set(x + 1, y));
			floodRemove(point.set(x - 1, y));
			floodRemove(point.set(x, y + 1));
			floodRemove(point.set(x, y - 1));
		}
	}

	private static class Point {
		public int x;
		public int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Point set(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Point))
				return false;
			if (other == this)
				return true;

			Point point = (Point) other;
			return x == point.x && y == point.y;
		}
	}
}

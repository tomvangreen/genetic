package geneticships.ships;

import geneticships.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trait<T> {
	private final Random random = new Random();
	public List<Chromosome<T>> chromosomes = new ArrayList<Chromosome<T>>();
	private float totalChance = 0f;

	public void add(Chromosome<T> chromosome) {
		chromosomes.add(chromosome);
		totalChance += chromosome.chance;
	}

	public Chromosome<T> pick() {
		if (chromosomes.size() == 0) {
			return null;
		} else if (chromosomes.size() == 1) {
			return chromosomes.get(0);
		} else {
			float pick = random.nextFloat() * totalChance;
			for (Chromosome<T> c : chromosomes) {
				pick -= c.chance;
				if (pick <= 0) {
					return c;
				}
			}
			return null;
		}
	}
}

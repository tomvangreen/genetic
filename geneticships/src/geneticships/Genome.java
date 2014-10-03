package geneticships;

import java.util.ArrayList;
import java.util.List;

public class Genome<T> implements Comparable<Genome<T>> {
	public int nr;
	public int generation;
	public float fitness;
	public List<Chromosome<T>> chromosomes = new ArrayList<Chromosome<T>>();

	public void apply(T data) {
		for (Chromosome<T> chromosome : chromosomes) {
			chromosome.apply(data);
		}
	}

	@Override
	public int compareTo(Genome<T> other) {
		return Float.compare(fitness, other.fitness);
	}

}

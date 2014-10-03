package geneticships;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Runner<T> {
	private final Random random = new Random();
	public float mutationRate = 0.001f;
	public float crossoverRate = 0.001f;
	public float survivalRate = 0.2f;
	public float eliteRate = 0.1f;
	public Validator<T> validator;
	public FitnessFunction<T> fitness;

	public final GenomeBuilder<T> builder;
	public final int populationCount;
	public final List<Genome<T>> population = new ArrayList<Genome<T>>();
	private int generation;

	public Runner(GenomeBuilder<T> builder, FitnessFunction<T> fitness, int population) {
		this.builder = builder;
		this.fitness = fitness;
		populationCount = population;
	}

	public void stepGeneration() {
		generation++;
		builder.crossoverRate = crossoverRate;
		builder.mutationRate = mutationRate;
		if (population.size() == 0) {
			createNewPopulation();
		} else {
			nextGeneration();
		}
	}

	private List<Genome<T>> newPopulation = new ArrayList<Genome<T>>();

	private void nextGeneration() {
		System.out.println("Splicing next generation. Generation " + generation);
		calcScore();
		int split = (int) (populationCount * survivalRate);

		if (split < 0) {
			System.out.println("No survivors in generation " + generation);
			population.clear();
			return;
		}

		int count = populationCount;
		while (count-- > 0) {
			Genome<T> genome = null;
			if (random.nextFloat() > eliteRate) {
				do {
					Genome<T> left = population.get(random.nextInt(split));
					Genome<T> right = population.get(random.nextInt(split));

					genome = builder.splice(left, right);
				} while (validator != null && !validator.isValid(genome));
			} else {
				genome = new Genome<T>();
				genome.chromosomes.addAll(population.get(random.nextInt(split)).chromosomes);
			}
			genome.nr = count;

			genome.generation = generation;
			newPopulation.add(genome);
		}
		population.clear();
		population.addAll(newPopulation);
		newPopulation.clear();
	}

	public void calcScore() {
		for (Genome<T> genome : population) {
			genome.fitness = fitness.calculate(genome);
		}
		Collections.sort(population);
		Collections.reverse(population);
	}

	private void createNewPopulation() {
		System.out.println("Spawning new population in generation " + generation);
		int count = populationCount;
		while (count-- > 0) {
			Genome<T> genome = null;
			do {
				genome = builder.spawn();
			} while (validator != null && !validator.isValid(genome));
			genome.nr = count;
			genome.generation = generation;
			population.add(genome);
		}
	}
}

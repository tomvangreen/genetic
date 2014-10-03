package geneticships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenomeBuilder<T> {
	public final List<List<Chromosome<T>>> traits = new ArrayList<List<Chromosome<T>>>();
	public final List<Genome<T>> templates = new ArrayList<Genome<T>>();
	private final Random random = new Random();
	public float crossoverRate = 0.7f;
	public float mutationRate = 0.01f;
	public float templateRate = 0.25f;

	public void addTrait() {
		traits.add(new ArrayList<Chromosome<T>>());
	}

	public void addChromosome(Chromosome<T> chromosome) {
		traits.get(traits.size() - 1).add(chromosome);
	}

	public void addTemplate(T object) {
		addTemplate(createGenome(object));
	}

	public Genome<T> createGenome(T object) {
		Genome<T> genome = new Genome<T>();
		for (int index = 0; index < traits.size(); index++) {
			List<Chromosome<T>> trait = traits.get(index);
			Chromosome<T> traitChromosome = findTraitChromosome(object, trait);
			genome.chromosomes.add(traitChromosome);
		}
		return genome;
	}

	private Chromosome<T> findTraitChromosome(T object, List<Chromosome<T>> trait) {
		for (Chromosome<T> chromosome : trait) {
			if (chromosome.isChromosomeOf(object)) {
				return chromosome;
			}
		}
		return null;
	}

	public void addTemplate(Genome<T> genome) {
		templates.add(genome);
	}

	public Genome<T> spawn() {
		Genome<T> genome = new Genome<T>();
		Genome<T> template = getTemplate();
		genome.chromosomes.clear();
		for (int index = 0; index < traits.size(); index++) {
			List<Chromosome<T>> trait = traits.get(index);
			Chromosome<T> templateChromosome = template != null ? template.chromosomes.get(index) : null;
			genome.chromosomes.add(templateChromosome != null ? templateChromosome : spawn(trait));
		}
		return genome;
	}

	private Genome<T> getTemplate() {
		Genome<T> template = null;
		int templateSize = templates.size();
		if (templateSize > 0 && random.nextFloat() < templateRate) {
			if (templateSize == 1) {
				template = templates.get(0);
			} else {
				template = templates.get(random.nextInt(templateSize));
			}
		}
		return template;
	}

	private Chromosome<T> spawn(List<Chromosome<T>> trait) {
		int size = trait.size();
		return size == 1 ? trait.get(0) : trait.get(random.nextInt(size));
	}

	public Genome<T> splice(Genome<T> left, Genome<T> right) {
		List<Chromosome<T>> leftChromosomes = left.chromosomes;
		List<Chromosome<T>> rightChromosomes = right.chromosomes;
		int length = Math.min(leftChromosomes.size(), rightChromosomes.size());
		length = Math.min(length, traits.size());
		Genome<T> genome = new Genome<T>();
		for (int index = 0; index < length; index++) {
			List<Chromosome<T>> trait = traits.get(index);
			Chromosome<T> leftChromosome = leftChromosomes.get(index);
			Chromosome<T> rightChromosome = rightChromosomes.get(index);
			genome.chromosomes.add(splice(trait, leftChromosome, rightChromosome));
		}
		return genome;
	}

	private Chromosome<T> splice(List<Chromosome<T>> trait, Chromosome<T> left, Chromosome<T> right) {
		if (mutationRate > 0f && random.nextFloat() < mutationRate) {
			// Mutate
			return spawn(trait);
		}
		return random.nextFloat() < crossoverRate ? left : right;
	}
}

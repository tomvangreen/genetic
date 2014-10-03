package geneticships;

public abstract class Chromosome<T> {
	public final float chance;

	public Chromosome(float chance) {
		this.chance = chance;
	}

	/**
	 * Apply your genome to the existing object
	 * 
	 * @param data
	 */
	public abstract void apply(T data);

	/**
	 * Check if the object contains a given chromosome
	 * 
	 * @param data
	 * @return
	 */
	public abstract boolean isChromosomeOf(T data);
}

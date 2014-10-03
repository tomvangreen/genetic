package geneticships;

import java.util.ArrayList;
import java.util.List;

public interface FitnessFunction<T> {
	float calculate(Genome<T> genome);

	public abstract static class TypedFitnessFunction<T> implements FitnessFunction<T> {
		private final T template;

		public TypedFitnessFunction(T template) {
			this.template = template;
		}

		public abstract float calculate(T template);

		@Override
		public float calculate(Genome<T> genome) {
			genome.apply(template);
			return calculate(template);
		}
	}

	public static class AddFitness<T> implements FitnessFunction<T> {
		private final List<FitnessFunction<T>> list = new ArrayList<FitnessFunction<T>>();

		public AddFitness(FitnessFunction<T>... functions) {
			for (FitnessFunction<T> function : functions) {
				list.add(function);
			}
		}

		@Override
		public float calculate(Genome<T> genome) {
			float result = 0f;
			for (FitnessFunction<T> function : list) {
				result += function.calculate(genome);
			}
			return result;
		}

	}

	public static class MultiplyFitness<T> implements FitnessFunction<T> {
		private final List<FitnessFunction<T>> list = new ArrayList<FitnessFunction<T>>();

		public MultiplyFitness(FitnessFunction<T>... functions) {
			for (FitnessFunction<T> function : functions) {
				list.add(function);
			}
		}

		@Override
		public float calculate(Genome<T> genome) {
			float result = 0f;
			boolean first = true;
			for (FitnessFunction<T> function : list) {
				float part = function.calculate(genome);
				if (first) {
					result = part;
					first = false;
				} else {
					result *= part;
				}
			}
			return result;
		}

	}

}

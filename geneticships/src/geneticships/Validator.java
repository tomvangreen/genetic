package geneticships;

import java.util.ArrayList;
import java.util.List;

public interface Validator<T> {
	public boolean isValid(Genome<T> genome);

	public static class AndValidator<T> implements Validator {
		private final List<Validator<T>> validators = new ArrayList<Validator<T>>();

		public AndValidator(Validator<T>... validators) {
			for (Validator<T> validator : validators) {
				this.validators.add(validator);
			}
		}

		@Override
		public boolean isValid(Genome genome) {
			for (Validator<T> validator : validators) {
				if (!validator.isValid(genome)) {
					return false;
				}
			}
			return true;
		}
	}

	public static class OrValidator<T> implements Validator {
		private final List<Validator<T>> validators = new ArrayList<Validator<T>>();

		public OrValidator(Validator<T>... validators) {
			for (Validator<T> validator : validators) {
				this.validators.add(validator);
			}
		}

		@Override
		public boolean isValid(Genome genome) {
			for (Validator<T> validator : validators) {
				if (validator.isValid(genome)) {
					return true;
				}
			}
			return validators.size() == 0;
		}
	}
}

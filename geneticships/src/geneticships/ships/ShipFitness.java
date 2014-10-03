package geneticships.ships;

import geneticships.FitnessFunction;
import geneticships.Genome;
import geneticships.ships.Ship.ShipConfiguration;

public class ShipFitness implements FitnessFunction<Ship> {
	private final MultiplyFitness<Ship> symmetryMultiplier;
	private final AddFitness<Ship> generalFitness;

	public ShipFitness() {
		//@formatter:off
		generalFitness = new AddFitness<Ship>(
				new TypedFitnessFunction<Ship>(new Ship()) {
					@Override
					public float calculate(Ship template) {
						int mass = template.getMass();
						int engines = template.getMass(ShipConfiguration.Engine);
						if(mass == 0){
							return 0f;
						}
						float value = 1f / mass * engines * 4;
						if(value > 8f){
							value = 8f;
						}
						return value * 8;
					}
				}
				, new TypedFitnessFunction<Ship>(new Ship()) {
					@Override
					public float calculate(Ship template) {
						int mass = template.getMass();
						int shields = template.getMass(ShipConfiguration.Shields);
						if(mass == 0){
							return 0f;
						}
						float value = 1f / mass * shields * 8;
						if(value > 8f){
							value = 8f;
						}
						return value* 8;
					}
				}
				, new TypedFitnessFunction<Ship>(new Ship()) {
					@Override
					public float calculate(Ship template) {
						int firepower = template.getMass(ShipConfiguration.WeaponBay);
						float value = firepower / 4f;
						if(value > 8f){
							value = 8f;
						}
						return value* 4;
					}
				}
//				, new TypedFitnessFunction<Ship>(new Ship()) {
//					@Override
//					public float calculate(Ship template) {
//						int mass = template.getMass();
//						float value = ((4*4) - mass) / 8f;
//						if(value > 15){
//							value = 15;
//						}
//						return value * 2;
//					}
//				}
				, new TypedFitnessFunction<Ship>(new Ship()) {
					@Override
					public float calculate(Ship template) {
						int mass = template.getMass(ShipConfiguration.Structure);
						return mass / 4;
					}
				}
				, new TypedFitnessFunction<Ship>(new Ship()) {
					@Override
					public float calculate(Ship template) {
						int total = Ship.WIDTH * Ship.HEIGHT;
						int mass = template.getMass(ShipConfiguration.Structure);
						return (total - mass*8);
					}
				}
			);
		symmetryMultiplier = new MultiplyFitness<Ship>(
				new SymmetryFitness()
				, generalFitness
				, new TypedFitnessFunction<Ship>(new Ship()) {
					@Override
					public float calculate(Ship template) {
						if(template.getMass(ShipConfiguration.Engine) == 0){
							return 0f;
						}
						else if(template.getMass(ShipConfiguration.WeaponBay) == 0){
							return 0f;
						}
						return 1f;
					}
				}				
			);
		//@formatter:on
	}

	@Override
	public float calculate(Genome<Ship> genome) {
		return symmetryMultiplier.calculate(genome);
	}

}

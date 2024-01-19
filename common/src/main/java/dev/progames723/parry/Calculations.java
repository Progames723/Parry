package dev.progames723.parry;

public class Calculations {
	public static float calculateArmorPenetrationWithDmgReduction(float damage, double armorPenetration, double dmgReduction) {
		//fix 100% damage reduction (A SOLUTION: calculate insaneNumber before calling, if insane number is less or equal to -100 then cancel damage)
		double insaneNumber = armorPenetration - dmgReduction;
		double finalDamage;
		if (insaneNumber < -100){
			insaneNumber = -100;
		} if (insaneNumber > 100){
			finalDamage = ifArmorPenetrationIsHigherThan100Percent(damage,insaneNumber);
		} else {
			if (insaneNumber < 0) {
				finalDamage = damage / 100 * (-100 - insaneNumber);
			} else {
				finalDamage = (100 * damage - damage * insaneNumber) / 100;
			}
		}
		return (float) finalDamage;
	}
	public static float ifArmorPenetrationIsHigherThan100Percent(float damage, double armorPenetration){
		double finalDamage = Math.pow(((damage * armorPenetration)/100), 1.01); //((damage*armorPenetration)/100)^1.01
		return (float) finalDamage;
	}
}
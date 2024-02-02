package dev.progames723.parry.fabric;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

import static dev.progames723.parry.EffectThing.BROKEN_ARMOR;

public class EffectThingImpl {
	public static boolean chanceForArmorReduction(LivingEntity entity, float amount, ItemStack item) {
		int ampl = entity.getEffect(BROKEN_ARMOR).getAmplifier()+1;
		int mult = 1;
		Random random = new Random();
		int h = random.nextInt(101);
		if (entity.getEffect(BROKEN_ARMOR) != null){
			if (ampl > 0){
				double stas = ampl*1.25;
				mult = (int) stas;
			}
		}
		if (item.is(ItemTags.AXES)){
			if (h > 0 && h <= 5*mult){
				return true;
			}
		} else if (item.is(ItemTags.SWORDS)) {
			if (h > 0 && h <= 3*mult){
				return true;
			}
		} else if (item.is(ItemTags.PICKAXES)) {
			if (h > 0 && h <= 6*mult){
				return true;
			}
		} else if (item.is(ItemTags.HOES)) {
			if (h > 0 && h <= 6*mult){
				return true;
			}
		} else if (item.is(ItemTags.SHOVELS)) {
			if (h > 0 && h <= 2*mult){
				return true;
			}
		} else if (item.is(ConventionalItemTags.SPEARS)){
			if (h > 0 && h <= 6*mult){
				return true;
			}
		}
		return false;
	}
}

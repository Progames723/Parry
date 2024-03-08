package dev.progames723.parry.forge;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.Random;

import static dev.progames723.parry.EffectThing.brokenArmorEffect;

public class EffectThingImpl {
	public static boolean chanceForArmorReduction(LivingEntity entity, DamageSource source, float amount, Entity attacker) {
		ItemStack item = null;
		if (attacker instanceof LivingEntity living) {
			item = living.getMainHandItem();
		}
		int ampl;
		double mult = 1.0;
		Random random = new Random();
		int h = random.nextInt(101);
		if (entity.getEffect(brokenArmorEffect) != null){
			ampl = entity.getEffect(brokenArmorEffect).getAmplifier()+1;
			if (ampl > 0){
				double x = ampl * amount;
				mult = (1 - (1 / (1 + (x) / 30.0))) * 15.0;
			}
		}
		if (!source.isIndirect() && item != null){
			if (item.is(ItemTags.AXES)){
				if (h > 0 && h <= Math.min(5*mult, 100)){
					return true;
				}
			} else if (item.is(ItemTags.SWORDS)) {
				if (h > 0 && h <= Math.min(3*mult, 100)){
					return true;
				}
			} else if (item.is(ItemTags.PICKAXES)) {
				if (h > 0 && h <= Math.min(5*mult, 100)){
					return true;
				}
			} else if (item.is(ItemTags.HOES)) {
				if (h > 0 && h <= Math.min(5*mult, 100)){
					return true;
				}
			} else if (item.is(ItemTags.SHOVELS)) {
				if (h > 0 && h <= Math.min(2*mult, 100)){
					return true;
				}
			} else if (item.is(Tags.Items.TOOLS_TRIDENTS)){
				if (h > 0 && h <= Math.min(4*mult, 100)){
					return true;
				}
			}
		} else if (item == null){
			if (h > 0 && h <= mult){
				return true;
			}
		} else if (item.is(Items.AIR)) {
			if (h > 0 && h <= mult){
				return true;
			}
		}
		return false;
	}
}

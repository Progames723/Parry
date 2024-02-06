package dev.progames723.parry.effects;

import dev.progames723.hmmm.DamageSourceSomething;
import dev.progames723.hmmm.HmmmDamageTypes;
import dev.progames723.parry.EffectThing;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedEffect extends MobEffect {
	protected BleedEffect(MobEffectCategory mobEffectCategory, int i) {
		super(mobEffectCategory, i);
	}
	public void applyEffectTick(LivingEntity livingEntity, int i) {
		super.applyEffectTick(livingEntity, i);
		if (livingEntity.getEffect(EffectThing.bleedEffect) != null && livingEntity.getEffect(EffectThing.bleedEffect).getAmplifier() > 0) {
			livingEntity.hurt(DamageSourceSomething.of(
					livingEntity.level(),
					HmmmDamageTypes.BLEED,
					null,
					null
			), 2.0f);
		}
	}
	public boolean shouldApplyEffectTickThisTick(int i, int j) {
		int k = 25 >> j;
		if (k > 0) {
			return i % k == 0;
		} else {
			return true;
		}
	}
}

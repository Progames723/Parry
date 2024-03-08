package dev.progames723.parry.effects;

import dev.progames723.hmmm.DamageSourceSomething;
import dev.progames723.hmmm.HmmmDamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedEffect extends MobEffect {
	public BleedEffect(MobEffectCategory mobEffectCategory, int i) {
		super(mobEffectCategory, i);
	}
	public void applyEffectTick(LivingEntity livingEntity, int i) {
		super.applyEffectTick(livingEntity, i);
		livingEntity.hurt(DamageSourceSomething.of(
				livingEntity.level(),
				HmmmDamageTypes.BLEED,
				null,
				null
		), 1f*(i+1));
	}
	public boolean shouldApplyEffectTickThisTick(int i, int j) {
		int k = 24 >> j;
		if (k > 0) {
			return i % k == 0;
		} else {
			return true;
		}
	}
}

package dev.progames723.parry.effects;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectThing extends MobEffect {
	protected EffectThing(MobEffectCategory mobEffectCategory, int i) {
		super(mobEffectCategory, i);
	}

	private static MobEffect register(String string, MobEffect mobEffect) {
		return Registry.register(BuiltInRegistries.MOB_EFFECT, string, mobEffect);
	}
	public static final MobEffect RESISTANT;
	public static final MobEffect VULNERABLE;
	public static final MobEffect BLEED;
	public static final MobEffect BROKEN_ARMOR;
	static {
		RESISTANT = register("resistant", new EffectThing(MobEffectCategory.BENEFICIAL, 2917807));
		VULNERABLE = register("vulnerable", new EffectThing(MobEffectCategory.HARMFUL, 9724160));
		BLEED = register("bleed", new EffectThing(MobEffectCategory.HARMFUL, 8921100));
		BROKEN_ARMOR = register("broken_armor", new EffectThing(MobEffectCategory.HARMFUL, 9273985));
	}
}

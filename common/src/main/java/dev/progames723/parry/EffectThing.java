package dev.progames723.parry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.progames723.parry.effects.BleedEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import static dev.progames723.parry.Parry.LOGGER;
import static dev.progames723.parry.Parry.MOD_ID;

public class EffectThing extends MobEffect {
	protected EffectThing(MobEffectCategory mobEffectCategory, int i) {
		super(mobEffectCategory, i);
	}
	public static MobEffect resistantEffect;
	public static MobEffect vulnerableEffect;
	public static MobEffect bleedEffect;
	public static MobEffect brokenArmorEffect;
	public static MobEffect brokenEffect;

	private static MobEffect register(String name, MobEffect effect) {
		LOGGER.info("Registered effect: " + name);
		return Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(MOD_ID, name), effect);
	}
	public static void init(){
		resistantEffect = register("resistant", new EffectThing(MobEffectCategory.BENEFICIAL, 2917807));
		vulnerableEffect = register("vulnerable", new EffectThing(MobEffectCategory.HARMFUL, 9724160));
		bleedEffect = register("bleed", new BleedEffect(MobEffectCategory.HARMFUL, 8921100));
		brokenArmorEffect = register("broken_armor", new EffectThing(MobEffectCategory.HARMFUL, 9273985)
				.addAttributeModifier(Attributes.ARMOR, "C712BC04-64F8-48E8-8434-15BDDA9DD391", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
				.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "D0A338E1-AEBD-4B49-B3CD-638089ECBAF4", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
				.addAttributeModifier(Attributes.MOVEMENT_SPEED, "CBF164AE-3426-4E02-AAD1-020E36C2B2E9", 0.02, AttributeModifier.Operation.MULTIPLY_TOTAL));
		brokenEffect = register("broken", new EffectThing(MobEffectCategory.HARMFUL, 12871)
				.addAttributeModifier(Attributes.MAX_HEALTH, "EE919000-9769-4231-9A59-3C160DFD3271", -0.05,AttributeModifier.Operation.MULTIPLY_TOTAL));
	}

	@ExpectPlatform
	public static boolean chanceForArmorReduction(LivingEntity entity, DamageSource source, float amount, Entity attacker){
		throw new RuntimeException(new ClassNotFoundException());
	}
	public static void applyArmorReduction(LivingEntity entity){
		if (entity.getEffect(brokenArmorEffect) != null) {
			int ampl = entity.getEffect(brokenArmorEffect).getAmplifier()+1;
			int duration = entity.getEffect(brokenArmorEffect).getDuration();
			entity.forceAddEffect(new MobEffectInstance(brokenArmorEffect, duration+900, ampl+1), entity);
		} else {
			entity.addEffect(new MobEffectInstance(brokenArmorEffect, 900, 1), entity);
		}
		entity.level().playSound(null, entity.getOnPos(), SoundEvents.ANVIL_PLACE, SoundSource.MASTER, 2, 1.3f);
		entity.level().playSound(null, entity.getOnPos(), SoundEvents.ARMOR_STAND_BREAK, SoundSource.MASTER, 2, 1.3f);
	}
	public static void reduceArmorReductionEffect(LivingEntity entity) {
		if (entity.getEffect(brokenArmorEffect) != null){
			int ampl = entity.getEffect(brokenArmorEffect).getAmplifier()+1;
			int duration = entity.getEffect(brokenArmorEffect).getDuration();
			entity.forceAddEffect(new MobEffectInstance(brokenArmorEffect, duration, ampl-2), entity);
			entity.level().playSound(null, entity.getOnPos(), SoundEvents.ANVIL_USE, SoundSource.MASTER, 0.5f, 0.5f);
		}
	}
}

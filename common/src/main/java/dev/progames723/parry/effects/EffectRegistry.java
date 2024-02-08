package dev.progames723.parry.effects;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.progames723.parry.EffectThing;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import static dev.progames723.parry.Parry.LOGGER;
import static dev.progames723.parry.Parry.MOD_ID;

public class EffectRegistry {
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(MOD_ID, Registries.MOB_EFFECT);
	public static RegistrySupplier<MobEffect> RESISTANT;
	public static RegistrySupplier<MobEffect> VULNERABLE;
	public static RegistrySupplier<MobEffect> BLEED;
	public static RegistrySupplier<MobEffect> BROKEN_ARMOR;
	public static RegistrySupplier<MobEffect> BROKEN;
	public static RegistrySupplier<MobEffect> registerEffect(String name, MobEffect effect){
		LOGGER.info("Registered Effect: " + name);
		return EFFECTS.register(new ResourceLocation(MOD_ID, name), () -> effect);
	}
	public static void init() {
		RESISTANT = registerEffect("resistant", EffectThing.resistantEffect);
		VULNERABLE = registerEffect("vulnerable", EffectThing.vulnerableEffect);
		BLEED = registerEffect("bleed", EffectThing.bleedEffect);
		BROKEN_ARMOR = registerEffect("broken_armor", EffectThing.brokenArmorEffect);
		BROKEN = registerEffect("broken", EffectThing.brokenEffect);
	}
}

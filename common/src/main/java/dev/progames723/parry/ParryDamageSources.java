package dev.progames723.parry;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class ParryDamageSources {
	public ParryDamageSources(RegistryAccess registryAccess) {
		damageTypes = registryAccess.registryOrThrow(Registries.DAMAGE_TYPE);
		bleedSource = source(bleed);
	}
	private final Registry<DamageType> damageTypes;
	public static ResourceKey<DamageType> bleed;
	public DamageSource bleedSource;
	public static void init() {
		bleed = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Parry.MOD_ID, "bleed"));
	}
	private DamageSource source(ResourceKey<DamageType> resourceKey) {
		return new DamageSource(damageTypes.getHolderOrThrow(resourceKey));
	}
}

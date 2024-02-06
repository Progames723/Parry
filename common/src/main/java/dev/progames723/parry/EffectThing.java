package dev.progames723.parry;

import com.google.common.base.Suppliers;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.progames723.hmmm.DamageSourceSomething;
import dev.progames723.hmmm.HmmmDamageTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

import static dev.progames723.parry.Parry.MOD_ID;

public class EffectThing extends MobEffect {
	protected EffectThing(MobEffectCategory mobEffectCategory, int i) {
		super(mobEffectCategory, i);
	}
	public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
	public static final Registrar<MobEffect> effects = MANAGER.get().get(Registries.MOB_EFFECT);
	public static final RegistrySupplier<MobEffect> RESISTANT = effects.register(new ResourceLocation(MOD_ID, "resistant"), () -> new EffectThing(MobEffectCategory.BENEFICIAL, 2917807));
	public static final RegistrySupplier<MobEffect> VULNERABLE = effects.register(new ResourceLocation(MOD_ID, "vulnerable"), () -> new EffectThing(MobEffectCategory.HARMFUL, 9724160));
	public static final RegistrySupplier<MobEffect> BLEED = effects.register(new ResourceLocation(MOD_ID, "bleed"), () -> new EffectThing(MobEffectCategory.HARMFUL, 8921100));
	public static final RegistrySupplier<MobEffect> BROKEN_ARMOR = effects.register(new ResourceLocation(MOD_ID, "broken_armor"), () -> new EffectThing(MobEffectCategory.HARMFUL, 9273985)
			.addAttributeModifier(Attributes.ARMOR, "C712BC04-64F8-48E8-8434-15BDDA9DD391", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "D0A338E1-AEBD-4B49-B3CD-638089ECBAF4", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "CBF164AE-3426-4E02-AAD1-020E36C2B2E9", 0.02, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistrySupplier<MobEffect> BROKEN = effects.register(new ResourceLocation(MOD_ID, "broken"), () -> new EffectThing(MobEffectCategory.HARMFUL, 12871)
			.addAttributeModifier(Attributes.MAX_HEALTH, "EE919000-9769-4231-9A59-3C160DFD3271", -0.02,AttributeModifier.Operation.MULTIPLY_TOTAL));

	public static final MobEffect resistantEffect = RESISTANT.get();
	public static final MobEffect vulnerableEffect = VULNERABLE.get();
	public static final MobEffect bleedEffect = BLEED.get();
	public static final MobEffect brokenArmorEffect = BROKEN_ARMOR.get();
	public static final MobEffect brokenEffect = BROKEN.get();

	private static MobEffect register(String string, MobEffect mobEffect) {
		return Registry.register(BuiltInRegistries.MOB_EFFECT, string, mobEffect);
	}
//	public static final MobEffect RESISTANT = registerStatusEffect("resistant", new EffectThing(MobEffectCategory.BENEFICIAL, 2917807));
//	public static final MobEffect VULNERABLE = registerStatusEffect("vulnerable", new EffectThing(MobEffectCategory.HARMFUL, 9724160));
//	public static final MobEffect BLEED = registerStatusEffect("bleed", new EffectThing(MobEffectCategory.HARMFUL, 8921100));
//	public static final MobEffect BROKEN_ARMOR = registerStatusEffect("broken_armor", new EffectThing(MobEffectCategory.HARMFUL, 9273985));
//	public static final MobEffect BROKEN = registerStatusEffect("broken", new EffectThing(MobEffectCategory.HARMFUL, 12871));

	@ExpectPlatform
	public static boolean chanceForArmorReduction(LivingEntity entity, DamageSource source, float amount, ItemStack item){
		throw new RuntimeException(new ClassNotFoundException());
	}
	public static void damageBleed(Player player, Level level){
		if (player.getEffect(bleedEffect) != null){
			int amplifier = player.getEffect(bleedEffect).getAmplifier()+1;
			player.hurt(DamageSourceSomething.of(level, HmmmDamageTypes.BLEED, null, null), (1.5f*amplifier)-1);
		}
	}
}

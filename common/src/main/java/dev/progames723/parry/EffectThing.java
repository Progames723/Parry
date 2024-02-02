package dev.progames723.parry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.progames723.hmmm.DamageSourceSomething;
import dev.progames723.hmmm.HmmmDamageTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
	public static final MobEffect BROKEN;
	static {
		RESISTANT = register("resistant", new EffectThing(MobEffectCategory.BENEFICIAL, 2917807));
		VULNERABLE = register("vulnerable", new EffectThing(MobEffectCategory.HARMFUL, 9724160));
		BLEED = register("bleed", new EffectThing(MobEffectCategory.HARMFUL, 8921100));
		BROKEN = register("broken", new EffectThing(MobEffectCategory.HARMFUL, 12871))
				.addAttributeModifier(Attributes.MAX_HEALTH, "EE919000-9769-4231-9A59-3C160DFD3271", -0.02,AttributeModifier.Operation.MULTIPLY_TOTAL);
		BROKEN_ARMOR = register("broken_armor", new EffectThing(MobEffectCategory.HARMFUL, 9273985))
				.addAttributeModifier(Attributes.ARMOR, "C712BC04-64F8-48E8-8434-15BDDA9DD391", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
				.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "D0A338E1-AEBD-4B49-B3CD-638089ECBAF4", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
				.addAttributeModifier(Attributes.MOVEMENT_SPEED, "CBF164AE-3426-4E02-AAD1-020E36C2B2E9", 0.02, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
	@ExpectPlatform
	public static boolean chanceForArmorReduction(LivingEntity entity, float amount, ItemStack item){
		throw new RuntimeException(new ClassNotFoundException());
	}
	public static void damageBleed(Player player, Level level){
		if (player.getEffect(BLEED) != null){
			int amplifier = player.getEffect(BLEED).getAmplifier()+1;
			player.hurt(DamageSourceSomething.of(level, HmmmDamageTypes.BLEED, null, null), (1.5f*amplifier)-1);
		}
	}
}

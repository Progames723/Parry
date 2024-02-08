package dev.progames723.parry;

import com.google.common.base.Suppliers;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.progames723.parry.effects.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class Parry {
	public static final String MOD_ID = "parry_this";
	public static final Logger LOGGER = LoggerFactory.getLogger("Parry");
//	public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));
//	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(MOD_ID, Registries.MOB_EFFECT);
//	public static RegistrySupplier<MobEffect> RESISTANT;
//	public static RegistrySupplier<MobEffect> VULNERABLE;
//	public static RegistrySupplier<MobEffect> BLEED;
//	public static RegistrySupplier<MobEffect> BROKEN_ARMOR;
//	public static RegistrySupplier<MobEffect> BROKEN;

	public static void init() {
		EffectRegistry.init();
//		RegistrySupplier<MobEffect> RESISTANT = EFFECTS.register(new ResourceLocation(MOD_ID, "resistant"), () -> new EffectThing(MobEffectCategory.BENEFICIAL, 2917807));
//		RegistrySupplier<MobEffect> VULNERABLE = EFFECTS.register(new ResourceLocation(MOD_ID, "vulnerable"), () -> new EffectThing(MobEffectCategory.HARMFUL, 9724160));
//		RegistrySupplier<MobEffect> BLEED = EFFECTS.register(new ResourceLocation(MOD_ID, "bleed"), () -> new EffectThing(MobEffectCategory.HARMFUL, 8921100));
//		RegistrySupplier<MobEffect> BROKEN_ARMOR = EFFECTS.register(new ResourceLocation(MOD_ID, "broken_armor"), () -> new EffectThing(MobEffectCategory.HARMFUL, 9273985)
//				.addAttributeModifier(Attributes.ARMOR, "C712BC04-64F8-48E8-8434-15BDDA9DD391", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
//				.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "D0A338E1-AEBD-4B49-B3CD-638089ECBAF4", -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)
//				.addAttributeModifier(Attributes.MOVEMENT_SPEED, "CBF164AE-3426-4E02-AAD1-020E36C2B2E9", 0.02, AttributeModifier.Operation.MULTIPLY_TOTAL));
//		RegistrySupplier<MobEffect> BROKEN = EFFECTS.register(new ResourceLocation(MOD_ID, "broken"), () -> new EffectThing(MobEffectCategory.HARMFUL, 12871)
//				.addAttributeModifier(Attributes.MAX_HEALTH, "EE919000-9769-4231-9A59-3C160DFD3271", -0.02,AttributeModifier.Operation.MULTIPLY_TOTAL));
		PlayerEvent.PLAYER_JOIN.register((player) -> {
			Variables.initializeVar(player);
			CompoundTag nbt = Variables.thisIsImportant(player);
			if (!nbt.getBoolean(Variables.isNew)){
				Minecraft mc = Minecraft.getInstance();
				mc.gui.getChat().addRecentChat("Hello and thank you for downloading my mod! This is shown only once. \n" +
						"Well good luck and don't forget to parry that fall damage");
			}
		});
		PlayerEvent.PLAYER_RESPAWN.register((player, what) -> {
			if (!what){
				Variables.initializeVar(player, true);
			}
		});
		TickEvent.PLAYER_PRE.register((player) -> {
			Variables.parryTick(player);
			Variables.changeValues(player);
		});
	}
}

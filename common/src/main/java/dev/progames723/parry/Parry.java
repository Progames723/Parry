package dev.progames723.parry;

import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.progames723.parry.enchantments.ParryEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parry {
	public static final String MOD_ID = "parry_this";
	public static final Logger LOGGER = LoggerFactory.getLogger("Parry");
	public static GameRules.Key<GameRules.IntegerValue> perfectParryTicksGameRule;
	public static GameRules.Key<GameRules.IntegerValue> parryTicksGameRule;
	public static GameRules.Key<GameRules.IntegerValue> lateParryTicksGameRule;

	public static void init() {
		ParryEnchantments.init();
		EffectThing.init();
		perfectParryTicksGameRule = GameRules.register("perfect_parry_ticks", GameRules.Category.PLAYER, GameRules.IntegerValue.create(3));
		parryTicksGameRule = GameRules.register("perfect_parry_ticks", GameRules.Category.PLAYER, GameRules.IntegerValue.create(8));
		lateParryTicksGameRule = GameRules.register("perfect_parry_ticks", GameRules.Category.PLAYER, GameRules.IntegerValue.create(9));
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
			} else {
				Variables.initializeVar(player, true);
			}
		});
		TickEvent.PLAYER_PRE.register((player) -> {
			Variables.parryTick(player);
			Variables.changeValues(player);
		});
	}
}

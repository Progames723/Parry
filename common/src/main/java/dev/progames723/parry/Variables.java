package dev.progames723.parry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import static dev.progames723.parry.Parry.LOGGER;

public class Variables{
	protected Variables() {}
	public static String perfectParry = "perfect_parry";//boolean
	public static String parry = "parry";//boolean
	public static String lateParry = "late_parry";//boolean
	public static String perfectParryTicks = "perfect_parry_ticks";//int
	public static String parryTicks = "parry_ticks";//int
	public static String lateParryTicks = "late_parry_ticks";//int
	public static String resistant = "resistant";//double
	public static String vulnerable = "vulnerable";//double
	public static String insaneNumber = "insane_number";//double, combination of resistant and vulnerable with (vulnerable - resistant)
	public static String isNew = "is_new";//boolean

	public static void changeValues(Player player){
		CompoundTag nbt = thisIsImportant(player);
		if (nbt.getDouble(resistant) > 100.0){
			nbt.putDouble(resistant, 100.0);
		}
		if (nbt.getDouble(resistant) < 0.0){
			nbt.putDouble(resistant, 0.0);
		}
		if (nbt.getDouble(vulnerable) < 0.0){
			nbt.putDouble(vulnerable, 0.0);
		}
		if (nbt.getDouble(insaneNumber) > 200.0+nbt.getDouble(vulnerable)){
			nbt.putDouble(insaneNumber, 200.0+nbt.getDouble(vulnerable));
		}
		if (nbt.getDouble(insaneNumber) < -100.0){
			nbt.putDouble(insaneNumber, -100.0);
		}
		nbt.putDouble(insaneNumber, nbt.getDouble(vulnerable)-nbt.getDouble(resistant));
	}
	public static void parryTick(Player player){
		CompoundTag nbt = thisIsImportant(player);
		int h1 = nbt.getInt(perfectParryTicks);
		int h2 = nbt.getInt(parryTicks);
		int h3 = nbt.getInt(lateParryTicks);
		if (h1 <= 0){
			nbt.putInt(perfectParryTicks, 0);
		}
		if (h2 <= 0){
			nbt.putInt(parryTicks, 0);
		}
		if (h3 <= 0){
			nbt.putInt(lateParryTicks, 0);
		}
		if (h1 > 0){
			if (nbt.getBoolean(perfectParry) &&
					!nbt.getBoolean(parry) &&
					!nbt.getBoolean(lateParry)) {
				nbt.putInt(perfectParryTicks, h1-1);
				if (h1 <= 1){
					nbt.putBoolean(perfectParry, false);
					nbt.putBoolean(parry, true);
					nbt.putBoolean(lateParry, false);
				}
			} else {
				nbt.putBoolean(perfectParry, true);
				nbt.putBoolean(parry, false);
				nbt.putBoolean(lateParry, false);
			}
		} else if (h1 <= 0 && h2 > 0){
			if (!nbt.getBoolean(perfectParry) &&
					nbt.getBoolean(parry) &&
					!nbt.getBoolean(lateParry)) {
				nbt.putInt(parryTicks, h2-1);
				if (h2 <= 1){
					nbt.putBoolean(perfectParry, false);
					nbt.putBoolean(parry, true);
					nbt.putBoolean(lateParry, false);
				}
			} else {
				nbt.putBoolean(perfectParry, false);
				nbt.putBoolean(parry, true);
				nbt.putBoolean(lateParry, false);
			}
		} else if (h1 <= 0 && h2 <= 0 && h3 > 0){
			if (!nbt.getBoolean(perfectParry) &&
					!nbt.getBoolean(parry) &&
					nbt.getBoolean(lateParry)) {
				nbt.putInt(lateParryTicks, h3-1);
				if (h3 <= 1){
					nbt.putBoolean(perfectParry, false);
					nbt.putBoolean(parry, false);
					nbt.putBoolean(lateParry, true);
				}
			} else {
				nbt.putBoolean(perfectParry, false);
				nbt.putBoolean(parry, false);
				nbt.putBoolean(lateParry, true);
			}
		} else if (h1 <= 0 && h2 <= 0 && h3 <= 0) {
			nbt.putBoolean(perfectParry, false);
			nbt.putBoolean(parry, false);
			nbt.putBoolean(lateParry, false);
		}
	}
	public static CompoundTag thisIsImportant(Player player){
		EntityDataSaver w = (EntityDataSaver) player;
		return w.getPersistentData();
	}
	@Deprecated(forRemoval = true)//deprecated because variables are persistent enough so this is unnecessary
	public static void revokeVariablesOnExit(Player player){
		CompoundTag nbt = thisIsImportant(player);
		if (nbt.getBoolean(perfectParry) || nbt.getBoolean(parry) || nbt.getBoolean(lateParry)){
			nbt.putBoolean(perfectParry, false);
			nbt.putBoolean(parry, false);
			nbt.putBoolean(lateParry, false);
		}
		if (nbt.getInt(perfectParryTicks) != 0){
			nbt.putInt(perfectParryTicks, 0);
		}
		if (nbt.getInt(parryTicks) != 0){
			nbt.putInt(parryTicks, 0);
		}
		if (nbt.getInt(lateParryTicks) != 0){
			nbt.putInt(lateParryTicks, 0);
		}
	}
	public static void initializeVar(Player player){
		CompoundTag nbt = thisIsImportant(player);
		if (!nbt.getBoolean((isNew))){
			nbt.putInt(perfectParryTicks, 0);
			nbt.putInt(parryTicks, 0);
			nbt.putInt(lateParryTicks, 0);
			nbt.putBoolean(perfectParry, false);
			nbt.putBoolean(parry, false);
			nbt.putBoolean(lateParry, false);
			nbt.putDouble(vulnerable, 0.0);
			nbt.putDouble(resistant, 0.0);
			nbt.putBoolean(isNew, true);
		}
	}
	public static void initializeVar(Player player, boolean override){
		CompoundTag nbt = thisIsImportant(player);
		if (!nbt.getBoolean((isNew)) || override){
			nbt.putInt(perfectParryTicks, 0);
			nbt.putInt(parryTicks, 0);
			nbt.putInt(lateParryTicks, 0);
			nbt.putBoolean(perfectParry, false);
			nbt.putBoolean(parry, false);
			nbt.putBoolean(lateParry, false);
			nbt.putDouble(vulnerable, 0.0);
			nbt.putDouble(resistant, 0.0);
			nbt.putBoolean(isNew, true);
		}
	}

	public static void setTicks(Player player, int perfectParryTicks, int parryTicks, int lateParryTicks){
		player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), (perfectParryTicks+parryTicks+lateParryTicks));
		CompoundTag nbt = thisIsImportant(player);
		nbt.putInt(Variables.perfectParryTicks, perfectParryTicks);
		nbt.putInt(Variables.parryTicks, parryTicks);
		nbt.putInt(Variables.lateParryTicks, lateParryTicks);
		nbt.putBoolean(perfectParry, true);
		nbt.putBoolean(parry, false);
		nbt.putBoolean(lateParry, false);
	}
	public static void setTicks(Player player){
		player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 20);
		CompoundTag nbt = thisIsImportant(player);
		nbt.putInt(Variables.perfectParryTicks, 3);
		nbt.putInt(Variables.parryTicks, 8);
		nbt.putInt(Variables.lateParryTicks, 9);
		nbt.putBoolean(perfectParry, true);
		nbt.putBoolean(parry, false);
		nbt.putBoolean(lateParry, false);
	}
}

package dev.progames723.parry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class Variables{
	protected Variables() {}
	public static String perfectParry = "perfect_parry";//boolean
	public static String parry = "parry";//boolean
	public static String lateParry = "late_parry";//boolean
	public static String parryTicks = "parry_ticks";//int
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
	public static void parryTick(Player player){//TODO make less hardcoded
		CompoundTag nbt = thisIsImportant(player);
		int h = nbt.getInt(parryTicks);
		if (h < 0){
			nbt.putInt(parryTicks, 0);
		} else {
			if (nbt.getBoolean(perfectParry) &&
					!nbt.getBoolean(parry) &&
					!nbt.getBoolean(lateParry)){
				if (h > 1 && h <= 3){
					h--;
					nbt.putInt(parryTicks, h);
				} else {
					nbt.putInt(parryTicks, 8);
					nbt.putBoolean(perfectParry, false);
					nbt.putBoolean(parry, true);
					nbt.putBoolean(lateParry, false);
				}
			} else if ((!nbt.getBoolean(perfectParry) &&
					nbt.getBoolean(parry) &&
					!nbt.getBoolean(lateParry))) {
				if (h > 1 && h <= 8){
					h--;
					nbt.putInt(parryTicks, h);
				} else {
					nbt.putInt(parryTicks, 9);
					nbt.putBoolean(perfectParry, false);
					nbt.putBoolean(parry, false);
					nbt.putBoolean(lateParry, true);
				}
			} else if ((!nbt.getBoolean(perfectParry) &&
					!nbt.getBoolean(parry) &&
					nbt.getBoolean(lateParry))) {
				if (h > 1 && h <= 9){
					h--;
					nbt.putInt(parryTicks, h);
				} else {
					nbt.putInt(parryTicks, 0);
					nbt.putBoolean(perfectParry, false);
					nbt.putBoolean(parry, false);
					nbt.putBoolean(lateParry, false);
				}
			}
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
		if (nbt.getInt(parryTicks) != 0){
			nbt.putInt(parryTicks, 0);
		}
	}
	public static void initializeVar(Player player){
		CompoundTag nbt = thisIsImportant(player);
		if (!nbt.getBoolean((isNew))){
			nbt.putInt(parryTicks, 0);
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
			nbt.putInt(parryTicks, 0);
			nbt.putBoolean(perfectParry, false);
			nbt.putBoolean(parry, false);
			nbt.putBoolean(lateParry, false);
			nbt.putDouble(vulnerable, 0.0);
			nbt.putDouble(resistant, 0.0);
			nbt.putBoolean(isNew, true);
		}
	}

	public static void setTicks(Player player){//TODO make less hardcoded
		CompoundTag nbt = thisIsImportant(player);
		nbt.putInt(parryTicks, 3);
		nbt.putBoolean(perfectParry, true);
		nbt.putBoolean(parry, false);
		nbt.putBoolean(lateParry, false);
	}
}

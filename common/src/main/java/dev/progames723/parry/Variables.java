package dev.progames723.parry;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Variables {
	public static HashMap<Player, Boolean> perfectParry = new HashMap<>();
	public static HashMap<Player, Boolean> parry = new HashMap<>();
	public static HashMap<Player, Boolean> lateParry = new HashMap<>();
	public static HashMap<Player, Integer> parryTicks = new HashMap<>();
	public static HashMap<Player, Runnable> theH = new HashMap<>();
	public static HashMap<Player, Double> resistant = new HashMap<>();
	public static HashMap<Player, Double> vulnerable = new HashMap<>();
	public static void changeHashmapValues(Player player){
		Variables.vulnerable.putIfAbsent(player, 0.0);
		Variables.resistant.putIfAbsent(player, 0.0);
		if (Variables.vulnerable.get(player) != null && Variables.resistant.get(player) != null){
			if (resistant.get(player) > 100.0){
				resistant.put(player, 100.0);
			}
			if (resistant.get(player) < 0.0){
				resistant.put(player, 0.0);
			}
			if (vulnerable.get(player) < 0.0){
				vulnerable.put(player, 0.0);
			}
		}
	}
	public static void parryTick(Player player){
		Variables.vulnerable.putIfAbsent(player, 0.0);
		Variables.resistant.putIfAbsent(player, 0.0);
		if (Variables.vulnerable.get(player) != null && Variables.resistant.get(player) != null){
			int h = parryTicks.get(player);
			if (h <= 0){
				if (theH.get(player) != null){
					theH.get(player).run();
				} else {
					return;
				}
				theH.put(player, null);
			} else {
				parryTicks.put(player, h-1);
			}
		}
	}
	public static void setTicks(Player player){
		parryTicks.put(player, 3);
		perfectParry.put(player, true);
		parry.put(player, false);
		lateParry.put(player, false);

		theH.put(player, () -> {
			perfectParry.put(player, false);
			parry.put(player, true);
			lateParry.put(player, false);
			parryTicks.put(player, 8);

			theH.put(player, () -> {
				perfectParry.put(player, false);
				parry.put(player, false);
				lateParry.put(player, true);
				parryTicks.put(player, 9);

				theH.put(player, () -> {
					perfectParry.put(player, false);
					parry.put(player, false);
					lateParry.put(player, false);
				});
			});
		});
	}
}

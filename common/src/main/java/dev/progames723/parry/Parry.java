package dev.progames723.parry;

import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parry
{
	public static final String MOD_ID = "parry_this";
	public static final Logger LOGGER = LoggerFactory.getLogger("Parry");

	public static void init() {
		TickEvent.PLAYER_PRE.register((player) -> {
			Variables.changeHashmapValues(player);
			Variables.parryTick(player);
		});
	}
}

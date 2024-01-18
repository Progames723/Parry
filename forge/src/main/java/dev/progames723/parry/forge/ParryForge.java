package dev.progames723.parry.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.progames723.parry.Parry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Parry.MOD_ID)
public class ParryForge {
    public ParryForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Parry.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Parry.init();
    }
}
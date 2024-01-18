package dev.progames723.parry.fabric;

import dev.progames723.parry.Parry;
import net.fabricmc.api.ModInitializer;

public class ParryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Parry.init();
    }
}
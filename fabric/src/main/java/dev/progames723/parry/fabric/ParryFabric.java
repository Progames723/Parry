package dev.progames723.parry.fabric;

import dev.architectury.event.events.common.TickEvent;
import dev.progames723.parry.Parry;
import dev.progames723.parry.Variables;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ParryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Parry.init();
        TickEvent.PLAYER_PRE.register((player) -> {
            Variables.changeHashmapValues(player);
            Variables.parryTick(player);
        });
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, level) -> {
            if (entity instanceof Player player){
                CompoundTag nbt = player.saveWithoutId(new CompoundTag());
                Variables.perfectParry.put(player, false);
                Variables.parry.put(player, false);
                Variables.lateParry.put(player, false);
                Variables.parryTicks.put(player, 0);
                Variables.theH.put(player, null);
                double v = Variables.vulnerable.get(player);
                double r = Variables.resistant.get(player);
                nbt.putDouble("vulnerable", v);
                nbt.putDouble("resistant", r);
            }
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof Player player){
                Variables.resistant.get(player);
                CompoundTag nbt = player.saveWithoutId(new CompoundTag());
                Variables.vulnerable.put(player, nbt.getDouble("vulnerable"));
                Variables.resistant.put(player, nbt.getDouble("resistant"));
            }
        });
    }
}
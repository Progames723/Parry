package dev.progames723.parry.fabric;

import dev.architectury.event.events.common.TickEvent;
import dev.progames723.hmmm.fabric.event.LivingEvents;
import dev.progames723.parry.Parry;
import dev.progames723.parry.Variables;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ParryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Parry.init();
        LivingEvents.BEFORE_LIVING_HURT.register((level, entity, source, amount) -> {
            if (entity instanceof Player player){
                if (Variables.perfectParry.get(player) && !Variables.parry.get(player) && !Variables.lateParry.get(player)){
                    return 0;
                } else if (!Variables.perfectParry.get(player) && Variables.parry.get(player) && !Variables.lateParry.get(player)){
                    float number = (float) Math.pow(amount, 0.25) + (float) Math.pow(amount, 0.875)*0.12f;
                    if (number > 200.0f){
                        number = 200.0f;
                    } else if (number < 0f) {
                        number = 0f;
                    }
                    return number;
                } else if (!Variables.perfectParry.get(player) && !Variables.parry.get(player) && Variables.lateParry.get(player)){
                    float number = (float) Math.pow(amount, 1.3)*1.5f;
                    if (number > 400.0f){
                        number = 400.0f;
                    } else if (number < 0f) {
                        number = 0f;
                    }
                    return number;
                }
            }
            return amount;
        });
        UseItemCallback.EVENT.register((player, level, hand) -> {
            Item item = player.getMainHandItem().getItem();
            ItemStack item2 = player.getOffhandItem();
            if (!player.isSpectator()){
                if (hand == InteractionHand.MAIN_HAND){
                    if (player.getItemInHand(hand).is(ItemTags.SWORDS)){
                        if (
                                !item2.is(ConventionalItemTags.BOWS) &&
                                !item2.is(ConventionalItemTags.SHIELDS) &&
                                !item2.is(ConventionalItemTags.SPEARS) &&
                                !item2.is(ItemTags.SWORDS)
                        ){
                            player.getCooldowns().addCooldown(item, 20);
                            Variables.setTicks(player);
                        }
                    }
                }
            }
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        });
    }
}
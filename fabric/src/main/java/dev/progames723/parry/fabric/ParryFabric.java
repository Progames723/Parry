package dev.progames723.parry.fabric;

import dev.progames723.hmmm.HmmmDamageTypes;
import dev.progames723.hmmm.fabric.event.LivingEvents;
import dev.progames723.parry.Parry;
import dev.progames723.parry.Variables;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
                CompoundTag nbt = Variables.thisIsImportant(player);
                if (!source.is(HmmmDamageTypes.BLEED) && !source.is(HmmmDamageTypes.PIERCING)){
                    if (!nbt.getBoolean(Variables.perfectParry) &&
                            nbt.getBoolean(Variables.parry) &&
                            !nbt.getBoolean(Variables.lateParry)){
                        float number = (float) Math.pow(amount, 0.25) + (float) Math.pow(amount, 0.875)*0.12f;
                        if (number > 200.0f){
                            number = 200.0f;
                        } else if (number < 0f) {
                            number = 0f;
                        }
                        level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BLOCK, SoundSource.MASTER, 1, 1);
                        return number;
                    }
                }
                if (!nbt.getBoolean(Variables.perfectParry) &&
                        !nbt.getBoolean(Variables.parry) &&
                        nbt.getBoolean(Variables.lateParry)){
                    float number = (float) Math.pow(amount, 1.3)*1.5f;
                    if (number > 400.0f){
                        number = 400.0f;
                    } else if (number < 0f) {
                        number = 0f;
                    }
                    level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BREAK, SoundSource.MASTER, 1, 1);
                    return number;
                }
            }
            return amount;
        });
        LivingEvents.BEFORE_LIVING_HURT_CANCELLABLE.register((level, entity, source, amount) -> {
            if (entity instanceof Player player){
                CompoundTag nbt = Variables.thisIsImportant(player);
                if (!source.is(HmmmDamageTypes.PIERCING)){
                    if (nbt.getBoolean(Variables.perfectParry) &&
                            !nbt.getBoolean(Variables.parry) &&
                            !nbt.getBoolean(Variables.lateParry)){
                        level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BLOCK, SoundSource.MASTER, 1, 1);
                        player.setAbsorptionAmount(player.getAbsorptionAmount() + 2.0f);
                        return false;
                    }
                }
            }
            if (amount <= 0){
                return false;
            }
            return true;
        });
        UseItemCallback.EVENT.register((player, level, hand) -> {
            Item item = player.getMainHandItem().getItem();
            ItemStack item2 = player.getOffhandItem();
            if (!player.isSpectator()){
                if (hand == InteractionHand.MAIN_HAND){
                    if (player.getItemInHand(hand).is(ItemTags.SWORDS) && !player.getCooldowns().isOnCooldown(item)){
                        if (
                                !item2.is(ConventionalItemTags.BOWS) &&
                                        !item2.is(ConventionalItemTags.SHIELDS) &&
                                        !item2.is(ConventionalItemTags.SPEARS) &&
                                        !item2.is(ItemTags.SWORDS)
                        ){
                            Variables.setTicks(player);
                            level.playSound(null, player.getOnPos(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.MASTER, 1, 1);
                        }
                    }
                }
            }
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        });
        DynamicRegistrySetupCallback.EVENT.register((dynamicRegistryView) -> {
            dynamicRegistryView.asDynamicRegistryManager();
        });
    }
}
package dev.progames723.parry.fabric;

import dev.progames723.hmmm.HmmmDamageTypes;
import dev.progames723.hmmm.fabric.event.LivingEvents;
import dev.progames723.parry.EffectThing;
import dev.progames723.parry.Parry;
import dev.progames723.parry.Variables;
import dev.progames723.parry.enchantments.ParryEnchantments;
import dev.progames723.parry.enchantments.ReturnEnchantment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.fabricmc.fabric.mixin.gamerule.GameRulesAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;

public class ParryFabric implements ModInitializer {
    private void reduceDurability(ItemStack item, int amount){
        item.setDamageValue(item.getDamageValue() + amount);
    }
    @Override
    public void onInitialize() {
        Parry.init();
        LivingEvents.BEFORE_LIVING_HURT.register((level, entity, source, amount) -> {
            if (entity instanceof LivingEntity living){
                if (EffectThing.chanceForArmorReduction(living, source, amount, source.getEntity())){
                    EffectThing.applyArmorReduction(living);
                }
            }
            if (entity instanceof Player player){
                CompoundTag nbt = Variables.thisIsImportant(player);
                if (!source.is(HmmmDamageTypes.BLEED) && !source.is(HmmmDamageTypes.PIERCING)){
                    if (!nbt.getBoolean(Variables.perfectParry) &&
                            nbt.getBoolean(Variables.parry) &&
                            !nbt.getBoolean(Variables.lateParry)){
                        float number = (float) Math.pow(amount, 0.25) + (float) Math.pow(amount, 0.875)*0.12f;
                        if (number < 0f) {
                            number = 0f;
                        }
                        ItemStack item = player.getInventory().getSelected();
                        if (item.isEnchanted() && item.is(ItemTags.SWORDS)){
                            int a = EnchantmentHelper.getItemEnchantmentLevel(ParryEnchantments.RETURN, item);
                            if (a > 0){
                                if (a <= 1){
                                    a = 1;
                                } else {
                                    a = 2;
                                }
                                double h = Math.pow(amount-number, 0.15*a)+item.getOrCreateTagElement("parry_data").getDouble("damage_boost");
                                if (h > 30){
                                    h = 30;
                                } else if (h < 0) {
                                    h = 0;
                                }
                                item.getOrCreateTagElement("parry_data").putDouble("damage_boost", h);
                                reduceDurability(item, (int) Math.abs(Math.pow(amount, 0.3)));
                            }
                        }
                        level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BLOCK, SoundSource.MASTER, 1, 1);
                        return number;
                    }
                }
                if (!nbt.getBoolean(Variables.perfectParry) &&
                        !nbt.getBoolean(Variables.parry) &&
                        nbt.getBoolean(Variables.lateParry)){
                    float number = (float) Math.pow(amount, 1.3)*1.5f;
                    if (number < 0f) {
                        number = 0f;
                    }
                    level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BREAK, SoundSource.MASTER, 1, 1);
                    return number;
                }
            }
            return amount;
        });
        LivingEvents.BEFORE_LIVING_HURT_CANCELLABLE.register((level, entity, source, amount) -> {
            Entity attacker = source.getDirectEntity();
            if (source.getDirectEntity() != null){
                attacker = source.getEntity();
            }
            if (entity instanceof LivingEntity living) {
                EffectThing.chanceForArmorReduction(living, source, amount, attacker);
            }
            if (entity instanceof Player player){
                CompoundTag nbt = Variables.thisIsImportant(player);
                if (!source.is(HmmmDamageTypes.PIERCING)){
                    if (nbt.getBoolean(Variables.perfectParry) &&
                            !nbt.getBoolean(Variables.parry) &&
                            !nbt.getBoolean(Variables.lateParry)){
                        ItemStack item = player.getInventory().getSelected();
                        if (item.isEnchanted() && item.is(ItemTags.SWORDS)){
                            int a = EnchantmentHelper.getItemEnchantmentLevel(ParryEnchantments.RETURN, item);
                            if (a > 0){
                                if (a <= 1){
                                    a = 1;
                                } else {
                                    a = 2;
                                }
                                double h = Math.pow(amount, 0.15*a)+item.getOrCreateTagElement("parry_data").getDouble("damage_boost");
                                if (h > 30){
                                    h = 30;
                                } else if (h < 0) {
                                    h = 0;
                                }
                                item.getOrCreateTagElement("parry_data").putDouble("damage_boost", h);
                                reduceDurability(item, (int) Math.abs(Math.pow(amount, 0.15)));
                            }
                        }
                        level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BLOCK, SoundSource.MASTER, 1, 1);
                        player.setAbsorptionAmount(player.getAbsorptionAmount()+ 2.0f);
                        EffectThing.reduceArmorReductionEffect(player);
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
                            Variables.setTicks(player,
                                    level.getGameRules().getInt(Parry.perfectParryTicksGameRule),
                                    level.getGameRules().getInt(Parry.parryTicksGameRule),
                                    level.getGameRules().getInt(Parry.lateParryTicksGameRule));
                            level.playSound(null, player.getOnPos(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.MASTER, 1, 1);
                        }
                    }
                }
            }
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        });
    }
}
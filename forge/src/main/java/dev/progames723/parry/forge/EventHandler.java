package dev.progames723.parry.forge;

import dev.progames723.hmmm.HmmmDamageTypes;
import dev.progames723.parry.EffectThing;
import dev.progames723.parry.Variables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber
public class EventHandler{
	@SubscribeEvent
	public static void onPlayerUseItem(PlayerInteractEvent.RightClickItem e){
		Player player = e.getEntity();
		InteractionHand hand = e.getHand();
		Level level = e.getLevel();
		Item item = player.getMainHandItem().getItem();
		ItemStack item2 = player.getOffhandItem();
		if (!player.isSpectator()){
			if (hand == InteractionHand.MAIN_HAND){
				if (player.getItemInHand(hand).is(ItemTags.SWORDS) && !player.getCooldowns().isOnCooldown(item)){
					if (
							!item2.is(Tags.Items.TOOLS_BOWS) &&
							!item2.is(Tags.Items.TOOLS_SHIELDS) &&
							!item2.is(Tags.Items.TOOLS_TRIDENTS) &&
							!item2.is(ItemTags.SWORDS)
					){
						level.playSound(null, player.getOnPos(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.MASTER, 1, 1);
						Variables.setTicks(player);
					}
				}
			}
		}
	}
	@SubscribeEvent
	public static void beforeLivingHurt(LivingHurtEvent e){
		LivingEntity entity = e.getEntity();
		DamageSource source = e.getSource();
		float amount = e.getAmount();
		if (EffectThing.chanceForArmorReduction(entity, source, amount, source.getEntity())){
			EffectThing.applyArmorReduction(entity);
		}
		if (entity instanceof Player player){
			float number = 0f;
			Level level = player.level();
			CompoundTag nbt = Variables.thisIsImportant(player);
			//you can still parry BLEED but not PIERCING
			if (!source.is(HmmmDamageTypes.BLEED) && !source.is(HmmmDamageTypes.PIERCING)){
				if (nbt.getBoolean(Variables.perfectParry) &&
						!nbt.getBoolean(Variables.parry) &&
						!nbt.getBoolean(Variables.lateParry)){
					level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BLOCK, SoundSource.MASTER, 1, 1);
					e.setCanceled(true);
				}
			}
			//no damage reduction if DamageSource is PIERCING or BLEED
			if (!source.is(HmmmDamageTypes.BLEED) && !source.is(HmmmDamageTypes.PIERCING)) {
				if (!nbt.getBoolean(Variables.perfectParry) &&
						nbt.getBoolean(Variables.parry) &&
						!nbt.getBoolean(Variables.lateParry)){
					number = (float) Math.pow(amount, 0.25) + (float) Math.pow(amount, 0.875)*0.12f;
					if (number < 0f) {
						number = 0f;
					}
					level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BLOCK, SoundSource.MASTER, 1, 1);
					e.setAmount(number);
				}
			} else {//damage is still increased from any DamageSource or DamageType
				if (!nbt.getBoolean(Variables.perfectParry) &&
						!nbt.getBoolean(Variables.parry) &&
						nbt.getBoolean(Variables.lateParry)){
					number = (float) Math.pow(amount, 1.3)*1.5f;
					if (number < 0f) {
						number = 0f;
					}
					level.playSound(null, player.getOnPos(), SoundEvents.SHIELD_BLOCK, SoundSource.MASTER, 1, 1);
					e.setAmount(number);
				}
			}
			if (number <= 0f){
				e.setCanceled(true);
			}
		}
	}
}

package dev.progames723.parry.mixin;

import dev.progames723.parry.enchantments.ParryEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	//this.age = this.tickCount
	//this is stupid
	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}
	@ModifyVariable(
			method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
			at = @At(value = "HEAD"),
			argsOnly = true
	)
	private float damageBoost(float amount, DamageSource source) {
		if (source.getEntity() != null && !source.isIndirect()){
			LivingEntity entity = (LivingEntity) (Object) this;
			Entity attacker = source.getEntity();
			if (attacker instanceof Player player) {
				ItemStack test = player.getMainHandItem();
				if (test.is(ItemTags.SWORDS) && EnchantmentHelper.getItemEnchantmentLevel(ParryEnchantments.RETURN, test) > 0){
					CompoundTag h = test.getTagElement("parry_data");
					if (!Objects.isNull(h.getDouble("damage_boost"))) {
						float a = (float) h.getDouble("damage_boost");
						h.putDouble("damage_boost", 0);
						return amount + a;
					}
				}
			}
		}
		return amount;
	}
}

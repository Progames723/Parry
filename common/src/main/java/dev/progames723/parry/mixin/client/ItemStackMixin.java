package dev.progames723.parry.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.progames723.parry.enchantments.ParryEnchantments;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public class ItemStackMixin {
	@ModifyExpressionValue(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDamageBonus(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/MobType;)F"))
	private float test(float value) {
		ItemStack the = (ItemStack) (Object) this;
		if (the.is(ItemTags.SWORDS) && EnchantmentHelper.getItemEnchantmentLevel(ParryEnchantments.RETURN, the) > 0){
			return value + (float) the.getOrCreateTagElement("parry_data").getDouble("damage_boost");
		}
		return value;
	}
}

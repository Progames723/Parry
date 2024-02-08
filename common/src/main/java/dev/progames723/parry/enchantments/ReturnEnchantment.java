package dev.progames723.parry.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ReturnEnchantment extends Enchantment {
	protected ReturnEnchantment(Enchantment.Rarity rarity, EquipmentSlot... equipmentSlots) {
		super(rarity, EnchantmentCategory.WEAPON, equipmentSlots);
	}
	public int getMaxLevel() {
		return 2;
	}
	public int getMinCost(int i) {
		return 5 + (i * 11) - (i - 2);
	}
	public int getMaxCost(int i) {
		return this.getMinCost(i) + 40;
	}
	public float getDamageBonus(int i, MobType mobType){
		if (i <= 1){
			return 1.0f;
		} else {
			return 1.5f;
		}
	}
}

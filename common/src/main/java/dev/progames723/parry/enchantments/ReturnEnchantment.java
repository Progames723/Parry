package dev.progames723.parry.enchantments;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class ReturnEnchantment extends Enchantment {
	protected ReturnEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
		super(rarity, EnchantmentCategory.WEAPON, equipmentSlots);
	}
	@Override
	public int getMaxLevel() {
		return 2;
	}
	@Override
	public int getMinCost(int i) {
		return 5 + (i * 11) - (i - 2);
	}
	@Override
	public int getMaxCost(int i) {
		return this.getMinCost(i) + 40;
	}
	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		if (enchantment.getDamageBonus(enchantment.getMinLevel(), MobType.WATER) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMinLevel(), MobType.UNDEAD) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMinLevel(), MobType.ARTHROPOD) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMinLevel(), MobType.ILLAGER) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMaxLevel(), null) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMinLevel(), MobType.UNDEFINED) > 0.0f){return false;}
		
		if (enchantment.getDamageBonus(enchantment.getMaxLevel(), MobType.WATER) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMaxLevel(), MobType.UNDEAD) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMaxLevel(), MobType.ARTHROPOD) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMaxLevel(), MobType.ILLAGER) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMaxLevel(), null) > 0.0f){return false;
		} else if (enchantment.getDamageBonus(enchantment.getMaxLevel(), MobType.UNDEFINED) > 0.0f){return false;}
		return this != enchantment;
	}
	@Override
	public float getDamageBonus(int i, MobType mobType){
		return 1.0f;
	}
}

package dev.progames723.parry.enchantments;

import dev.progames723.parry.Parry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class ParryEnchantments {
	public static Enchantment RETURN;
	private static Enchantment register(String name, Enchantment enchantment) {
		return Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(Parry.MOD_ID, name), enchantment);
	}

	public static void init() {
		RETURN = register("return", new ReturnEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
	}
}

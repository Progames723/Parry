package dev.progames723.parry;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parry
{
	public static final String MOD_ID = "parry_this";
	public static final Logger LOGGER = LoggerFactory.getLogger("Parry");

	public static void init() {
		InteractionEvent.RIGHT_CLICK_ITEM.register((player, hand) -> {
			Item item = player.getMainHandItem().getItem();
			ItemStack item2 = player.getOffhandItem();
			if (hand == InteractionHand.MAIN_HAND){
				if (player.getItemInHand(hand).is(ItemTags.SWORDS)){
					player.getCooldowns().addCooldown(item, 20);
				}
			}
			return CompoundEventResult.pass();
		});
	}
}

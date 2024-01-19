package dev.progames723.parry.forge;

import dev.progames723.parry.Variables;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {
	@SubscribeEvent
	public static void onPlayerUseItem(PlayerInteractEvent.RightClickItem e){
		Player player = e.getEntity();
		InteractionHand hand = e.getHand();
		Item item = player.getMainHandItem().getItem();
		ItemStack item2 = player.getOffhandItem();
		if (hand == InteractionHand.MAIN_HAND){
			if (player.getItemInHand(hand).is(ItemTags.SWORDS)){
				if (
						!item2.is(Tags.Items.TOOLS_BOWS) &&
						!item2.is(Tags.Items.TOOLS_CROSSBOWS) &&
						!item2.is(Tags.Items.TOOLS_SHIELDS) &&
						!item2.is(Tags.Items.TOOLS_FISHING_RODS) &&
						!item2.is(Tags.Items.TOOLS_TRIDENTS) &&
						!item2.is(ItemTags.SWORDS)
				){
					player.getCooldowns().addCooldown(item, 20);
					Variables.setTicks(player);
				}
			}
		}
	}
	@SubscribeEvent
	public static void beforeEntityLeaves(EntityLeaveLevelEvent e){
		if (e.getEntity() instanceof Player player){
			//code here
		}
	}
}

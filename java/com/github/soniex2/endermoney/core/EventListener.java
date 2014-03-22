package com.github.soniex2.endermoney.core;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;

import com.github.soniex2.endermoney.trading.helper.inventory.IterableInventoryWrapper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventListener {

	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer) {
			@SuppressWarnings("unused")
			NBTTagCompound tag = ((EntityPlayer) event.entity).getEntityData();

		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (event.block == Blocks.chest) {
			TileEntity t = event.world.getTileEntity(event.x, event.y, event.z);
			if (t != null && t instanceof TileEntityChest) {
				IterableInventoryWrapper iiw = new IterableInventoryWrapper(
						(TileEntityChest) t);
				for (ItemStack i : iiw) {
					if (i == null
							|| i.stackSize < 64
							|| i.getItem() != Item
									.getItemFromBlock(Blocks.chest)) {
						return;
					}
				}
				for (int i = 0; i < iiw.getSizeInventory(); i++) {
					iiw.setInventorySlotContents(i, null);
				}
				event.world.setTileEntity(event.x, event.y, event.z, null);
				event.world.setBlockToAir(event.x, event.y, event.z);
				EntityItem item = new EntityItem(event.world, event.x + 0.5,
						event.y + 0.5, event.z + 0.5,
						EnderMoney.chestception.getItemStack());
				item.forceSpawn = true;
				item.lifespan = Integer.MAX_VALUE;
				event.world.spawnEntityInWorld(item);
				event.setCanceled(true);
				event.setResult(Result.DENY);
			}
		}
	}
}

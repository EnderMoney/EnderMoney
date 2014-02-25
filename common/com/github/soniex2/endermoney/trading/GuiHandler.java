package com.github.soniex2.endermoney.trading;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.github.soniex2.endermoney.trading.base.AbstractTraderTileEntity;
import com.github.soniex2.endermoney.trading.container.ContainerCreativeItemTrader;
import com.github.soniex2.endermoney.trading.container.ContainerItemTrader;
import com.github.soniex2.endermoney.trading.gui.GuiCreativeItemTrader;
import com.github.soniex2.endermoney.trading.gui.GuiItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityItemTrader;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IGuiHandler;

@SuppressWarnings("boxing")
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof AbstractTraderTileEntity) {
			switch (id) {
			case 0:
				return new ContainerCreativeItemTrader(player.inventory,
						(TileEntityCreativeItemTrader) tileEntity,
						player.getCommandSenderName());
			case 1:
				return new ContainerItemTrader(player.inventory,
						(TileEntityItemTrader) tileEntity,
						player.getCommandSenderName());
			default:
				FMLLog.warning("Invalid GUI Element ID: %s", id);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof AbstractTraderTileEntity) {
			switch (id) {
			case 0:
				return new GuiCreativeItemTrader(player.inventory,
						(TileEntityCreativeItemTrader) tileEntity,
						player.getCommandSenderName());
			case 1:
				return new GuiItemTrader(player.inventory,
						(TileEntityItemTrader) tileEntity,
						player.getCommandSenderName());
			default:
				FMLLog.warning("Invalid GUI Element ID: %s", id);
			}
		}
		return null;
	}

}

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

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof AbstractTraderTileEntity) {
			switch (ID) {
				case 0:
					return new ContainerCreativeItemTrader(player.inventory,
							(TileEntityCreativeItemTrader) tileEntity, player.username);
				case 1:
					return new ContainerItemTrader(player.inventory,
							(TileEntityItemTrader) tileEntity, player.username);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof AbstractTraderTileEntity) {
			switch (ID) {
				case 0:
					return new GuiCreativeItemTrader(player.inventory,
							(TileEntityCreativeItemTrader) tileEntity, player.username);
				case 1:
					return new GuiItemTrader(player.inventory,
							(TileEntityItemTrader) tileEntity, player.username);
			}
		}
		return null;
	}

}

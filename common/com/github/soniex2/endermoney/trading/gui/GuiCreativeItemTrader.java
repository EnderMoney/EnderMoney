package com.github.soniex2.endermoney.trading.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.InventoryPlayer;

import com.github.soniex2.endermoney.trading.base.AbstractTraderGui;
import com.github.soniex2.endermoney.trading.container.ContainerCreativeItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;

public class GuiCreativeItemTrader extends AbstractTraderGui {
	
	@SuppressWarnings("unused")
	private String playerName;

	public GuiCreativeItemTrader(InventoryPlayer inventoryPlayer, TileEntityCreativeItemTrader tileEntity, String playerName) {
		super(new ContainerCreativeItemTrader(inventoryPlayer, tileEntity, playerName));
		ySize = 204;
		this.playerName = playerName;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/mods/endermoneytrading/textures/gui/itemtrader.png");
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}

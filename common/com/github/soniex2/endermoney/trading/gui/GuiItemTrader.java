package com.github.soniex2.endermoney.trading.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.InventoryPlayer;

import com.github.soniex2.endermoney.trading.EnderMoneyTrading;
import com.github.soniex2.endermoney.trading.base.AbstractTraderGui;
import com.github.soniex2.endermoney.trading.container.ContainerItemTrader;
import com.github.soniex2.endermoney.trading.packet.TradePacket;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityItemTrader;

public class GuiItemTrader extends AbstractTraderGui {

	@SuppressWarnings("unused")
	private String playerName;

	public GuiItemTrader(InventoryPlayer inventoryPlayer,
			TileEntityItemTrader tileEntity, String playerName) {
		super(new ContainerItemTrader(inventoryPlayer, tileEntity, playerName));
		ySize = 204;
		this.playerName = playerName;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButtonArrow(1, guiLeft + 76, guiTop + 25));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 1:
			EnderMoneyTrading.packetPipe.sendToServer(new TradePacket());
			break;
		default:
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		ResourceLocation rs = new ResourceLocation("endermoneytrading",
				"textures/gui/itemtrader.png");
		this.mc.getTextureManager().bindTexture(rs);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}

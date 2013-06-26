package com.github.soniex2.endermoney.trading.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.github.soniex2.endermoney.trading.base.AbstractTraderGui;
import com.github.soniex2.endermoney.trading.container.ContainerCreativeItemTrader;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityCreativeItemTrader;

import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiCreativeItemTrader extends AbstractTraderGui {

	@SuppressWarnings("unused")
	private String playerName;

	private int xPos, yPos, zPos;

	public GuiCreativeItemTrader(InventoryPlayer inventoryPlayer,
			TileEntityCreativeItemTrader tileEntity, String playerName) {
		super(new ContainerCreativeItemTrader(inventoryPlayer, tileEntity, playerName));
		xPos = tileEntity.xCoord;
		yPos = tileEntity.yCoord;
		zPos = tileEntity.zCoord;
		ySize = 204;
		this.playerName = playerName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButtonArrow(1, guiLeft+76, guiTop+25));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 1:
				ByteArrayOutputStream baos = new ByteArrayOutputStream(36);
				DataOutputStream dos = new DataOutputStream(baos);
				try {
					dos.writeInt(xPos);
					dos.writeShort((short) yPos);
					dos.writeInt(zPos);
					dos.writeUTF("CreativeItem");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Packet250CustomPayload packet = new Packet250CustomPayload("EMTrading",
						baos.toByteArray());
				PacketDispatcher.sendPacketToServer(packet);
				break;
		}
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

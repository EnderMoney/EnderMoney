package com.github.soniex2.endermoney.trading.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonArrow extends GuiButton {

	public GuiButtonArrow(int par1, int par2, int par3) {
		super(par1, par2, par3, 23, 15, "");

	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (this.visible) {
			ResourceLocation rs = new ResourceLocation("endermoneytrading",
					"textures/gui/itemtrader.png");
			par1Minecraft.getTextureManager().bindTexture(rs);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean flag = par2 >= this.xPosition && par3 >= this.yPosition
					&& par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int k = 0;
			int l = 176;

			if (!this.enabled) {
				return;
			} else if (flag) {
				k = 32;
			}

			this.drawTexturedModalRect(this.xPosition, this.yPosition, l, k, this.width,
					this.height);
		}
	}
}

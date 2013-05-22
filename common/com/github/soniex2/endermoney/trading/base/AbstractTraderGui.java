package com.github.soniex2.endermoney.trading.base;

import net.minecraft.client.gui.inventory.GuiContainer;

public abstract class AbstractTraderGui extends GuiContainer {

	protected AbstractTraderGui(AbstractTraderContainer container) {
		super(container);
	}

	@Override
	protected abstract void drawGuiContainerForegroundLayer(int par1, int par2);

	@Override
	protected abstract void drawGuiContainerBackgroundLayer(float par1, int par2, int par3);

}

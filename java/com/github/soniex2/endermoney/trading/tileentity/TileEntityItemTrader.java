package com.github.soniex2.endermoney.trading.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.github.soniex2.endermoney.trading.base.AbstractTraderTileEntity;

public class TileEntityItemTrader extends AbstractTraderTileEntity {

	public TileEntityItemTrader() {
		super(18);
	}

	@Override
	public ItemStack[] getTradeInputs() {
		ItemStack[] tradeInputs = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			tradeInputs[i] = ItemStack.copyItemStack(inv[i]);
		}
		return tradeInputs;
	}

	@Override
	public ItemStack[] getTradeOutputs() {
		ItemStack[] tradeOutputs = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			tradeOutputs[i] = ItemStack.copyItemStack(inv[i + 9]);
		}
		return tradeOutputs;
	}

	@Override
	public String getInventoryName() {
		return "endermoney.traders.item";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public TradeStatus doTrade(IInventory fakeInv, int inputMinSlot,
			int inputMaxSlot, int outputMinSlot, int outputMaxSlot,
			boolean really) {
		return TradeStatus.INVALID;
	}

}

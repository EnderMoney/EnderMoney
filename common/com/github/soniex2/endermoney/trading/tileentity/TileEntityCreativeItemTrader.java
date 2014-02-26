package com.github.soniex2.endermoney.trading.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;

import com.github.soniex2.endermoney.trading.base.AbstractTraderTileEntity;
import com.github.soniex2.endermoney.trading.exception.TradeException;
import com.github.soniex2.endermoney.trading.helper.inventory.IterableInventoryWrapper;
import com.github.soniex2.endermoney.trading.helper.item.ItemIdentifier;

import cpw.mods.fml.common.FMLLog;

public class TileEntityCreativeItemTrader extends AbstractTraderTileEntity {

	public TileEntityCreativeItemTrader() {
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
	public boolean canTrade(IInventory fakeInv, int inputMinSlot,
			int inputMaxSlot, int outputMinSlot, int outputMaxSlot) {
		return false;
	}

	@Override
	public boolean doTrade(IInventory fakeInv, int inputMinSlot,
			int inputMaxSlot, int outputMinSlot, int outputMaxSlot)
			throws TradeException {
		if (this.worldObj.isRemote)
			return false;
		if (fakeInv == null) {
			FMLLog.severe("Please send the following stack trace to SoniEx2:\n"
					+ "%s\n" + "==================== END ====================",
					org.apache.commons.lang3.exception.ExceptionUtils
							.getStackTrace(new Exception()));
			return false;
		}
		if (!canTrade(fakeInv, inputMinSlot, inputMaxSlot, outputMinSlot,
				outputMaxSlot)) {
			return false;
		}

		return false;
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

}

package com.github.soniex2.endermoney.trading.tileentity;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

import com.github.soniex2.endermoney.trading.base.AbstractTraderTileEntity;
import com.github.soniex2.endermoney.trading.exception.TradeException;
import com.github.soniex2.endermoney.trading.helper.inventory.IterableInventoryWrapper;
import com.github.soniex2.endermoney.trading.helper.item.ItemIdentifier;

import cpw.mods.fml.common.FMLLog;

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
	public boolean canTrade(IInventory fakeInv, int inputMinSlot,
			int inputMaxSlot, int outputMinSlot, int outputMaxSlot) {
		if (this.worldObj.isRemote)
			return false;
		if (fakeInv == null)
			return false;

		ItemStack[] inp = getTradeInputs();

		int[][] indx = new int[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 },
				{ -1, 0, 0 }, { 0, -1, 0 }, { 0, 0, -1 } };
		ArrayList<IInventory> invs = new ArrayList<IInventory>();
		for (int[] i : indx) {
			TileEntity te = this.worldObj.getTileEntity(this.xCoord + i[0],
					this.yCoord + i[1], this.zCoord + i[2]);
			if (te == null || !(te instanceof IInventory)) {
				continue;
			}
			invs.add((IInventory) te);
			if (te instanceof TileEntityChest) {
				if (((TileEntityChest) te).adjacentChestXNeg != null) {
					invs.add(((TileEntityChest) te).adjacentChestXNeg);
				} else if (((TileEntityChest) te).adjacentChestZNeg != null) {
					invs.add(((TileEntityChest) te).adjacentChestZNeg);
				} else if (((TileEntityChest) te).adjacentChestXPos != null) {
					invs.add(((TileEntityChest) te).adjacentChestXPos);
				} else if (((TileEntityChest) te).adjacentChestZPos != null) {
					invs.add(((TileEntityChest) te).adjacentChestZPos);
				}
			}
		}

		HashMap<ItemIdentifier, Integer> map = new HashMap<ItemIdentifier, Integer>();

		for (IInventory inv : invs) {
			for (ItemStack is : new IterableInventoryWrapper(inv)) {
				if (is == null)
					continue;
				ItemIdentifier id = new ItemIdentifier(is);
				if (map.containsKey(id)) {
					map.put(id, map.get(id) + is.stackSize);
				} else {
					map.put(id, is.stackSize);
				}
			}
		}

		for (ItemStack is : inv) {
			if (is == null)
				continue;
			ItemIdentifier id = new ItemIdentifier(is);
			if (!map.containsKey(id))
				return false;
			map.put(id, map.get(id) - is.stackSize);
		}

		for (Integer i : map.values()) {
			if (i < 0)
				return false;
		}

		return true;
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

package com.github.soniex2.endermoney.trading.tileentity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.github.soniex2.endermoney.core.EnderMoney;
import com.github.soniex2.endermoney.core.item.EnderCoin;
import com.github.soniex2.endermoney.trading.base.AbstractTraderTileEntity;
import com.github.soniex2.endermoney.trading.exception.OutOfInventorySpaceException;
import com.github.soniex2.endermoney.trading.exception.TradeException;
import com.github.soniex2.endermoney.trading.helper.inventory.InventoryHelper;
import com.github.soniex2.endermoney.trading.helper.item.ItemStackMapKey;
import com.github.soniex2.endermoney.trading.helper.money.MoneyHelper;

public class TileEntityItemTrader extends AbstractTraderTileEntity {

	public TileEntityItemTrader() {
		super(18);
	}

	public ItemStack[] getTradeInputs() {
		ItemStack[] tradeInputs = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			tradeInputs[i] = ItemStack.copyItemStack(inv[i]);
		}
		return tradeInputs;
	}

	public ItemStack[] getTradeOutputs() {
		ItemStack[] tradeOutputs = new ItemStack[9];
		for (int i = 0; i < 9; i++) {
			tradeOutputs[i] = ItemStack.copyItemStack(inv[i + 9]);
		}
		return tradeOutputs;
	}

	public boolean canTrade(IInventory fakeInv, int inputMinSlot, int inputMaxSlot,
			int outputMinSlot, int outputMaxSlot) {
		HashMap<ItemStackMapKey, Integer> tradeInput = InventoryHelper
				.inventoryToHashMap(InventoryHelper.itemStackArrayToInventory(inv));
		BigInteger requiredMoney = MoneyHelper.extractFromHashMap(tradeInput);

		HashMap<ItemStackMapKey, Integer> invInput = InventoryHelper.inventoryToHashMap(fakeInv,
				inputMinSlot, inputMaxSlot);
		BigInteger availableMoney = MoneyHelper.extractFromHashMap(tradeInput);

		Set<Entry<ItemStackMapKey, Integer>> itemsRequired = tradeInput.entrySet();
		Iterator<Entry<ItemStackMapKey, Integer>> i = itemsRequired.iterator();
		while (i.hasNext()) {
			Entry<ItemStackMapKey, Integer> entry = i.next();
			ItemStackMapKey item = entry.getKey();
			Integer amount = entry.getValue();
			Integer available = invInput.get(item);
			if (available == null) {
				return false;
			}
			if (available < amount) {
				return false;
			}
		}

		BigInteger newMoney = availableMoney.subtract(requiredMoney);
		if (newMoney.signum() == -1) {
			return false;
		}
		return true;
	}

	public boolean doTrade(IInventory fakeInv, int inputMinSlot, int inputMaxSlot,
			int outputMinSlot, int outputMaxSlot) throws TradeException {
		if (fakeInv == null) {
			throw new TradeException(new NullPointerException());
		}

		HashMap<ItemStackMapKey, Integer> tradeInput = InventoryHelper
				.inventoryToHashMap(InventoryHelper.itemStackArrayToInventory(getTradeInputs()));
		BigInteger requiredMoney = MoneyHelper.extractFromHashMap(tradeInput);

		HashMap<ItemStackMapKey, Integer> invInput = InventoryHelper.inventoryToHashMap(fakeInv,
				inputMinSlot, inputMaxSlot);
		BigInteger availableMoney = MoneyHelper.extractFromHashMap(invInput);

		Set<Entry<ItemStackMapKey, Integer>> itemsRequired = tradeInput.entrySet();
		Iterator<Entry<ItemStackMapKey, Integer>> i = itemsRequired.iterator();
		while (i.hasNext()) {
			Entry<ItemStackMapKey, Integer> entry = i.next();
			ItemStackMapKey item = entry.getKey();
			Integer amount = entry.getValue();
			Integer available = invInput.get(item);
			if (available == null) {
				return false;
			}
			if (available < amount) {
				return false;
			}
			if (available - amount == 0) {
				invInput.remove(item);
				continue;
			}
			invInput.put(item, available - amount);
		}

		BigInteger newMoney = availableMoney.subtract(requiredMoney);
		if (newMoney.signum() == -1) {
			return false;
		}

		if (newMoney.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
			BigInteger[] coinCount = newMoney
					.divideAndRemainder(BigInteger.valueOf(Long.MAX_VALUE));
			int a = coinCount[0].intValue();
			long b = coinCount[1].longValue();
			ItemStack is1 = ((EnderCoin) EnderMoney.coin).getItemStack(Long.MAX_VALUE);
			ItemStack is2 = ((EnderCoin) EnderMoney.coin).getItemStack(b);
			ItemStackMapKey index1 = new ItemStackMapKey(is1);
			ItemStackMapKey index2 = new ItemStackMapKey(is2);
			invInput.put(index1, a);
			invInput.put(index2, 1);
		} else if (!newMoney.equals(BigInteger.ZERO)) {
			ItemStack is = ((EnderCoin) EnderMoney.coin).getItemStack(newMoney.longValue(), 1);
			ItemStackMapKey index = new ItemStackMapKey(is);
			invInput.put(index, 1);
		}

		ItemStack[] tradeOutputs = getTradeOutputs();
		ItemStack[] oldOutInv = new ItemStack[outputMaxSlot - outputMinSlot + 1];
		for (int a = outputMinSlot; a <= outputMaxSlot; a++) {
			oldOutInv[a - outputMinSlot] = ItemStack.copyItemStack(fakeInv.getStackInSlot(a));
		}
		for (int a = outputMinSlot; a <= outputMaxSlot; a++) {
			ItemStack is = fakeInv.getStackInSlot(a);
			for (int b = 0; b < tradeOutputs.length; b++) {
				if (is != null && tradeOutputs[b] != null && is.isItemEqual(tradeOutputs[b])
						&& ItemStack.areItemStackTagsEqual(is, tradeOutputs[b])) {
					if (is.isStackable()) {
						if (is.stackSize < is.getMaxStackSize()) {
							if (is.stackSize + tradeOutputs[b].stackSize > is.getMaxStackSize()) {
								int newStackSize = tradeOutputs[b].stackSize + is.stackSize;
								if (newStackSize > is.getMaxStackSize()) {
									newStackSize = newStackSize - is.getMaxStackSize();
								}
								tradeOutputs[b].stackSize = newStackSize;
								is.stackSize = is.getMaxStackSize();
							} else {
								is.stackSize = is.stackSize + tradeOutputs[b].stackSize;
								tradeOutputs[b] = null;
							}
						}
					}
				} else if (is == null && tradeOutputs[b] != null) {
					fakeInv.setInventorySlotContents(a, tradeOutputs[b]);
					is = fakeInv.getStackInSlot(a);
					tradeOutputs[b] = null;
				}
				if (tradeOutputs[b] != null && tradeOutputs[b].stackSize <= 0) {
					tradeOutputs[b] = null;
				}
			}
		}
		for (int a = 0; a < tradeOutputs.length; a++) {
			if (tradeOutputs[a] != null) {
				for (int b = 0; b < oldOutInv.length; b++) {
					fakeInv.setInventorySlotContents(b + outputMinSlot, oldOutInv[b]);
				}
				throw new OutOfInventorySpaceException();
			}
		}
		for (int _i = inputMinSlot; _i <= inputMaxSlot; _i++) {
			fakeInv.setInventorySlotContents(_i, null);
		}
		Set<Entry<ItemStackMapKey, Integer>> input = invInput.entrySet();
		Iterator<Entry<ItemStackMapKey, Integer>> it = input.iterator();
		int slot = inputMinSlot;
		while (it.hasNext()) {
			if (slot > inputMaxSlot) {
				throw new OutOfInventorySpaceException();
			}
			if (fakeInv.getStackInSlot(slot) != null) {
				slot++;
				continue;
			}
			Entry<ItemStackMapKey, Integer> entry = it.next();
			ItemStackMapKey itemData = entry.getKey();
			ItemStack item = new ItemStack(itemData.itemID, 1, itemData.damage);
			item.stackTagCompound = (NBTTagCompound) itemData.getTag();
			Integer amount = entry.getValue();
			if (amount == 0) { // shouldn't happen but who knows...
				continue;
			}
			int stacks = amount / item.getMaxStackSize();
			int extra = amount % item.getMaxStackSize();
			ItemStack newItem = item.copy();
			newItem.stackSize = item.getMaxStackSize();
			for (int n = slot; n < slot + stacks; n++) {
				fakeInv.setInventorySlotContents(n, newItem);
			}
			slot += stacks;
			if (extra != 0) {
				newItem = item.copy();
				newItem.stackSize = extra;
				fakeInv.setInventorySlotContents(slot, newItem);
				slot++;
			}
		}
		return true;
	}

	@Override
	public String getInvName() {
		return "endermoney.traders.item";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

}

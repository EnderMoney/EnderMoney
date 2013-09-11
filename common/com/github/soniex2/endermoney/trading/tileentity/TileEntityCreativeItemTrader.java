package com.github.soniex2.endermoney.trading.tileentity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.github.soniex2.endermoney.core.EnderMoney;
import com.github.soniex2.endermoney.core.item.EnderCoin;
import com.github.soniex2.endermoney.trading.base.AbstractTraderTileEntity;
import com.github.soniex2.endermoney.trading.exception.OutOfInventorySpaceException;
import com.github.soniex2.endermoney.trading.exception.TradeException;
import com.github.soniex2.endermoney.trading.helper.inventory.InventoryHelper;
import com.github.soniex2.endermoney.trading.helper.item.ItemIdentifier;
import com.github.soniex2.endermoney.trading.helper.money.MoneyHelper;

public class TileEntityCreativeItemTrader extends AbstractTraderTileEntity {

	public TileEntityCreativeItemTrader() {
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
		HashMap<ItemIdentifier, Integer> tradeInput = InventoryHelper
				.inventoryToHashMap(InventoryHelper.itemStackArrayToInventory(getTradeInputs()));
		BigInteger requiredMoney = MoneyHelper.extractFromHashMap(tradeInput);

		HashMap<ItemIdentifier, Integer> invInput = InventoryHelper.inventoryToHashMap(fakeInv,
				inputMinSlot, inputMaxSlot);
		BigInteger availableMoney = MoneyHelper.extractFromHashMap(tradeInput);

		Set<Entry<ItemIdentifier, Integer>> itemsRequired = tradeInput.entrySet();
		Iterator<Entry<ItemIdentifier, Integer>> i = itemsRequired.iterator();
		while (i.hasNext()) {
			Entry<ItemIdentifier, Integer> entry = i.next();
			ItemIdentifier item = entry.getKey();
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

		HashMap<ItemIdentifier, Integer> tradeInput = InventoryHelper
				.inventoryToHashMap(InventoryHelper.itemStackArrayToInventory(getTradeInputs()));
		BigInteger requiredMoney = MoneyHelper.extractFromHashMap(tradeInput);

		HashMap<ItemIdentifier, Integer> invInput = InventoryHelper.inventoryToHashMap(fakeInv,
				inputMinSlot, inputMaxSlot);
		BigInteger availableMoney = MoneyHelper.extractFromHashMap(invInput);

		Set<Entry<ItemIdentifier, Integer>> itemsRequired = tradeInput.entrySet();
		Iterator<Entry<ItemIdentifier, Integer>> i = itemsRequired.iterator();
		while (i.hasNext()) {
			Entry<ItemIdentifier, Integer> entry = i.next();
			ItemIdentifier item = entry.getKey();
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
			ItemIdentifier index1 = new ItemIdentifier(is1);
			ItemIdentifier index2 = new ItemIdentifier(is2);
			invInput.put(index1, a);
			invInput.put(index2, 1);
		} else if (!newMoney.equals(BigInteger.ZERO)) {
			ItemStack is = ((EnderCoin) EnderMoney.coin).getItemStack(newMoney.longValue(), 1);
			ItemIdentifier index = new ItemIdentifier(is);
			invInput.put(index, 1);
		}

		if (!InventoryHelper.itemStackArrayIntoInventory(fakeInv, getTradeOutputs(), outputMinSlot,
				outputMaxSlot)) {
			throw new OutOfInventorySpaceException();
		}
		for (int _i = inputMinSlot; _i <= inputMaxSlot; _i++) {
			fakeInv.setInventorySlotContents(_i, null);
		}
		if (!InventoryHelper.hashMapIntoInventory(fakeInv, invInput, inputMinSlot, inputMaxSlot)) {
			throw new OutOfInventorySpaceException();
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

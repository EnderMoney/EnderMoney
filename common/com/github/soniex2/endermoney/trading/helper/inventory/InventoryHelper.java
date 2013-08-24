package com.github.soniex2.endermoney.trading.helper.inventory;

import java.util.HashMap;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

import com.github.soniex2.endermoney.trading.helper.item.ItemStackMapKey;

public class InventoryHelper {

	private InventoryHelper() {
	}

	public static IInventory itemStackArrayToInventory(ItemStack[] array) {
		return itemStackArrayToInventory(array, 0, array.length - 1);
	}

	public static IInventory itemStackArrayToInventory(ItemStack[] array, int start, int end) {
		InventoryBasic inv = new InventoryBasic("", true, end - start + 1);
		for (int i = start; i <= end; i++) {
			inv.setInventorySlotContents(i - start, array[i]);
		}
		return inv;
	}

	public static HashMap<ItemStackMapKey, Integer> inventoryToHashMap(IInventory inventory) {
		return inventoryToHashMap(inventory, 0, inventory.getSizeInventory() - 1);
	}

	public static HashMap<ItemStackMapKey, Integer> inventoryToHashMap(IInventory inventory,
			int startSlot, int endSlot) {
		HashMap<ItemStackMapKey, Integer> map = new HashMap<ItemStackMapKey, Integer>();
		for (int i = startSlot; i <= endSlot; i++) {
			ItemStack is = inventory.getStackInSlot(i);
			if (is == null) {
				continue;
			}
			ItemStackMapKey index = new ItemStackMapKey(is);
			if (map.containsKey(index)) {
				map.put(index, is.stackSize + map.get(index));
			} else {
				map.put(index, is.stackSize);
			}
		}
		return map;
	}

}

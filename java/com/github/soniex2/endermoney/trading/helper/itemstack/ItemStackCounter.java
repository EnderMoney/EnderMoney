package com.github.soniex2.endermoney.trading.helper.itemstack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackCounter implements Iterable<Entry<NBTTagCompound, Integer>> {

	private HashMap<NBTTagCompound, Integer> stacks = new HashMap<NBTTagCompound, Integer>();

	public void put(ItemStack is) {
		if (is == null)
			return;
		NBTTagCompound nbt = is.writeToNBT(new NBTTagCompound());
		nbt.removeTag("Count");
		Integer i = stacks.get(nbt);
		if (i == null)
			stacks.put(nbt, is.stackSize);
		else
			stacks.put(nbt, i.intValue() + is.stackSize);
	}

	public Integer get(ItemStack key) {
		if (key == null)
			return null;
		NBTTagCompound nbt = key.writeToNBT(new NBTTagCompound());
		nbt.removeTag("Count");
		return stacks.get(nbt);
	}
	
	public Integer get(NBTTagCompound key) {
		return stacks.get(key);
	}

	@Override
	public Iterator<Entry<NBTTagCompound, Integer>> iterator() {
		return stacks.entrySet().iterator();
	}

}

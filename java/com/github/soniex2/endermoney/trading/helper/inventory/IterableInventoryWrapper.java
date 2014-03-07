package com.github.soniex2.endermoney.trading.helper.inventory;

import java.lang.ref.WeakReference;
import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class IterableInventoryWrapper implements Iterable<ItemStack>,
		IInventory {

	private final WeakReference<IInventory> inv;

	public IterableInventoryWrapper(IInventory inv) {
		this.inv = new WeakReference<IInventory>(inv);
	}

	@Override
	public int getSizeInventory() {
		return inv.get().getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return inv.get().getStackInSlot(var1);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return inv.get().decrStackSize(var1, var2);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return inv.get().getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		inv.get().setInventorySlotContents(var1, var2);
	}

	@Override
	public String getInventoryName() {
		return inv.get().getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return inv.get().hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return inv.get().getInventoryStackLimit();
	}

	@Override
	public void markDirty() {
		inv.get().markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return inv.get().isUseableByPlayer(var1);
	}

	@Override
	public void openInventory() {
		inv.get().openInventory();
	}

	@Override
	public void closeInventory() {
		inv.get().closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return inv.get().isItemValidForSlot(var1, var2);
	}

	@Override
	public Iterator<ItemStack> iterator() {
		return new Iterator<ItemStack>() {

			int i = inv.get().getSizeInventory();
			int state = 0;

			@Override
			public boolean hasNext() {
				return state < i;
			}

			@Override
			public ItemStack next() {
				return inv.get().getStackInSlot(state++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

}

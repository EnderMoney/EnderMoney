package com.github.soniex2.endermoney.trading.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AbstractTraderContainer extends Container {
	
	protected class ReadOnlySlot extends Slot {
		public ReadOnlySlot(IInventory par1iInventory, int par2, int par3, int par4) {
			super(par1iInventory, par2, par3, par4);
		}
		
		@Override
		public boolean isItemValid(ItemStack par1ItemStack) {
			return false;
		}

		@Override
		public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
			return false;
		}
	}

	/**
	 * The TileEntity bound to this container.
	 */
	protected AbstractTraderTileEntity tileEntity;
	protected String playerName;
	protected String owner;

	protected AbstractTraderContainer(InventoryPlayer inventoryPlayer,
			AbstractTraderTileEntity tileEntity, String playerName) {
		this.tileEntity = tileEntity;
		this.playerName = playerName;
		this.owner = tileEntity.getOrSetOwner(playerName);
		bindPlayerInventory(inventoryPlayer);
		bindIInventory(tileEntity);
	}

	/**
	 * Bind a Player's inventory to this container.
	 * 
	 * @param inventory
	 *            The {@link net.minecraft.entity.player.InventoryPlayer
	 *            InventoryPlayer} to bind to this container.
	 */
	protected abstract void bindPlayerInventory(InventoryPlayer inventory);

	/**
	 * Bind an {@link net.minecraft.inventory.IInventory IInventory} to this
	 * container.
	 * 
	 * @param inventory
	 *            The {@link net.minecraft.inventory.IInventory IInventory} to
	 *            bind to this container.
	 */
	protected abstract void bindIInventory(IInventory inventory);

	@Override
	public abstract ItemStack transferStackInSlot(EntityPlayer player, int slot);

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

}

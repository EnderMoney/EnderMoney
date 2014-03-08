package com.github.soniex2.endermoney.trading.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class AbstractTraderTileEntity extends TileEntity implements
		IInventory {

	/**
	 * Enum containing trade statuses.
	 * @author SoniEx2
	 *
	 */
	public static enum TradeStatus {
		/**
		 * Can trade.
		 */
		AVAILABLE,
		/**
		 * Trade successful.
		 */
		SUCCESS,
		/**
		 * Trader results full.
		 */
		RESULTS_FULL,
		/**
		 * Output chest/inventory full.
		 */
		CHEST_FULL,
		/**
		 * Trader has nowhere to output to.
		 */
		NO_OUTPUT,
		/**
		 * Trader has nowhere to input from.
		 */
		NO_INPUT,
		/**
		 * Not enough items on trader input.
		 */
		NOT_ENOUGH_INPUT,
		/**
		 * Not enough items on input chest/inventory.
		 */
		NOT_ENOUGH_TRADE_ITEMS,
		/**
		 * For any invalid data, be it input or state.
		 */
		INVALID;
	}

	protected ItemStack[] inv;
	protected String owner;

	protected AbstractTraderTileEntity(int invSize) {
		inv = new ItemStack[invSize];
	}

	public abstract ItemStack[] getTradeInputs();

	public abstract ItemStack[] getTradeOutputs();

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < 0 || i >= inv.length) {
			return null;
		}
		return inv[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (getStackInSlot(i) == null) {
			return null;
		} else {
			ItemStack is = inv[i].copy();
			if (j > is.stackSize) {
				j = is.stackSize;
			}
			inv[i].stackSize -= j;
			if (inv[i].stackSize == 0) {
				inv[i] = null;
			}
			is.stackSize = j;
			this.markDirty();
			return is;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack stack = getStackInSlot(i);
		if (stack != null) {
			setInventorySlotContents(i, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (i < 0 || i >= inv.length) {
			return;
		} else {
			inv[i] = itemstack;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
				&& entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
						zCoord + 0.5) < 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length) {
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		owner = tagCompound.getString("Owner");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
		tagCompound.setString("Owner", owner);
	}

	public String getOrSetOwner(String playerName) {
		if (owner == null || owner.isEmpty()) {
			owner = playerName;
		}
		return owner;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord,
				this.zCoord, 3, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound tag = pkt.func_148857_g();
		this.owner = tag.getString("Owner");
	}

	@Deprecated
	public boolean canTrade(IInventory fakeInv, int inputMinSlot,
			int inputMaxSlot, int outputMinSlot, int outputMaxSlot) {
		switch (doTrade(fakeInv, inputMinSlot, inputMaxSlot, outputMinSlot,
				outputMaxSlot, false)) {
		case SUCCESS:
			throw new IllegalStateException();
		case AVAILABLE:
			return true;
		default:
			return false;
		}
	}

	@Deprecated
	public boolean doTrade(IInventory fakeInv, int inputMinSlot,
			int inputMaxSlot, int outputMinSlot, int outputMaxSlot) {
		switch (doTrade(fakeInv, inputMinSlot, inputMaxSlot, outputMinSlot,
				outputMaxSlot, true)) {
		case AVAILABLE:
			throw new IllegalStateException();
		case SUCCESS:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Simulate/run a trade.
	 * 
	 * @param fakeInv
	 *            - The trader inventory.
	 * @param inputMinSlot
	 *            - Trader inventory input start slot.
	 * @param inputMaxSlot
	 *            - Trader inventory input end slot.
	 * @param outputMinSlot
	 *            - Trader inventory output start slot.
	 * @param outputMaxSlot
	 *            - Trader inventory output end slot.
	 * @param really
	 *            - True to run, false to simulate.
	 * @return The trade status. See {@link TradeStatus}.
	 */
	public abstract TradeStatus doTrade(IInventory fakeInv, int inputMinSlot,
			int inputMaxSlot, int outputMinSlot, int outputMaxSlot,
			boolean really);
}

package com.github.soniex2.endermoney.trading.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import com.github.soniex2.endermoney.trading.base.AbstractTraderContainer;
import com.github.soniex2.endermoney.trading.tileentity.TileEntityItemTrader;

import cpw.mods.fml.common.FMLLog;

public class ContainerItemTrader extends AbstractTraderContainer {

	private InventoryBasic fakeInv;

	public ContainerItemTrader(InventoryPlayer inventoryPlayer,
			TileEntityItemTrader tileEntity, String playerName) {
		super(inventoryPlayer, tileEntity, playerName);
		fakeInv = new InventoryBasic("endermoney.traders.item", false, 18);
		int startX = 16;
		int startY = 7;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				addSlotToContainer(new Slot(fakeInv, x + (y * 3), startX
						+ (x * 18), startY + (y * 18)));
			}
		}
		startX = 107;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				addSlotToContainer(new OutputSlot(fakeInv, 9 + x + (y * 3),
						startX + (x * 18), startY + (y * 18)));
			}
		}
	}

	@Override
	protected void bindPlayerInventory(InventoryPlayer inventory) {
		int a = 8;
		int b = 121;
		int c = 179;
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, a + i * 18, c));
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, a + j
						* 18, b + i * 18));
			}
		}
	}

	@Override
	protected void bindIInventory(IInventory inventory) {
		if (playerName.equals(owner)) {
			int startX = 16;
			int startY = 64;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					addSlotToContainer(new Slot(inventory, x + (y * 3), startX
							+ (x * 18), startY + (y * 18)));
				}
			}
			startX = 107;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					addSlotToContainer(new Slot(inventory, 9 + x + (y * 3),
							startX + (x * 18), startY + (y * 18)));
				}
			}
		} else {
			int startX = 16;
			int startY = 64;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					addSlotToContainer(new ReadOnlySlot(inventory, x + (y * 3),
							startX + (x * 18), startY + (y * 18)));
				}
			}
			startX = 107;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					addSlotToContainer(new ReadOnlySlot(inventory, 9 + x
							+ (y * 3), startX + (x * 18), startY + (y * 18)));
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (slot < 36) {
				if (!this.mergeItemStack(stackInSlot, 54, 63, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(stackInSlot, 0, 36, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (!tileEntity.getWorldObj().isRemote) {
			for (int i = 0; i < fakeInv.getSizeInventory(); ++i) {
				ItemStack itemstack = this.fakeInv.getStackInSlotOnClosing(i);
				if (itemstack != null) {
					player.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}

	@Override
	public void doTrade(EntityPlayer player) {
		switch (tileEntity.doTrade(fakeInv, 0, 8, 9, 17, true)) {
		case NO_OUTPUT:
			player.addChatMessage(new ChatComponentText(
					"Whoops! This trader has nowhere to output to!"));
			break;
		case NO_INPUT:
			player.addChatMessage(new ChatComponentText(
					"Whoops! This trader has nowhere to input from!"));
			break;
		case CHEST_FULL:
			player.addChatMessage(new ChatComponentText(
					"Whoops! This trader's output inventory is full!"));
			break;
		case NOT_ENOUGH_TRADE_ITEMS:
			player.addChatMessage(new ChatComponentText(
					"Whoops! This trander's input inventory is full!"));
			break;
		case NOT_ENOUGH_INPUT:
			player.addChatMessage(new ChatComponentText(
					"Please put in the right items to trade."));
			break;
		case RESULTS_FULL:
			player.addChatMessage(new ChatComponentText(
					"Please collect your trade results."));
			break;
		case OVERFLOW:
			player.addChatMessage(new ChatComponentText(
					"Something went really wrong! Items were deleted!"));
			break;
		default:
			FMLLog.warning("Someone's been doing naughty stuff!");
		}
		this.detectAndSendChanges();
	}
}

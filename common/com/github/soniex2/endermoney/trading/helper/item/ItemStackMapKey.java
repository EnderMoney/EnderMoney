package com.github.soniex2.endermoney.trading.helper.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackMapKey {

	public final int itemID;
	public final int damage;
	private final NBTTagCompound tag;

	public ItemStackMapKey(ItemStack is) {
		this.itemID = is.itemID;
		this.damage = is.getItemDamage();
		this.tag = is.stackTagCompound;
	}
	
	public ItemStackMapKey(int itemID, int damage, NBTTagCompound tagCompound) {
		this.itemID = itemID;
		this.damage = damage;
		this.tag = tagCompound;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof ItemStackMapKey))
			return false;
		return itemID == ((ItemStackMapKey) obj).itemID
				&& damage == ((ItemStackMapKey) obj).damage
				&& tag.equals(((ItemStackMapKey) obj).tag);
	}
	
	public NBTTagCompound getTag() {
		return (NBTTagCompound) tag.copy();
	}
}

package com.github.soniex2.endermoney.trading.helper.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemIdentifier {

	public final Item item;
	public final int damage;
	private final NBTTagCompound tag;

	public ItemIdentifier(ItemStack is) {
		this.item = is.getItem();
		this.damage = is.getItemDamage();
		this.tag = (NBTTagCompound) (is.stackTagCompound != null ? is.stackTagCompound.copy()
				: null);
	}

	public ItemIdentifier(Item item, int damage, NBTTagCompound tagCompound) {
		this.item = item;
		this.damage = damage;
		this.tag = (NBTTagCompound) (tagCompound != null ? tagCompound.copy() : null);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof ItemIdentifier)) return false;
		return item == ((ItemIdentifier) obj).item
				&& damage == ((ItemIdentifier) obj).damage
				&& (tag == null ? ((ItemIdentifier) obj).tag == null : tag
						.equals(((ItemIdentifier) obj).tag));
	}

	public int hashCode() {
		return (item.getUnlocalizedName() + ":" + (damage & 65536) + (tag != null ? (":("
				+ tag.toString() + ")") : "")).hashCode();
	}

	public NBTTagCompound getTag() {
		return (NBTTagCompound) (tag != null ? tag.copy() : null);
	}
}

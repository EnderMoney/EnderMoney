package com.github.soniex2.endermoney.trading.helper.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemIdentifier {

	public final Item item;
	public final int damage;
	private final NBTTagCompound tag;

	public ItemIdentifier(ItemStack is) {
		this.item = is.getItem();
		// Reflectionless hack to get ItemStack.itemDamage
		this.damage = Items.diamond.getDamage(is);
		// Use .getTagCompound() because that's the proper way to do it
		this.tag = (NBTTagCompound) (is.getTagCompound() != null ? is
				.getTagCompound().copy() : null);
	}

	public ItemIdentifier(Item item, int damage, NBTTagCompound tagCompound) {
		this.item = item;
		this.damage = damage;
		this.tag = (NBTTagCompound) (tagCompound != null ? tagCompound.copy()
				: null);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ItemIdentifier))
			return false;
		return item == ((ItemIdentifier) obj).item
				&& damage == ((ItemIdentifier) obj).damage
				&& (tag == null ? ((ItemIdentifier) obj).tag == null : tag
						.equals(((ItemIdentifier) obj).tag));
	}

	@Override
	public int hashCode() {
		return item.hashCode() + (damage & 65535)
				+ (tag != null ? tag.hashCode() : 0);
	}

	public NBTTagCompound getTag() {
		return (NBTTagCompound) (tag != null ? tag.copy() : null);
	}
}

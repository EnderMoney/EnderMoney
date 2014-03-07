package com.github.soniex2.endermoney.trading.helper.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemIdentifier {

	public final Item item;
	public final int damage;
	private final NBTTagCompound tag;
	private int hashCode = 0;
	private boolean hashCodeCached = false;

	public ItemIdentifier(ItemStack is) {
		this.item = is.getItem();
		this.damage = is.getItemDamage();
		this.tag = (NBTTagCompound) (is.stackTagCompound != null ? is.stackTagCompound
				.copy() : null);
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
		if (!hashCodeCached) {
			hashCodeCached = true;
			hashCode = item.getClass().getCanonicalName().hashCode()
					+ (damage & 65535) + (tag != null ? tag.hashCode() : 0);
		}
		return hashCode;
	}

	public NBTTagCompound getTag() {
		return (NBTTagCompound) (tag != null ? tag.copy() : null);
	}
}

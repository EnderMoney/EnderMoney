package com.github.soniex2.endermoney.core;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class EnderCoin extends Item {

	private Random rand = new Random();

	public EnderCoin(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(EnderMoney.tab);
		setUnlocalizedName("endercoin");
		this.setHasSubtypes(true);
	}

	public ItemStack getItemStack(long value) {
		ItemStack is = new ItemStack(this, 1, 0);
		NBTTagCompound tag = new NBTTagCompound("tag");
		tag.setLong("value", value);
		is.setTagCompound(tag);
		return is;
	}

	@Override
	public int getColorFromItemStack(ItemStack is, int pass) {
		NBTTagCompound tag = is.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound("tag");
			tag.setLong("value", 0);
			is.setTagCompound(tag);
		}
		long v = tag.getLong("value");
		rand.setSeed(Long.valueOf(v).hashCode());
		int r = 255 - rand.nextInt(224) & 255;
		int g = 255 - rand.nextInt(224) & 255;
		int b = 255 - rand.nextInt(224) & 255;
		return r * 0x10000 | g * 0x100 | b;
	}

	@Override
	public String getItemDisplayName(ItemStack is) {
		NBTTagCompound tag = is.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound("tag");
			tag.setLong("value", 0);
			is.setTagCompound(tag);
		}
		long v = tag.getLong("value");
		return "$" + v + " EnderCoin";
	}

	@Override
	public void registerIcons(IconRegister ireg) {
		itemIcon = ireg.registerIcon("endermoneycore:coin");
	}

	public static long getValueFromItemStack(ItemStack is) {
		if (!(is.getItem() instanceof EnderCoin))
			throw new IllegalArgumentException(is.getItem().getItemDisplayName(is)
					+ " is not a valid item for method EnderCoin.getValueFromItemStack");
		NBTTagCompound tag = is.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound("tag");
			tag.setLong("value", 0);
			is.setTagCompound(tag);
		}
		return tag.getLong("value");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int x = 0; x <= 63; x++) {
			if (x != 63) {
				list.add(getItemStack(BigInteger.valueOf(2).pow(x).longValue()));
			} else {
				list.add(getItemStack(Long.MAX_VALUE));
			}
		}
	}

	public ItemStack getItemStack(long value, int amount) {
		ItemStack is = getItemStack(value);
		is.stackSize = amount;
		return is;
	}
}

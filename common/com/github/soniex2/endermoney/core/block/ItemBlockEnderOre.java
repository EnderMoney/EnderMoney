package com.github.soniex2.endermoney.core.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockEnderOre extends ItemBlock {

	public ItemBlockEnderOre(Block b) {
		super(b);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return this.field_150939_a.getUnlocalizedName() + "."
				+ par1ItemStack.getItemDamage();
	}
}
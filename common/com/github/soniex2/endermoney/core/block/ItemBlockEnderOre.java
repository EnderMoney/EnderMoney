package com.github.soniex2.endermoney.core.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockEnderOre extends ItemBlock {

	public ItemBlockEnderOre(Block b) {
		super(b);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
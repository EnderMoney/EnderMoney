package com.github.soniex2.endermoney.core.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

import com.github.soniex2.endermoney.core.item.EnderItem.EnderSubItem;

public class GenericItem extends EnderSubItem {

	private String texture;
	private int color = 0xFFFFFF;
	private boolean hasEffect = false;

	public GenericItem(int id, String name, String texture) {
		EnderItem.instance.super(id);
		setUnlocalizedName(name);
		this.texture = texture;
	}

	public GenericItem(int id, String name, String texture, int color) {
		this(id, name, texture);
		this.color = color;
	}

	public GenericItem(int id, String name, String texture, int color, boolean hasEffect) {
		this(id, name, texture, color);
		this.hasEffect = hasEffect;
	}

	@Override
	public void updateIcons(IIconRegister ireg) {
		iconIndex = ireg.registerIcon(texture);
	}

	@Override
	public int getColorFromItemStack(ItemStack is, int pass) {
		return pass > 0 ? 16777215 : color;
	}

	@Override
	@Deprecated
	public boolean hasEffect(ItemStack is) {
		return hasEffect;
	}
}
